package com.pk.ysf.apimodels.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.CONFLICT)
class DuplicateEntityException(message: String) : RuntimeException(message)