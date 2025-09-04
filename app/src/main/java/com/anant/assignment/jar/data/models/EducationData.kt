package com.anant.assignment.jar.data.models

import com.google.gson.annotations.SerializedName

data class EducationData(
    @SerializedName("toolBarText")
    val toolBarText: String,
    @SerializedName("introTitle")
    val introTitle: String,
    @SerializedName("introSubtitle")
    val introSubtitle: String,
    @SerializedName("educationCardList")
    val educationCardList: List<EducationCard>,
    @SerializedName("saveButtonCta")
    val saveButtonCta: SaveButtonCta,
    @SerializedName("ctaLottie")
    val ctaLottie: String
)