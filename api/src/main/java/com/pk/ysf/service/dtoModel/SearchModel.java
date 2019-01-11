package com.pk.ysf.service.dtoModel;

import java.util.List;

public class SearchModel {

    private String name;
    private List<Long> surfaces;
    private Boolean paid;
    private Boolean lighting;
    private Boolean fenced;
    private Boolean lockerRoom;
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

    public List<Long> getSurfaces() {
        return surfaces;
    }

    public void setSurfaces(List<Long> surfaces) {
        this.surfaces = surfaces;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getLighting() {
        return lighting;
    }

    public void setLighting(Boolean lighting) {
        this.lighting = lighting;
    }

    public Boolean getFenced() {
        return fenced;
    }

    public void setFenced(Boolean fenced) {
        this.fenced = fenced;
    }

    public Boolean getLockerRoom() {
        return lockerRoom;
    }

    public void setLockerRoom(Boolean lockerRoom) {
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