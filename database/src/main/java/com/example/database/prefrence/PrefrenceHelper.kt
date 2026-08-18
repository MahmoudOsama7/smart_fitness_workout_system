package com.example.database.prefrence

import android.content.SharedPreferences
import com.example.database.model.WeightUnitLocalData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelper @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun setWeightUnit(unit: WeightUnitLocalData) {
        sharedPreferences.edit()
            .putString(KEY_WEIGHT_UNIT, unit.name)
            .apply()
    }

    fun getSavedWeightUnit(): WeightUnitLocalData {
        val savedName = sharedPreferences.getString(KEY_WEIGHT_UNIT, WeightUnitLocalData.KG.name)
        return runCatching { WeightUnitLocalData.valueOf(savedName ?: WeightUnitLocalData.KG.name) }
            .getOrDefault(WeightUnitLocalData.KG)
    }

    companion object {
        private const val KEY_WEIGHT_UNIT = "key_weight_unit"
        const val PREF_NAME = "smart_gym_preferences"
    }
}