package com.pk.ysf.api.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UnactivatedUserException(message: String) : RuntimeException(message)