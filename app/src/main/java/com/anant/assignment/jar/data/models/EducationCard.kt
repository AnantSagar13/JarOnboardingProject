package com.anant.assignment.jar.data.models

import com.google.gson.annotations.SerializedName

data class EducationCard(
    @SerializedName("image")
    val image: String,
    @SerializedName("collapsedStateText")
    val collapsedStateText: String,
    @SerializedName("expandStateText")
    val expandStateText: String,
    @SerializedName("backGroundColor")
    val backgroundColor: String,
    @SerializedName("strokeStartColor")
    val strokeStartColor: String,
    @SerializedName("strokeEndColor")
    val strokeEndColor: String,
    @SerializedName("startGradient")
    val startGradient: String,
    @SerializedName("endGradient")
    val endGradient: String
)