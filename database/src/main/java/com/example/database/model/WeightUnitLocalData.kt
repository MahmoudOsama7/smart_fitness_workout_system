package com.example.database.model

enum class WeightUnitLocalData {
    KG, LBS
}

object WeightUnitLocalConverter {
    private const val KG_TO_LBS_RATIO = 2.20462

    fun formatWeight(weightInKg: Double, unit: WeightUnitLocalData): String {
        return when (unit) {
            WeightUnitLocalData.KG -> "%.1f kg".format(weightInKg)
            WeightUnitLocalData.LBS -> "%.1f lbs".format(weightInKg * KG_TO_LBS_RATIO)
        }
    }
}