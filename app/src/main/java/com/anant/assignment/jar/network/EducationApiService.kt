package com.anant.assignment.jar.network

import com.anant.assignment.jar.data.models.EducationMetadata
import retrofit2.http.GET

interface EducationApiService {
    @GET("_assets/shared/education-metadata.json")
    suspend fun getEducationMetadata(): EducationMetadata
    
    companion object {
        const val BASE_URL = "https://myjar.app/"
    }
}