package com.example.data.repository

import com.example.data.mapper.toData
import com.example.data.mapper.toLocalData
import com.example.database.prefrence.PreferenceHelper
import com.example.domain.model.WeightUnit
import com.example.domain.repository.UserSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSettingsRepositoryImpl @Inject constructor(
    private val preferenceHelper: PreferenceHelper
) : UserSettingsRepository {

    override fun getWeightUnit(): WeightUnit {
        return preferenceHelper.getSavedWeightUnit().toData()
    }

    override fun setWeightUnit(unit: WeightUnit) {
        preferenceHelper.setWeightUnit(unit.toLocalData())
    }
}