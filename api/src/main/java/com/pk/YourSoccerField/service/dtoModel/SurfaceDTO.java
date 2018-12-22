package com.pk.YourSoccerField.service.dtoModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class SurfaceDTO {

    @Positive
    private Long id;

    @NotNull
    private String name;

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
}
