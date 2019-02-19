package com.pk.ysf.service.impl;

import com.pk.ysf.apimodels.dto.BookingDetails;
import com.pk.ysf.apimodels.dto.BookingInput;
import com.pk.ysf.apimodels.exception.AppException;
import com.pk.ysf.apimodels.exception.BookingException;
import com.pk.ysf.apimodels.exception.ErrorCode;
import com.pk.ysf.apimodels.exception.MissingEntityException;
import com.pk.ysf.apimodels.entity.Booking;
import com.pk.ysf.apimodels.entity.OpenHour;
import com.pk.ysf.apimodels.entity.SoccerField;
import com.pk.ysf.repository.BookingRepository;
import com.pk.ysf.repository.SoccerFieldRepository;
import com.pk.ysf.service.BookingService;
import com.pk.ysf.service.dtoModel.BookingDTO;
import com.pk.ysf.service.mapper.booking.BookingInputToBooking;
import com.pk.ysf.service.mapper.impl.BookingFromDTO;
import com.pk.ysf.service.mapper.impl.BookingToDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

//@Service
public class BookingServiceImplJava implements BookingService {

    private SoccerFieldRepository soccerFieldRepository;
    private BookingRepository bookingRepository;

    private final BookingInputToBooking bookingInputToBooking;

    @Autowired
    public BookingServiceImplJava(
            SoccerFieldRepository soccerFieldRepository,
            BookingRepository bookingRepository,
            BookingInputToBooking bookingInputToBooking) {
        this.soccerFieldRepository = soccerFieldRepository;
        this.bookingRepository = bookingRepository;
        this.bookingInputToBooking = bookingInputToBooking;
    }

    @NotNull
    @Override
    public BookingDetails create(@NotNull BookingInput bookingInput) {
        if (!this.validation(bookingDTO)) {
            throw new AppException(
                    "Error occurred during data validation",
                    HttpStatus.PRECONDITION_FAILED,
                    ErrorCode.INVALID_INPUT
            );
        }

        Booking booking = this.bookingInputToBooking.map(bookingDTO);
//
//        return this.bookingToDTO.map(this.bookingRepository.save(booking));
    }

    private boolean validation(BookingDTO bookingDTO) {
        SoccerField soccerField = this.getSoccerFieldById(bookingDTO.getSoccerField());
        LocalDateTime bookingStartDate = LocalDateTime.parse(bookingDTO.getStartDate());
        LocalTime executionTime = LocalTime.parse(bookingDTO.getExecutionTime());
        OpenHour openHour = soccerField.getOpenHour();

        if (!this.checkIsSoccerFieldOpen(bookingStartDate, executionTime, openHour)) {
            return false;
        }

        return this.checkIsSoccerFieldFree(
                bookingDTO.getSoccerField(),
                bookingStartDate,
                executionTime);
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

        if (openTime.isAfter(bookingStartTime)) {
            throw new BookingException(
                    "Soccer field is not open yet",
                    ErrorCode.SOCCER_FIELD_NOT_OPEN
            );
        }

        if (closeTime.isBefore(this.sumLocalTimes(bookingStartTime, executionTime))) {
            throw new BookingException(
                    "Soccer field is then closed",
                    ErrorCode.SOCCER_FIELD_CLOSED
            );
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

        throw new AppException(
                "Error occurred while find open time by day of week",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.OPEN_HOUR_GET_VALUE
        );
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

        throw new AppException(
                "Error occurred while find open time by day of week",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.OPEN_HOUR_GET_VALUE
        );
    }

    private boolean checkIsSoccerFieldFree(
            Long soccerFieldId,
            LocalDateTime bookingStartDate,
            LocalTime executionTime
    ) {
        LocalTime bookingStartTime = bookingStartDate.toLocalTime();
        LocalTime bookingEndTime = this.sumLocalTimes(bookingStartTime, executionTime);

        List<Booking> bookings = this.bookingRepository
                .findAllByDate(
                        soccerFieldId,
                        bookingStartDate.getYear(),
                        bookingStartDate.getMonthValue(),
                        bookingStartDate.getDayOfMonth()
                );

        if (this.filterBookingsByStartAndEndTime(
                bookings,
                bookingStartTime,
                bookingEndTime) != 0
        ) {
            throw new BookingException(
                    "Is another booking on this time",
                    ErrorCode.BOOKING_CONFLICT
            );
        }

        return true;
    }

    private Long filterBookingsByStartAndEndTime(
            List<Booking> bookings,
            LocalTime bookingStartTime,
            LocalTime bookingEndTime
    ) {
        return bookings.stream()
                .filter(booking -> {
                    LocalTime startTime = Objects.requireNonNull(booking.component3()).toLocalTime();
                    LocalTime endTime = this.sumLocalTimes(
                            startTime,
                            Objects.requireNonNull(booking.getExecutionTime())
                    );

                    if (bookingStartTime.equals(startTime) || bookingEndTime.equals(endTime)) {
                        return true;
                    }

                    if (bookingStartTime.isAfter(startTime) && bookingStartTime.isBefore(endTime)) {
                        return true;
                    }

                    return bookingEndTime.isAfter(startTime) && bookingEndTime.isBefore(endTime);
                })
                .count();
    }

    private LocalTime sumLocalTimes(LocalTime one, LocalTime two) {
        LocalTime sum = one.plusHours(two.getHour()).plusMinutes(two.getMinute());

        return sum.isBefore(one) ?
                LocalTime.of(23, 59, 59, 59) :
                sum;
    }
}
