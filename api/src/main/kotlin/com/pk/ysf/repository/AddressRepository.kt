package com.pk.ysf.repository

import com.pk.ysf.apimodels.entity.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long>