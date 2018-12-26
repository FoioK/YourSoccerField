package com.pk.YourSoccerField.service.impl;

import com.pk.YourSoccerField.exception.AppException;
import com.pk.YourSoccerField.exception.BookingException;
import com.pk.YourSoccerField.exception.ErrorCode;
import com.pk.YourSoccerField.exception.MissingEntityException;
import com.pk.YourSoccerField.model.Booking;
import com.pk.YourSoccerField.model.OpenHour;
import com.pk.YourSoccerField.model.SoccerField;
import com.pk.YourSoccerField.repository.BookingRepository;
import com.pk.YourSoccerField.repository.SoccerFieldRepository;
import com.pk.YourSoccerField.service.BookingService;
import com.pk.YourSoccerField.service.dtoModel.BookingDTO;
import com.pk.YourSoccerField.service.mapper.BaseFromDTO;
import com.pk.YourSoccerField.service.mapper.BaseToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class BookingServiceImpl implements BookingService {

    private SoccerFieldRepository soccerFieldRepository;
    private BookingRepository bookingRepository;
    private BaseFromDTO<Booking, BookingDTO> bookingFromDTO;
    private BaseToDTO<Booking, BookingDTO> bookingToDTO;

    @Autowired
    public BookingServiceImpl(
            SoccerFieldRepository soccerFieldRepository,
            BookingRepository bookingRepository
    ) {
        this.soccerFieldRepository = soccerFieldRepository;
        this.bookingRepository = bookingRepository;
        setBookingMapper();
    }

    private void setBookingMapper() {
        this.bookingFromDTO = dto -> new Booking(
                dto.getId(),
                dto.getUserCode(),
                LocalDateTime.parse(dto.getStartDate()),
                LocalTime.parse(dto.getExecutionTime()),
                this.getSoccerFieldById(dto.getSoccerField()),
                dto.getPayed() == null ? false : dto.getPayed()
        );

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
    public BookingDTO create(BookingDTO bookingDTO) {
        if (!this.validation(bookingDTO)) {
            throw new AppException(
                    "Error occurred during data validation",
                    HttpStatus.PRECONDITION_FAILED,
                    ErrorCode.INVALID_INPUT
            );
        }

        Booking booking = this.bookingFromDTO.createFromDTO(bookingDTO);

        return this.bookingToDTO
                .createFromEntity(this.bookingRepository.save(booking));
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
