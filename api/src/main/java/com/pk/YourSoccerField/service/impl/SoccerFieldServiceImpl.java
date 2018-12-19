package com.pk.YourSoccerField.service.impl;

import com.google.gson.Gson;
import com.pk.YourSoccerField.exception.ErrorCode;
import com.pk.YourSoccerField.exception.MissingEntityException;
import com.pk.YourSoccerField.model.Address;
import com.pk.YourSoccerField.model.SoccerField;
import com.pk.YourSoccerField.model.Surface;
import com.pk.YourSoccerField.repository.AddressRepository;
import com.pk.YourSoccerField.repository.SoccerFieldRepository;
import com.pk.YourSoccerField.service.SoccerFieldService;
import com.pk.YourSoccerField.service.dtoModel.SearchModel;
import com.pk.YourSoccerField.service.dtoModel.SoccerFieldDTO;
import com.pk.YourSoccerField.service.dtoModel.SurfaceDTO;
import com.pk.YourSoccerField.service.mapper.BaseFromDTO;
import com.pk.YourSoccerField.service.mapper.BaseToDTO;
import com.pk.YourSoccerField.util.SearchFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SoccerFieldServiceImpl implements SoccerFieldService {

    private SoccerFieldRepository soccerFieldRepository;
    private AddressRepository addressRepository;
    private BaseToDTO<SoccerField, SoccerFieldDTO> soccerFieldToDTO;
    private BaseFromDTO<SoccerField, SoccerFieldDTO> soccerFieldFromDTO;
    private BaseToDTO<Surface, SurfaceDTO> surfaceToDTO;
    private SearchFactory searchFactory;

    @Autowired
    public SoccerFieldServiceImpl(
            SoccerFieldRepository soccerFieldRepository,
            AddressRepository addressRepository,
            SearchFactory searchFactory) {

        this.soccerFieldRepository = soccerFieldRepository;
        this.addressRepository = addressRepository;
        this.searchFactory = searchFactory;
        setSoccerFieldMapper();
        setSurfaceMapper();
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
            soccerFieldDTO.setLockerRoom(entity.isLockerRoom());
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
                dto.isLockerRoom(),
                dto.getDescription(),
                new ArrayList<>()
        );
    }

    private void setSurfaceMapper() {
        this.surfaceToDTO = entity -> {
            SurfaceDTO surfaceDTO = new SurfaceDTO();
            surfaceDTO.setId(entity.getId());
            surfaceDTO.setName(entity.getName());

            return surfaceDTO;
        };
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
                this.soccerFieldToDTO.mapAllFromEntities(this.soccerFieldRepository.findAll())
        );
    }

    @Override
    public SoccerFieldDTO createSoccerField(SoccerFieldDTO soccerFieldDTO) {
        SoccerField soccerField = this.soccerFieldFromDTO.createFromDTO(soccerFieldDTO);

        return this.soccerFieldToDTO
                .createFromEntity(this.soccerFieldRepository.save(soccerField));
    }

    @Override
    public List<SoccerFieldDTO> getByAddressContains(String street) {
        List<SoccerField> soccerFields = this.soccerFieldRepository
                .findByAddressContains(street);

        return new ArrayList<>(
                this.soccerFieldToDTO.mapAllFromEntities(soccerFields)
        );
    }

    @Override
    public List<SoccerFieldDTO> getExampleTen() {
        return new ArrayList<>(
                this.soccerFieldToDTO.mapAllFromEntities(
                        this.soccerFieldRepository.findExampleTen()
                )
        );
    }

    @Override
    public List<SoccerFieldDTO> getByCustomCriteria(String encodedObject) {
        SearchModel searchModel = this.parseToModel(encodedObject);
        List<SoccerField> soccerFields = this.searchFactory
                .getSoccerFieldByCustomCriteria(searchModel);

        return new ArrayList<>(
                this.soccerFieldToDTO
                        .mapAllFromEntities(searchModel.getSurfaces() != null ?
                                this.filterSurfaces(
                                        searchModel,
                                        soccerFields
                                ) :
                                soccerFields)
        );
    }

    private SearchModel parseToModel(String encodedObject) {
        String decodedObject = new String(Base64.getDecoder().decode(encodedObject));
        Gson gson = new Gson();

        return gson.fromJson(decodedObject, SearchModel.class);
    }

    private Collection<SoccerField> filterSurfaces(SearchModel searchModel, List<SoccerField> soccerFields) {
        List<Surface> surfaces = this.soccerFieldRepository
                .findSurfaceByIdIn(searchModel.getSurfaces());

        return soccerFields.stream()
                .filter(soccerField -> surfaces.contains(soccerField.getSurface()))
                .collect(Collectors.toList());
    }

    public List<SurfaceDTO> getAllSurface() {
        List<Surface> surfaces = this.soccerFieldRepository.findAllSurface();

        return new ArrayList<>(surfaceToDTO.mapAllFromEntities(surfaces));
    }
}
