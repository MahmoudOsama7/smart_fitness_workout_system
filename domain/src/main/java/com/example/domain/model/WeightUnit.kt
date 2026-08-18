package com.example.domain.model

enum class WeightUnit {
    KG, LBS
}

object WeightConverter {
    private const val KG_TO_LBS_RATIO = 2.20462

    fun formatWeight(weightInKg: Double, unit: WeightUnit): String {
        return when (unit) {
            WeightUnit.KG -> "%.1f kg".format(weightInKg)
            WeightUnit.LBS -> "%.1f lbs".format(weightInKg * KG_TO_LBS_RATIO)
        }
    }
}