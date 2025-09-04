package com.anant.assignment.jar.domain.repository

import com.anant.assignment.jar.data.models.EducationData
import com.anant.assignment.jar.network.NetworkModule
import com.anant.assignment.jar.utils.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EducationRepository {
    private val apiService = NetworkModule.educationApiService
    
    suspend fun getEducationData(): Result<EducationData> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getEducationMetadata()
            if (response.success) {
                Result.success(response.data.manualBuyEducationData)
            } else {
                Result.failure(Exception("API returned unsuccessful response"))
            }
        } catch (e: Exception) {
            val errorMessage = NetworkUtil.getNetworkErrorMessage(e)
            Result.failure(Exception(errorMessage))
        }
    }
}