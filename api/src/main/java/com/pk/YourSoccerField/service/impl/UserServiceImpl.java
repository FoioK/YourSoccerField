package com.pk.YourSoccerField.service.impl;

import com.pk.YourSoccerField.exception.*;
import com.pk.YourSoccerField.model.Role;
import com.pk.YourSoccerField.model.UserEntity;
import com.pk.YourSoccerField.repository.RoleRepository;
import com.pk.YourSoccerField.repository.UserRepository;
import com.pk.YourSoccerField.service.UserService;
import com.pk.YourSoccerField.service.dtoModel.UserDTO;
import com.pk.YourSoccerField.service.mapper.BaseFromDTO;
import com.pk.YourSoccerField.service.mapper.BaseToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;
    private BaseFromDTO<UserEntity, UserDTO> userFromDTO;
    private BaseToDTO<UserEntity, UserDTO> userToDTO;

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

        userDTO.setActive(true);
        userDTO.setCreateTime(LocalDateTime.now().toString());

        Long userCode = this.findNextUserCode();
        Role role = this.findRoleByName("user");
        insertUserRole(userCode, role.getId());

        userDTO.setCode(userCode);
        UserEntity userEntity = userRepository.save(userFromDTO.createFromDTO(userDTO));
        this.updateNextUserCode(userEntity.getCode());

        return this.userToDTO.createFromEntity(userEntity);
    }

    private boolean isUserWithSameEMail(String email) {
        return userRepository.findByEmail(email)
                .orElse(new ArrayList<>())
                .size() != 0;
    }

    private Long findNextUserCode() {
        return this.userRepository
                .findNextUserCode()
                .orElseThrow(() -> new MissingEntityException(
                        "Cannot find next user code value",
                        ErrorCode.NOT_FOUND_NEXT_USER_CODE
                )).longValue();
    }

    private Role findRoleByName(String name) {
        return this.roleRepository
                .findByName(name)
                .orElseThrow(() -> new MissingEntityException(
                        "Cannot find role with name " + name,
                        ErrorCode.NOT_FOUND_BY_NAME
                ));
    }

    private void insertUserRole(Long userCode, long roleId) {
        if (userRepository.insertUserRole(userCode, roleId) < 1) {
            new AppException(
                    "Error inserting data into the user_role table",
                    HttpStatus.INSUFFICIENT_STORAGE,
                    ErrorCode.INSERT_ERROR);
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
