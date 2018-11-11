package com.pk.YourSoccerField.exception;

public enum ErrorCode {

    //USER
    INCORRECT_PASSWORD,
    NOT_FOUND_NEXT_USER_CODE,
    UPDATE_NEXT_USER_CODE,
    UNACTIVATED_USER,
    NOT_FOUND_BY_EMAIL,
    DUPLICAT_USER_EMAIL,

    //ROLE
    NOT_FOUND_BY_NAME,

    //USER_ROLE
    INSERT_ERROR,
}
