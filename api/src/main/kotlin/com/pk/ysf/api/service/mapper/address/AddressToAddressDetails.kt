package com.pk.ysf.api.service.mapper.address

import com.pk.ysf.api.model.dto.AddressDetails
import com.pk.ysf.api.model.entity.Address
import com.pk.ysf.api.service.mapper.BaseMapper
import org.springframework.stereotype.Component

@Component
class AddressToAddressDetails : BaseMapper<Address, AddressDetails> {

    override fun map(from: Address): AddressDetails =
            AddressDetails(
                    from.id,
                    from.city,
                    from.street,
                    from.apartmentNumber
            )

}