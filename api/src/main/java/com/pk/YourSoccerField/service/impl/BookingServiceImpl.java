package com.pk.YourSoccerField.service.impl;

import com.pk.YourSoccerField.exception.ErrorCode;
import com.pk.YourSoccerField.exception.MissingEntityException;
import com.pk.YourSoccerField.model.OpenHour;
import com.pk.YourSoccerField.model.SoccerField;
import com.pk.YourSoccerField.repository.SoccerFieldRepository;
import com.pk.YourSoccerField.service.BookingService;
import com.pk.YourSoccerField.service.dtoModel.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class BookingServiceImpl implements BookingService {

    private SoccerFieldRepository soccerFieldRepository;

    @Autowired
    public BookingServiceImpl(SoccerFieldRepository soccerFieldRepository) {
        this.soccerFieldRepository = soccerFieldRepository;
    }

    @Override
    public BookingDTO create(BookingDTO bookingDTO) {
        if (!this.validation(bookingDTO)) {
            return new BookingDTO();
        }

        return null;
    }

    private boolean validation(BookingDTO bookingDTO) {
        SoccerField soccerField = this.getSoccerFieldById(bookingDTO.getSoccerField());
        LocalDateTime bookingStartDate = LocalDateTime.parse(bookingDTO.getStartDate());
        LocalTime executionTime = LocalTime.parse(bookingDTO.getExecutionTime());
        OpenHour openHour = soccerField.getOpenHour();

        if (!this.checkIsSoccerFieldOpen(bookingStartDate, executionTime, openHour)) {
            // TODO nie jest wtedy otwarty
            return false;
        }

        return true;
    }

    private SoccerField getSoccerFieldById(Long soccerFieldId) {
        return this.soccerFieldRepository
                .findById(soccerFieldId)
                .orElseThrow(() -> new MissingEntityException(
                        "Cannot find soccer field with id " + soccerFieldId,
                        ErrorCode.NOT_FOUND_BY_ID
                ));
    }

    private boolean checkIsSoccerFieldOpen(
            LocalDateTime bookingStartDate,
            LocalTime executionTime,
            OpenHour openHour
    ) {
        DayOfWeek dayOfWeek = bookingStartDate.getDayOfWeek();
        LocalTime openTime = findOpenTimeByDayOfWeek(dayOfWeek, openHour);
        LocalTime closeTime = findCloseTimeByDayOfWeek(dayOfWeek, openHour);
        LocalTime bookingStartTime = bookingStartDate.toLocalTime();

        if (openTime.isAfter(bookingStartDate.toLocalTime())) {
            // TODO jeszcze nie otwarty
            return false;
        }

        if (closeTime.isBefore(this.sumLocalTimes(bookingStartTime, executionTime))) {
            // TODO już zamknięty
            return false;
        }

        return true;
    }

    private LocalTime findOpenTimeByDayOfWeek(DayOfWeek dayOfWeek, OpenHour openHour) {
        switch (dayOfWeek.getValue()) {
            case 1:
                return openHour.getS1();
            case 2:
                return openHour.getS2();
            case 3:
                return openHour.getS3();
            case 4:
                return openHour.getS4();
            case 5:
                return openHour.getS5();
            case 6:
                return openHour.getS6();
            case 7:
                return openHour.getS7();
        }

        // TODO blad (zly dzien tygodnia)
        return LocalTime.now();
    }

    private LocalTime findCloseTimeByDayOfWeek(DayOfWeek dayOfWeek, OpenHour openHour) {
        switch (dayOfWeek.getValue()) {
            case 1:
                return openHour.getE1();
            case 2:
                return openHour.getE2();
            case 3:
                return openHour.getE3();
            case 4:
                return openHour.getE4();
            case 5:
                return openHour.getE5();
            case 6:
                return openHour.getE6();
            case 7:
                return openHour.getE7();
        }

        // TODO blad (zly dzien tygodnia)
        return LocalTime.now();
    }

    private LocalTime sumLocalTimes(LocalTime one, LocalTime two) {
        return one.plusHours(two.getHour()).plusMinutes(two.getMinute());
    }
}
