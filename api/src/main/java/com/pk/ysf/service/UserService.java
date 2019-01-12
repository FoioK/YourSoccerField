package com.pk.ysf.service;

import com.pk.ysf.service.dtoModel.BookingDTO;
import com.pk.ysf.service.dtoModel.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    List<BookingDTO> getAllBookingsByUserId(Long userId);
}
