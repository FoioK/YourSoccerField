package com.pk.ysf.util;

public enum  Permissions {

    // ADMIN
    USERS_ADMIN_PANE("USERS_ADMIN_PANE"),

    // ADMIN
    SOCCERFIELDS_GET_ALL("SOCCERFIELDS_GET_ALL"),
    // ADMIN
    SOCCERFIELDS_POST_CREATE("SOCCERFIELDS_POST_CREATE"),
    // ADMIN, USER
    SOCCERFIELDS_GET_BY_ID("SOCCERFIELDS_GET_BY_ID"),
    // ADMIN
    SOCCERFIELDS_PUT_UPDATE("SOCCERFIELDS_PUT_UPDATE"),

    // ADMIN, USER
    BOOKINGS_POST_CREATE("BOOKINGS_POST_CREATE");

    private String permission;

    Permissions(String permission) {
        this.permission = permission;
    }
}
