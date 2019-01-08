package com.pk.YourSoccerField.service;

import com.pk.YourSoccerField.service.dtoModel.BookingDTO;
import com.pk.YourSoccerField.service.dtoModel.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    List<BookingDTO> getAllBookingsByUserId(Long userId);
}
