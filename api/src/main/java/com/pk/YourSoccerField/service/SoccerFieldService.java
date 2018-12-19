package com.pk.YourSoccerField.service;

import com.pk.YourSoccerField.service.dtoModel.SoccerFieldDTO;
import com.pk.YourSoccerField.service.dtoModel.SurfaceDTO;

import java.util.List;

public interface SoccerFieldService {

    List<SoccerFieldDTO> getAll();

    SoccerFieldDTO createSoccerField(SoccerFieldDTO soccerFieldDTO);

    List<SoccerFieldDTO> getByAddressContains(String street);

    List<SoccerFieldDTO> getExampleTen();

    List<SoccerFieldDTO> getByCustomCriteria(String encodedObject);

    List<SurfaceDTO> getAllSurface();
}
