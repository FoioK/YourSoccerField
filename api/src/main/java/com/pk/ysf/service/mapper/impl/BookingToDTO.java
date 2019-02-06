package com.pk.ysf.service.mapper.impl;

import com.pk.ysf.apimodels.model.Booking;
import com.pk.ysf.service.dtoModel.BookingDTO;
import com.pk.ysf.service.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BookingToDTO implements BaseMapper<Booking, BookingDTO> {

    @Override
    public BookingDTO map(Booking entity) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(entity.getId());
        bookingDTO.setUserCode(entity.getUserCode());
        bookingDTO.setStartDate(Objects.requireNonNull(entity.getStartDate()).toString());
        bookingDTO.setExecutionTime(Objects.requireNonNull(entity.getExecutionTime()).toString());
        bookingDTO.setSoccerField(Objects.requireNonNull(entity.getSoccerField()).getId());
        bookingDTO.setPayed(entity.isPayed());

        return bookingDTO;
    }
}
