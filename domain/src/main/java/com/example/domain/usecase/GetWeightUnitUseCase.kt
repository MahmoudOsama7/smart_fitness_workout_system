package com.example.domain.usecase


import com.example.domain.model.WeightUnit
import com.example.domain.repository.UserSettingsRepository
import javax.inject.Inject

class GetWeightUnitUseCase @Inject constructor(
    private val repository: UserSettingsRepository
) {
    operator fun invoke(): WeightUnit {
        return repository.getWeightUnit()
    }
}