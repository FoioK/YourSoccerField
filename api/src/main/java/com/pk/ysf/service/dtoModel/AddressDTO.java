package com.pk.ysf.service.dtoModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AddressDTO {

    @Positive
    private Long id;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @NotNull
    private String apartmentNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}
