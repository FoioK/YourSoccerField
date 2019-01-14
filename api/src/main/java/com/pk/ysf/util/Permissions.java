package com.pk.ysf.util;

public enum  Permissions {

    USERS_ADMIN_PANE("USERS_ADMIN_PANE"),

    SOCCERFIELDS_GET_ALL("SOCCERFIELDS_GET_ALL"),
    SOCCERFIELDS_POST_CREATE("SOCCERFIELDS_POST_CREATE"),
    SOCCERFIELDS_GET_BY_ID("SOCCERFIELDS_GET_BY_ID"),

    BOOKINGS_POST_CREATE("BOOKINGS_POST_CREATE");

    private String permission;

    Permissions(String permission) {
        this.permission = permission;
    }
}
