package com.anant.assignment.jar.presentation.models

import com.anant.assignment.jar.data.models.EducationData

sealed class OnboardingUiState {
    object Loading : OnboardingUiState()
    data class Success(val educationData: EducationData) : OnboardingUiState()
    data class Error(val message: String) : OnboardingUiState()
}