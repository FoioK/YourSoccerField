package com.pk.YourSoccerField.repository

import com.pk.YourSoccerField.model.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long>