package com.pk.ysf.service.mapper.impl;

import com.pk.ysf.api.service.mapper.BaseMapper;
import com.pk.ysf.apimodels.entity.Booking;
import com.pk.ysf.service.dtoModel.BookingDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @NotNull
    @Override
    public Collection<BookingDTO> mapAll(@NotNull Collection<? extends Booking> collections) {
        return collections.stream().map(this::map).collect(Collectors.toList());
    }
}
