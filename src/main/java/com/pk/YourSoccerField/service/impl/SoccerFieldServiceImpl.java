package com.pk.YourSoccerField.service.impl;

import com.pk.YourSoccerField.model.SoccerField;
import com.pk.YourSoccerField.repository.SoccerFieldRepository;
import com.pk.YourSoccerField.service.SoccerFieldService;
import com.pk.YourSoccerField.service.dtoModel.SoccerFieldDTO;
import com.pk.YourSoccerField.service.mapper.BaseFromDTO;
import com.pk.YourSoccerField.service.mapper.BaseToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SoccerFieldServiceImpl implements SoccerFieldService {

    private SoccerFieldRepository soccerFieldRepository;
    private BaseToDTO<SoccerField, SoccerFieldDTO> soccerFieldToDTO;
    private BaseFromDTO<SoccerField, SoccerFieldDTO> soccerFieldFromDTO;

    @Autowired
    public SoccerFieldServiceImpl(
            SoccerFieldRepository soccerFieldRepository) {
        this.soccerFieldRepository = soccerFieldRepository;
        setSoccerFieldMapper();
    }

    private void setSoccerFieldMapper() {
        this.soccerFieldToDTO = entity -> {
            SoccerFieldDTO soccerFieldDTO = new SoccerFieldDTO();
            soccerFieldDTO.setId(entity.getId());
            soccerFieldDTO.setName(entity.getName());
            soccerFieldDTO.setAddressId(entity.getAddress().getId());
            soccerFieldDTO.setSurfaceId(entity.getSurface().getId());
            soccerFieldDTO.setWidth(entity.getWidth());
            soccerFieldDTO.setLength(entity.getLength());
            soccerFieldDTO.setPrice(Objects.requireNonNull(entity.getPrice()).toString());
            soccerFieldDTO.setLighting(entity.isLighting());
            soccerFieldDTO.setFenced(entity.isFenced());
            soccerFieldDTO.setLockerRom(entity.isLockerRoom());
            soccerFieldDTO.setDescription(entity.getDescription());

            return soccerFieldDTO;
        };

        this.soccerFieldFromDTO = dto -> {
            SoccerField soccerField = new SoccerField(
                    dto.getId(),
                    dto.getName(),
                    null,
                    null,
                    dto.getWidth(),
                    dto.getLength(),
                    new BigDecimal(dto.getPrice()),
                    dto.isLighting(),
                    dto.isFenced(),
                    dto.isLockerRom(),
                    dto.getDescription(),
                    new ArrayList<>()
            );

            return soccerField;
        };
    }

    @Override
    public List<SoccerFieldDTO> getAll() {
        return new ArrayList<>(
                this.soccerFieldToDTO.mapAllFromEntities(this.soccerFieldRepository.findAll()));
    }

    @Override
    public SoccerFieldDTO createSoccerField(SoccerFieldDTO soccerFieldDTO) {
        return null;
    }
}
