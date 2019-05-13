package com.pk.ysf.service.impl;

import com.pk.ysf.api.model.entity.Booking;
import com.pk.ysf.api.repository.BookingRepository;
import com.pk.ysf.api.repository.UserRepository;
import com.pk.ysf.api.model.entity.UserEntity;
import com.pk.ysf.api.model.exception.CreateEntityException;
import com.pk.ysf.api.model.exception.DuplicateEntityException;
import com.pk.ysf.api.model.exception.MissingEntityException;
import com.pk.ysf.api.model.exception.UpdateEntityException;
import com.pk.ysf.service.UserService;
import com.pk.ysf.service.dtoModel.BookingDTO;
import com.pk.ysf.service.dtoModel.UserDTO;
import com.pk.ysf.service.mapper.BaseFromDTO;
import com.pk.ysf.service.mapper.BaseToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    private PasswordEncoder passwordEncoder;
    private BaseFromDTO<UserEntity, UserDTO> userFromDTO;
    private BaseToDTO<UserEntity, UserDTO> userToDTO;
    private BaseToDTO<Booking, BookingDTO> bookingToDTO;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            BookingRepository bookingRepository,
            @Qualifier("encoder") PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
    public List<UserDTO> getAll() {
        return new ArrayList<>(
                this.userToDTO.mapAllFromEntities(this.userRepository.findAll())
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public UserDTO createUser(UserDTO userDTO) {
        this.validationUserDTOModel(userDTO);

        userDTO.setActive(true);
        userDTO.setCreateTime(LocalDateTime.now().toString());

        Long userCode = this.findNextUserCode(true);

        userDTO.setCode(userCode);
        UserEntity userEntity = userRepository.save(userFromDTO.createFromDTO(userDTO));
        this.updateNextUserCode(userEntity.getCode());

        return this.userToDTO.createFromEntity(userEntity);
    }


    private void validationUserDTOModel(UserDTO userDTO) {
        if (userDTO == null || userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new CreateEntityException(
                    "Cannot create user with incorrect password");
        }

        if (isUserWithSameEMail(userDTO.getEmail())) {
            throw new DuplicateEntityException("Already exist user with this email address");
        }

        if (isUserWithSameNickname(userDTO.getNickname())) {
            throw new DuplicateEntityException("Already exist user with this nickname");
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
                "Cannot find next user code value"
        );
    }

    private void insertNewNextUserCode() {
        Long lastUserCode = this.userRepository
                .findLastUserCode()
                .orElseThrow(() -> new MissingEntityException(
                        "Cannot find last user code")).longValue();

        this.userRepository.insertNextUserCode(lastUserCode + 1);
    }

    private void updateNextUserCode(Long actualCode) {
        if (this.userRepository.updateNextUserCode(actualCode + 1) == 0) {
            throw new UpdateEntityException(
                    "Cannot update next user code"
            );
        }
    }

    @Override
    public List<BookingDTO> getAllBookingsByUserId(Long userId) {
        UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new MissingEntityException(
                        "Cannot find user with id " + userId
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

        userDTO.setPassword("");
        UserEntity userEntity = this.userFromDTO.createFromDTO(userDTO);
        this.userRepository.save(userEntity);

        return Optional.empty();
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }
}
