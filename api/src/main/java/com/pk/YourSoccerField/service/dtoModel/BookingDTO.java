package com.pk.YourSoccerField.service.dtoModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class BookingDTO {

    private Long id;

    @Positive
    private Long userCode;

    @NotBlank
    private String startDate;

    @NotBlank
    private String executionTime;

    @Positive
    private Long soccerField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public Long getSoccerField() {
        return soccerField;
    }

    public void setSoccerField(Long soccerField) {
        this.soccerField = soccerField;
    }
}
