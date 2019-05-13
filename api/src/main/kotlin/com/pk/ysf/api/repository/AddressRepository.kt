package com.pk.ysf.api.repository

import com.pk.ysf.api.model.entity.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long>