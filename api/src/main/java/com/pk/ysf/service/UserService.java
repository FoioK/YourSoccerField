package com.pk.ysf.service;

import com.pk.ysf.service.dtoModel.BookingDTO;
import com.pk.ysf.service.dtoModel.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    List<BookingDTO> getAllBookingsByUserId(Long userId);

    Optional<UserDTO> updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long userId);
}
