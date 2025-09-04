package com.anant.assignment.jar.data.models

import com.google.gson.annotations.SerializedName

data class EducationMetadata(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: EducationDataWrapper
)

data class EducationDataWrapper(
    @SerializedName("manualBuyEducationData")
    val manualBuyEducationData: EducationData
)