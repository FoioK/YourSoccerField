package com.pk.ysf.repository

import com.pk.ysf.apimodels.model.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long>