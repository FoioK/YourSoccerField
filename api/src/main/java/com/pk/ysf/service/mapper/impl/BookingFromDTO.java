package com.pk.ysf.service.mapper.impl;

import com.pk.ysf.apimodels.exception.ErrorCode;
import com.pk.ysf.apimodels.exception.MissingEntityException;
import com.pk.ysf.apimodels.entity.Booking;
import com.pk.ysf.apimodels.entity.SoccerField;
import com.pk.ysf.repository.SoccerFieldRepository;
import com.pk.ysf.service.dtoModel.BookingDTO;
import com.pk.ysf.service.mapper.BaseMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

@Component
public class BookingFromDTO implements BaseMapper<BookingDTO, Booking> {

    private final SoccerFieldRepository soccerFieldRepository;

    @Autowired
    public BookingFromDTO(SoccerFieldRepository soccerFieldRepository) {
        this.soccerFieldRepository = soccerFieldRepository;
    }

    @Override
    public Booking map(BookingDTO dto) {
//        return new Booking(
//                dto.getId(),
//                dto.getUserCode(),
//                LocalDateTime.parse(dto.getStartDate()),
//                LocalTime.parse(dto.getExecutionTime()),
//                this.getSoccerFieldById(dto.getSoccerField()),
//                dto.getPayed() == null ? false : dto.getPayed()
//        );
        return null;
    }

    private SoccerField getSoccerFieldById(Long soccerFieldId) {
        return this.soccerFieldRepository
                .findById(soccerFieldId)
                .orElseThrow(() -> new MissingEntityException(
                        "Cannot find soccer field with id " + soccerFieldId,
                        ErrorCode.NOT_FOUND_BY_ID
                ));
    }

    @NotNull
    @Override
    public Collection<Booking> mapAll(@NotNull Collection<? extends BookingDTO> collections) {
        return null;
    }
}
