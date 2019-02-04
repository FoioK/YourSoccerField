package com.pk.ysf.service.impl;

import com.pk.ysf.apimodels.exception.*;
import com.pk.ysf.apimodels.model.Booking;
import com.pk.ysf.apimodels.model.Role;
import com.pk.ysf.apimodels.model.UserEntity;
import com.pk.ysf.apimodels.model.UserRole;
import com.pk.ysf.domain.Constants;
import com.pk.ysf.repository.BookingRepository;
import com.pk.ysf.repository.RoleRepository;
import com.pk.ysf.repository.UserRepository;
import com.pk.ysf.service.UserService;
import com.pk.ysf.service.dtoModel.BookingDTO;
import com.pk.ysf.service.dtoModel.UserDTO;
import com.pk.ysf.service.mapper.BaseFromDTO;
import com.pk.ysf.service.mapper.BaseToDTO;
import com.pk.ysf.util.CustomAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BookingRepository bookingRepository;

    private PasswordEncoder passwordEncoder;
    private BaseFromDTO<UserEntity, UserDTO> userFromDTO;
    private BaseToDTO<UserEntity, UserDTO> userToDTO;
    private BaseToDTO<Booking, BookingDTO> bookingToDTO;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            BookingRepository bookingRepository, @Qualifier("encoder") PasswordEncoder passwordEncoder, CustomAccessTokenConverter customAccessTokenConverter, JwtTokenStore jwtTokenStore, JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bookingRepository = bookingRepository;
        this.passwordEncoder = passwordEncoder;
        this.setUserMapper();
        this.setBookingMapper();
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

    private void setBookingMapper() {
        this.bookingToDTO = entity -> {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setId(entity.getId());
            bookingDTO.setUserCode(entity.getUserCode());
            bookingDTO.setStartDate(Objects.requireNonNull(entity.getStartDate()).toString());
            bookingDTO.setExecutionTime(Objects.requireNonNull(entity.getExecutionTime()).toString());
            bookingDTO.setSoccerField(Objects.requireNonNull(entity.getSoccerField()).getId());
            bookingDTO.setPayed(entity.isPayed());

            return bookingDTO;
        };
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public UserDTO createUser(UserDTO userDTO) {
        this.validationUserDTOModel(userDTO);

        userDTO.setActive(true);
        userDTO.setCreateTime(LocalDateTime.now().toString());

        Long userCode = this.findNextUserCode(true);
        Role role = this.findRoleByName(Constants.USER_ROLE.getValue(), true);
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
                    "Already exist user with this nickname",
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

    private Role findRoleByName(String name, boolean isUserRoleAndFirstSearch) {
        Optional<Role> role = this.roleRepository.findByName(name);

        if (role.isPresent()) {
            return role.get();
        }

        if (isUserRoleAndFirstSearch) {
            if (this.insertRole(name) == null) {
                throw new CreateEntityException(
                        "Error occurred while insert user role record",
                        ErrorCode.INSERT_ERROR
                );
            }

            return findRoleByName(name, false);
        }

        throw new MissingEntityException(
                "Cannot find role with name " + name,
                ErrorCode.NOT_FOUND_BY_NAME
        );
    }

    private Role insertRole(String name) {
        Role role = new Role(null, name, Collections.emptyList(), Collections.emptyList());

        return this.roleRepository.save(role);
    }

    private void insertUserRole(Long userCode, Role role) {
        this.entityManager.persist(new UserRole(null, userCode, role));
        Optional<UserRole> userRole = this.userRepository.findUserRoleByUserCode(userCode);

        if (!userRole.isPresent()) {
            throw new CreateEntityException(
                    "Error occurred while map user to role",
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

    @Override
    public List<BookingDTO> getAllBookingsByUserId(Long userId) {
        UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new MissingEntityException(
                        "Cannot find user with id " + userId,
                        ErrorCode.NOT_FOUND_BY_ID
                ));

        if (user.getCode() == null) {
            return Collections.emptyList();
        }

        List<Booking> bookings = this.bookingRepository
                .findAllByUserCode(user.getCode());

        return new ArrayList<>(this.bookingToDTO.mapAllFromEntities(bookings));
    }

    @Override
    public Optional<UserDTO> updateUser(Long userId, UserDTO userDTO) {
        Optional<UserEntity> userById = this.userRepository
                .findById(userId);

        if (!userById.isPresent()) {
            return Optional.ofNullable(this.createUser(userDTO));
        }

        UserEntity userEntity = this.userFromDTO.createFromDTO(userDTO);
        this.userRepository.save(userEntity);

        return Optional.empty();
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }
}
