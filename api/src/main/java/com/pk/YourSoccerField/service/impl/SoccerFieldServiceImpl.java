package com.pk.YourSoccerField.service.impl;

import com.pk.YourSoccerField.exception.ErrorCode;
import com.pk.YourSoccerField.exception.MissingEntityException;
import com.pk.YourSoccerField.model.Address;
import com.pk.YourSoccerField.model.SoccerField;
import com.pk.YourSoccerField.model.Surface;
import com.pk.YourSoccerField.repository.AddressRepository;
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
    private AddressRepository addressRepository;
    private BaseToDTO<SoccerField, SoccerFieldDTO> soccerFieldToDTO;
    private BaseFromDTO<SoccerField, SoccerFieldDTO> soccerFieldFromDTO;

    @Autowired
    public SoccerFieldServiceImpl(
            SoccerFieldRepository soccerFieldRepository,
            AddressRepository addressRepository) {
        this.soccerFieldRepository = soccerFieldRepository;
        this.addressRepository = addressRepository;
        setSoccerFieldMapper();
    }

    private void setSoccerFieldMapper() {
        this.soccerFieldToDTO = entity -> {
            SoccerFieldDTO soccerFieldDTO = new SoccerFieldDTO();
            soccerFieldDTO.setId(entity.getId());
            soccerFieldDTO.setName(entity.getName());
            soccerFieldDTO.setAddressId(
                    Objects.requireNonNull(entity.getAddress()).getId());
            soccerFieldDTO.setSurfaceId(
                    Objects.requireNonNull(entity.getSurface()).getId());
            soccerFieldDTO.setWidth(entity.getWidth());
            soccerFieldDTO.setLength(entity.getLength());
            soccerFieldDTO.setPrice(
                    entity.getPrice() != null ? entity.getPrice().toString() : null);
            soccerFieldDTO.setLighting(entity.isLighting());
            soccerFieldDTO.setFenced(entity.isFenced());
            soccerFieldDTO.setLockerRom(entity.isLockerRoom());
            soccerFieldDTO.setDescription(entity.getDescription());

            return soccerFieldDTO;
        };

        this.soccerFieldFromDTO = dto -> new SoccerField(
                dto.getId(),
                dto.getName(),
                this.getAddressById(dto.getAddressId()),
                this.getSurfaceById(dto.getSurfaceId()),
                dto.getWidth(),
                dto.getLength(),
                dto.getPrice() != null ? new BigDecimal(dto.getPrice()) : null,
                dto.isLighting(),
                dto.isFenced(),
                dto.isLockerRom(),
                dto.getDescription(),
                new ArrayList<>()
        );
    }

    private Address getAddressById(Long addressId) {
        return this.addressRepository
                .findById(addressId)
                .orElseThrow(() -> new MissingEntityException(
                        "Cannot find address with id " + addressId,
                        ErrorCode.NOT_FOUND_BY_ID
                ));
    }

    private Surface getSurfaceById(Long surfaceId) {
        return this.soccerFieldRepository
                .findSurfaceById(surfaceId)
                .orElseThrow(() -> new MissingEntityException(
                        "Cannot find surface with id " + surfaceId,
                        ErrorCode.NOT_FOUND_BY_ID
                ));
    }

    @Override
    public List<SoccerFieldDTO> getAll() {
        return new ArrayList<>(
                this.soccerFieldToDTO.mapAllFromEntities(this.soccerFieldRepository.findAll()));
    }

    @Override
    public SoccerFieldDTO createSoccerField(SoccerFieldDTO soccerFieldDTO) {
        SoccerField soccerField = this.soccerFieldFromDTO.createFromDTO(soccerFieldDTO);

        return this.soccerFieldToDTO
                .createFromEntity(this.soccerFieldRepository.save(soccerField));
    }
}
