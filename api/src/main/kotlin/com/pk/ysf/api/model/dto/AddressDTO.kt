package com.pk.ysf.api.model.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class AddressDTO {

    @field:Positive
    var id: Long? = null

    @field:NotNull
    var city: String? = null

    @field:NotNull
    var street: String? = null

    @field:NotNull
    var apartmentNumber: String? = null
}
