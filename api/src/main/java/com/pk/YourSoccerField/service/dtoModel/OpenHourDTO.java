package com.pk.YourSoccerField.service.dtoModel;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class OpenHourDTO {

    private Long id;

    @NotNull
    private LocalTime s1;
    @NotNull
    private LocalTime e1;

    @NotNull
    private LocalTime s2;
    @NotNull
    private LocalTime e2;

    @NotNull
    private LocalTime s3;
    @NotNull
    private LocalTime e3;

    @NotNull
    private LocalTime s4;
    @NotNull
    private LocalTime e4;

    @NotNull
    private LocalTime s5;
    @NotNull
    private LocalTime e5;

    @NotNull
    private LocalTime s6;
    @NotNull
    private LocalTime e6;

    @NotNull
    private LocalTime s7;
    @NotNull
    private LocalTime e7;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getS1() {
        return s1;
    }

    public void setS1(LocalTime s1) {
        this.s1 = s1;
    }

    public LocalTime getE1() {
        return e1;
    }

    public void setE1(LocalTime e1) {
        this.e1 = e1;
    }

    public LocalTime getS2() {
        return s2;
    }

    public void setS2(LocalTime s2) {
        this.s2 = s2;
    }

    public LocalTime getE2() {
        return e2;
    }

    public void setE2(LocalTime e2) {
        this.e2 = e2;
    }

    public LocalTime getS3() {
        return s3;
    }

    public void setS3(LocalTime s3) {
        this.s3 = s3;
    }

    public LocalTime getE3() {
        return e3;
    }

    public void setE3(LocalTime e3) {
        this.e3 = e3;
    }

    public LocalTime getS4() {
        return s4;
    }

    public void setS4(LocalTime s4) {
        this.s4 = s4;
    }

    public LocalTime getE4() {
        return e4;
    }

    public void setE4(LocalTime e4) {
        this.e4 = e4;
    }

    public LocalTime getS5() {
        return s5;
    }

    public void setS5(LocalTime s5) {
        this.s5 = s5;
    }

    public LocalTime getE5() {
        return e5;
    }

    public void setE5(LocalTime e5) {
        this.e5 = e5;
    }

    public LocalTime getS6() {
        return s6;
    }

    public void setS6(LocalTime s6) {
        this.s6 = s6;
    }

    public LocalTime getE6() {
        return e6;
    }

    public void setE6(LocalTime e6) {
        this.e6 = e6;
    }

    public LocalTime getS7() {
        return s7;
    }

    public void setS7(LocalTime s7) {
        this.s7 = s7;
    }

    public LocalTime getE7() {
        return e7;
    }

    public void setE7(LocalTime e7) {
        this.e7 = e7;
    }
}
