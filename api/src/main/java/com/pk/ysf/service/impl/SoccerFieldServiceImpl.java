package com.pk.ysf.service.impl;

import com.google.gson.Gson;
import com.pk.ysf.api.model.entity.Address;
import com.pk.ysf.api.model.entity.OpenHour;
import com.pk.ysf.api.model.entity.SoccerField;
import com.pk.ysf.api.model.entity.Surface;
import com.pk.ysf.api.model.entity.Booking;
import com.pk.ysf.api.repository.AddressRepository;
import com.pk.ysf.api.repository.BookingRepository;
import com.pk.ysf.api.repository.SoccerFieldRepository;
import com.pk.ysf.api.model.exception.MissingEntityException;
import com.pk.ysf.service.SoccerFieldService;
import com.pk.ysf.service.dtoModel.*;
import com.pk.ysf.service.mapper.BaseFromDTO;
import com.pk.ysf.service.mapper.BaseToDTO;
import com.pk.ysf.service.mapper.impl.BookingToDTO;
import com.pk.ysf.util.SearchFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SoccerFieldServiceImpl implements SoccerFieldService {

    private SoccerFieldRepository soccerFieldRepository;
    private AddressRepository addressRepository;
    private BookingRepository bookingRepository;

    private BookingToDTO bookingToDTO;

    private BaseToDTO<SoccerField, SoccerFieldDTO> soccerFieldToDTO;
    private BaseFromDTO<SoccerField, SoccerFieldDTO> soccerFieldFromDTO;
    private BaseToDTO<Surface, SurfaceDTO> surfaceToDTO;
    private BaseFromDTO<Surface, SurfaceDTO> surfaceFromDTO;
    private BaseToDTO<Address, AddressDTO> addressToDTO;
    private BaseFromDTO<Address, AddressDTO> addressFromDTO;
    private BaseToDTO<OpenHour, OpenHourDTO> openHourToDTO;
    private BaseFromDTO<OpenHour, OpenHourDTO> openHourFromDTO;

    private SearchFactory searchFactory;

    @Autowired
    public SoccerFieldServiceImpl(
            SoccerFieldRepository soccerFieldRepository,
            AddressRepository addressRepository,
            BookingRepository bookingRepository,
            BookingToDTO bookingToDTO,
            SearchFactory searchFactory) {

        this.soccerFieldRepository = soccerFieldRepository;
        this.addressRepository = addressRepository;
        this.bookingRepository = bookingRepository;
        this.bookingToDTO = bookingToDTO;
        this.searchFactory = searchFactory;
        setSoccerFieldMapper();
        setSurfaceMapper();
        setAddressMapper();
        setOpenHourMapper();
    }

    private void setSoccerFieldMapper() {
        this.soccerFieldToDTO = entity -> {
            SoccerFieldDTO soccerFieldDTO = new SoccerFieldDTO();
            soccerFieldDTO.setId(entity.getId());
            soccerFieldDTO.setName(entity.getName());
            soccerFieldDTO.setAddress(this.addressToDTO.createFromEntity(entity.getAddress()));
            soccerFieldDTO.setSurface(this.surfaceToDTO.createFromEntity(entity.getSurface()));
            soccerFieldDTO.setWidth(entity.getWidth());
            soccerFieldDTO.setLength(entity.getLength());
            soccerFieldDTO.setPrice(
                    entity.getPrice() != null ? entity.getPrice().toString() : null);
            soccerFieldDTO.setLighting(entity.isLighting());
            soccerFieldDTO.setFenced(entity.isFenced());
            soccerFieldDTO.setLockerRoom(entity.isLockerRoom());
            soccerFieldDTO.setDescription(entity.getDescription());
            soccerFieldDTO.setOpenHour(this.openHourToDTO.createFromEntity(entity.getOpenHour()));

            return soccerFieldDTO;
        };

        this.soccerFieldFromDTO = dto -> {
            boolean addressExist = dto.getAddress().getId() != null;
            boolean surfaceExist = dto.getSurface().getId() != null;
            boolean openHourExist = dto.getOpenHour().getId() != null;

            return new SoccerField(
                    dto.getId(),
                    dto.getName(),
                    addressExist ?
                            this.getAddressById(dto.getAddress().getId()) :
                            this.addressFromDTO.createFromDTO(dto.getAddress()),
                    surfaceExist ?
                            this.getSurfaceById(dto.getSurface().getId()) :
                            this.surfaceFromDTO.createFromDTO(dto.getSurface()),
                    dto.getWidth(),
                    dto.getLength(),
                    dto.getPrice() != null ? new BigDecimal(dto.getPrice()) : null,
                    dto.isLighting(),
                    dto.isFenced(),
                    dto.isLockerRoom(),
                    dto.getDescription(),
                    new ArrayList<>(),
                    openHourExist ?
                            this.getOpenHourById(dto.getOpenHour().getId()) :
                            this.openHourFromDTO.createFromDTO(dto.getOpenHour())
            );
        };
    }

    private void setSurfaceMapper() {
        this.surfaceToDTO = entity -> {
            SurfaceDTO surfaceDTO = new SurfaceDTO();
            surfaceDTO.setId(entity.getId());
            surfaceDTO.setName(entity.getName());

            return surfaceDTO;
        };

        this.surfaceFromDTO = dto -> new Surface(
                dto.getId(),
                dto.getName(),
                Collections.emptyList()
        );
    }

    private void setAddressMapper() {
        this.addressToDTO = entity -> {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setId(entity.getId());
            addressDTO.setCity(entity.getCity());
            addressDTO.setStreet(entity.getStreet());
            addressDTO.setApartmentNumber(entity.getApartmentNumber());

            return addressDTO;
        };

        this.addressFromDTO = dto -> new Address(
                dto.getId(),
                dto.getCity(),
                dto.getStreet(),
                dto.getApartmentNumber(),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    @SuppressWarnings("Duplicates")
    private void setOpenHourMapper() {
        this.openHourToDTO = entity -> {
            OpenHourDTO openHourDTO = new OpenHourDTO();
            openHourDTO.setId(entity.getId());

            openHourDTO.setS1(entity.getS1());
            openHourDTO.setE1(entity.getE1());

            openHourDTO.setS2(entity.getS2());
            openHourDTO.setE2(entity.getE2());

            openHourDTO.setS3(entity.getS3());
            openHourDTO.setE3(entity.getE3());

            openHourDTO.setS4(entity.getS4());
            openHourDTO.setE4(entity.getE4());

            openHourDTO.setS5(entity.getS5());
            openHourDTO.setE5(entity.getE5());

            openHourDTO.setS6(entity.getS6());
            openHourDTO.setE6(entity.getE6());

            openHourDTO.setS7(entity.getS7());
            openHourDTO.setE7(entity.getE7());

            return openHourDTO;
        };

        this.openHourFromDTO = dto -> {

            return new OpenHour(
                    dto.getId(),
                    dto.getS1(),
                    dto.getE1(),
                    dto.getS2(),
                    dto.getE2(),
                    dto.getS3(),
                    dto.getE3(),
                    dto.getS4(),
                    dto.getE4(),
                    dto.getS5(),
                    dto.getE5(),
                    dto.getS6(),
                    dto.getE6(),
                    dto.getS7(),
                    dto.getE7(),
                    new ArrayList<>()
            );
        };
    }

    private Address getAddressById(Long addressId) {
        return this.addressRepository
                .findById(addressId)
                .orElseThrow(() -> new MissingEntityException("Cannot find address with id " + addressId));
    }

    private Surface getSurfaceById(Long surfaceId) {
        return this.soccerFieldRepository
                .findSurfaceById(surfaceId)
                .orElseThrow(() -> new MissingEntityException("Cannot find surface with id " + surfaceId));
    }

    private OpenHour getOpenHourById(Long openHourId) {
        return this.soccerFieldRepository
                .findOpenHourById(openHourId)
                .orElseThrow(() -> new MissingEntityException("Cannot find open hour with id " + openHourId));
    }

    @Override
    public List<SoccerFieldDTO> getAll() {
        return new ArrayList<>(
                this.soccerFieldToDTO.mapAllFromEntities(this.soccerFieldRepository.findAll())
        );
    }

    @Override
    @Transactional
    public SoccerFieldDTO createSoccerField(SoccerFieldDTO soccerFieldDTO) {
        SoccerField soccerField = this.soccerFieldFromDTO.createFromDTO(soccerFieldDTO);

        return this.soccerFieldToDTO
                .createFromEntity(this.soccerFieldRepository.save(soccerField));
    }

    @Override
    public SoccerFieldDTO getById(Long soccerFieldId) {
        SoccerField soccerField = this.soccerFieldRepository
                .findById(soccerFieldId)
                .orElseThrow(() -> new MissingEntityException("Cannot find soccer field with id " + soccerFieldId));

        return this.soccerFieldToDTO.createFromEntity(soccerField);
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
        List<Long> ids = searchModel.getSurfaces();

        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        List<Surface> surfaces = this.soccerFieldRepository
                .findSurfaceByIdIn(ids);

        return soccerFields.stream()
                .filter(soccerField -> surfaces.contains(soccerField.getSurface()))
                .collect(Collectors.toList());
    }

    public List<SurfaceDTO> getAllSurface() {
        List<Surface> surfaces = this.soccerFieldRepository.findAllSurface();

        return new ArrayList<>(surfaceToDTO.mapAllFromEntities(surfaces));
    }

    @Override
    public List<BookingDTO> getAllBookings(Long soccerFieldId) {
        List<Booking> bookings = this.bookingRepository.findAllBySoccerField(soccerFieldId);

        return new ArrayList<>(this.bookingToDTO.mapAll(bookings));
    }

    @Override
    public Optional<SoccerFieldDTO> updateSoccerField(Long soccerFieldId, SoccerFieldDTO soccerFieldDTO) {
        Optional<SoccerField> soccerFieldById = this.soccerFieldRepository
                .findById(soccerFieldId);

        if (!soccerFieldById.isPresent()) {
            return Optional.ofNullable(this.createSoccerField(soccerFieldDTO));
        }

        SoccerField soccerField = this.soccerFieldFromDTO.createFromDTO(soccerFieldDTO);
        this.soccerFieldRepository.save(soccerField);

        return Optional.empty();
    }

    @Override
    public void deleteSoccerFieldById(Long soccerFieldId) {
        this.soccerFieldRepository.deleteById(soccerFieldId);
    }
}
