package com.pk.YourSoccerField.service.impl;

import com.pk.YourSoccerField.exception.*;
import com.pk.YourSoccerField.model.Role;
import com.pk.YourSoccerField.model.UserEntity;
import com.pk.YourSoccerField.model.UserRole;
import com.pk.YourSoccerField.repository.RoleRepository;
import com.pk.YourSoccerField.repository.UserRepository;
import com.pk.YourSoccerField.service.UserService;
import com.pk.YourSoccerField.service.dtoModel.UserDTO;
import com.pk.YourSoccerField.service.mapper.BaseFromDTO;
import com.pk.YourSoccerField.service.mapper.BaseToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;
    private BaseFromDTO<UserEntity, UserDTO> userFromDTO;
    private BaseToDTO<UserEntity, UserDTO> userToDTO;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            @Qualifier("encoder") PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.setUserMapper();
    }

    private void setUserMapper() {
        this.userFromDTO = dto -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(dto.getId());
            userEntity.setCode(dto.getCode());
            userEntity.setEmail(dto.getEmail());
            userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
            userEntity.setActive(dto.isActive());
            userEntity.setFirstName(dto.getFirstName());
            userEntity.setSecondName(dto.getSecondName());
            userEntity.setNickname(dto.getNickname());
            userEntity.setCreateTime(LocalDateTime.parse(dto.getCreateTime()));

            return userEntity;
        };

        this.userToDTO = entity -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(entity.getId());
            userDTO.setCode(entity.getCode());
            userDTO.setEmail(entity.getEmail());
            userDTO.setActive(entity.isActive());
            userDTO.setFirstName(entity.getFirstName());
            userDTO.setSecondName(entity.getSecondName());
            userDTO.setNickname(entity.getNickname());
            userDTO.setCreateTime(Objects.requireNonNull(entity.getCreateTime()).toString());

            return userDTO;
        };
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public UserDTO createUser(UserDTO userDTO) {
        this.validationUserDTOModel(userDTO);

        userDTO.setActive(true);
        userDTO.setCreateTime(LocalDateTime.now().toString());

        Long userCode = this.findNextUserCode(true);
        Role role = this.findRoleByName("user");
        this.insertUserRole(userCode, role);

        userDTO.setCode(userCode);
        UserEntity userEntity = userRepository.save(userFromDTO.createFromDTO(userDTO));
        this.updateNextUserCode(userEntity.getCode());

        return this.userToDTO.createFromEntity(userEntity);
    }

    private void validationUserDTOModel(UserDTO userDTO) {
        if (userDTO == null || userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new CreateEntityException(
                    "Cannot create user with incorrect password",
                    ErrorCode.INCORRECT_PASSWORD
            );
        }

        if (isUserWithSameEMail(userDTO.getEmail())) {
            throw new DuplicatEntityException(
                    "Already exist user with this email address",
                    ErrorCode.DUPLICATE_USER_EMAIL);
        }

        if (isUserWithSameNickname(userDTO.getNickname())) {
            throw new DuplicatEntityException(
                    "Alery exist user with this nickname",
                    ErrorCode.DUPLICATE_USER_NICKNAME
            );
        }
    }

    private boolean isUserWithSameEMail(String email) {
        return userRepository.findByEmail(email)
                .orElse(Collections.emptyList())
                .size() != 0;
    }

    private boolean isUserWithSameNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElse(Collections.emptyList())
                .size() != 0;
    }

    private Long findNextUserCode(boolean isFirst) {
        Optional<BigInteger> nextUserCode = this.userRepository.findNextUserCode();

        if (nextUserCode.isPresent()) {
            return nextUserCode.get().longValue();
        }

        if (isFirst) {
            insertNewNextUserCode();

            return this.findNextUserCode(false);
        }

        throw new MissingEntityException(
                "Cannot find next user code value",
                ErrorCode.NOT_FOUND_NEXT_USER_CODE
        );
    }

    private void insertNewNextUserCode() {
        Long lastUserCode = this.userRepository
                .findLastUserCode()
                .orElseThrow(() -> new MissingEntityException(
                        "Cannot find last user code",
                        ErrorCode.NOT_FOUND_LAST_USER_CODE
                )).longValue();

        this.userRepository.insertNextUserCode(lastUserCode + 1);
    }

    private Role findRoleByName(String name) {
        return this.roleRepository
                .findByName(name)
                .orElseThrow(() -> new MissingEntityException(
                        "Cannot find role with name " + name,
                        ErrorCode.NOT_FOUND_BY_NAME
                ));
    }

    private void insertUserRole(Long userCode, Role role) {
        this.entityManager.persist(new UserRole(null, userCode, role));
        Optional<UserRole> userRole = this.userRepository.findUserRoleByUserCode(userCode);

        if (!userRole.isPresent()) {
            throw new CreateEntityException(
                    "Error occurred while insert user role record",
                    ErrorCode.INSERT_ERROR
            );
        }
    }

    private void updateNextUserCode(Long actualCode) {
        if (this.userRepository.updateNextUserCode(actualCode + 1) == 0) {
            throw new UpdateEntityException(
                    "Cannot update next user code",
                    ErrorCode.UPDATE_NEXT_USER_CODE
            );
        }
    }
}
