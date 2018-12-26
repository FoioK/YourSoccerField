package com.pk.YourSoccerField.service.dtoModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class SoccerFieldDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private AddressDTO address;

    @NotNull
    private SurfaceDTO surface;

    @NotNull
    @Positive
    private Integer width;

    @NotNull
    @Positive
    private Integer length;
    private String price;

    private boolean isLighting;
    private boolean isFenced;
    private boolean isLockerRoom;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public SurfaceDTO getSurface() {
        return surface;
    }

    public void setSurface(SurfaceDTO surface) {
        this.surface = surface;
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

    public boolean isLockerRoom() {
        return isLockerRoom;
    }

    public void setLockerRoom(boolean lockerRoom) {
        isLockerRoom = lockerRoom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
