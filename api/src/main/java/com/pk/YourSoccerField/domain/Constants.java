package com.pk.YourSoccerField.domain;

public enum Constants {

    USER_ROLE("user");

    private String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}