package com.anant.assignment.jar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anant.assignment.jar.domain.repository.EducationRepository
import com.anant.assignment.jar.presentation.models.OnboardingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {
    private val repository = EducationRepository()
    
    private val _uiState = MutableStateFlow<OnboardingUiState>(OnboardingUiState.Loading)
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()
    
    init {
        loadEducationData()
    }
    
    private fun loadEducationData() {
        viewModelScope.launch {
            _uiState.value = OnboardingUiState.Loading
            repository.getEducationData()
                .onSuccess { educationData ->
                    _uiState.value = OnboardingUiState.Success(educationData)
                }
                .onFailure { exception ->
                    _uiState.value = OnboardingUiState.Error(
                        exception.message ?: "Unknown error occurred"
                    )
                }
        }
    }
    
    fun retry() {
        loadEducationData()
    }
}