package com.example.domain.repository

import com.example.domain.model.WeightUnit

interface UserSettingsRepository {
    fun getWeightUnit(): WeightUnit
    fun setWeightUnit(unit: WeightUnit)
}