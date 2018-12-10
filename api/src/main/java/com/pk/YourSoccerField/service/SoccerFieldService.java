package com.pk.YourSoccerField.service;

import com.pk.YourSoccerField.service.dtoModel.SoccerFieldDTO;

import java.util.List;

public interface SoccerFieldService {

    List<SoccerFieldDTO> getAll();

    SoccerFieldDTO createSoccerField(SoccerFieldDTO soccerFieldDTO);

    List<SoccerFieldDTO> getByAddressContains(String street);

    List<SoccerFieldDTO> getExampleTen();
}
