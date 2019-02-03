package com.pk.ysf.service;

import com.pk.ysf.service.dtoModel.BookingDTO;
import com.pk.ysf.service.dtoModel.SoccerFieldDTO;
import com.pk.ysf.service.dtoModel.SurfaceDTO;

import java.util.List;
import java.util.Optional;

public interface SoccerFieldService {

    List<SoccerFieldDTO> getAll();

    SoccerFieldDTO createSoccerField(SoccerFieldDTO soccerFieldDTO);

    SoccerFieldDTO getById(Long soccerFieldId);

    List<SoccerFieldDTO> getByAddressContains(String street);

    List<SoccerFieldDTO> getExampleTen();

    List<SoccerFieldDTO> getByCustomCriteria(String encodedObject);

    List<SurfaceDTO> getAllSurface();

    List<BookingDTO> getAllBookings(Long soccerFieldId);

    Optional<SoccerFieldDTO> updateSoccerField(Long soccerFieldId, SoccerFieldDTO soccerField);
}
