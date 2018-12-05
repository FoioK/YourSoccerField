package com.pk.YourSoccerField.service.dtoModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SoccerFieldDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Long addressId;

    @NotNull
    private Long surfaceId;
    private Integer width;
    private Integer length;
    private String price;

    @NotNull
    private boolean isLighting;

    @NotNull
    private boolean isFenced;

    @NotNull private boolean isLockerRom;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getSurfaceId() {
        return surfaceId;
    }

    public void setSurfaceId(Long surfaceId) {
        this.surfaceId = surfaceId;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isLighting() {
        return isLighting;
    }

    public void setLighting(boolean lighting) {
        isLighting = lighting;
    }

    public boolean isFenced() {
        return isFenced;
    }

    public void setFenced(boolean fenced) {
        isFenced = fenced;
    }

    public boolean isLockerRom() {
        return isLockerRom;
    }

    public void setLockerRom(boolean lockerRom) {
        isLockerRom = lockerRom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
