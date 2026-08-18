package com.example.data.mapper

import com.example.database.model.WeightUnitLocalData
import com.example.domain.model.WeightUnit

fun WeightUnit.toLocalData(): WeightUnitLocalData {
    return when (this) {
        WeightUnit.KG -> WeightUnitLocalData.KG
        WeightUnit.LBS -> WeightUnitLocalData.LBS
    }
}

fun WeightUnitLocalData.toData(): WeightUnit {
    return when (this) {
        WeightUnitLocalData.KG -> WeightUnit.KG
        WeightUnitLocalData.LBS -> WeightUnit.LBS
    }
}