package com.pk.YourSoccerField.service.dtoModel;

import java.util.List;

public class SearchModel {

    private String name;
    private List<String> surface;
    private boolean paid;
    private boolean lighting;
    private boolean fenced;
    private boolean lockerRoom;
    private Integer minWidth;
    private Integer maxWidth;
    private Integer minLength;
    private Integer maxLength;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSurface() {
        return surface;
    }

    public void setSurface(List<String> surface) {
        this.surface = surface;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isLighting() {
        return lighting;
    }

    public void setLighting(boolean lighting) {
        this.lighting = lighting;
    }

    public boolean isFenced() {
        return fenced;
    }

    public void setFenced(boolean fenced) {
        this.fenced = fenced;
    }

    public boolean isLockerRoom() {
        return lockerRoom;
    }

    public void setLockerRoom(boolean lockerRoom) {
        this.lockerRoom = lockerRoom;
    }

    public Integer getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(Integer minWidth) {
        this.minWidth = minWidth;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }
}
