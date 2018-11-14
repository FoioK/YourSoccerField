package com.pk.YourSoccerField.repository

import com.pk.YourSoccerField.model.SoccerField
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SoccerFieldRepository : JpaRepository<SoccerField, Long>