package com.anant.assignment.jar.data.models

import com.google.gson.annotations.SerializedName

data class SaveButtonCta(
    @SerializedName("text")
    val text: String,
    @SerializedName("deeplink")
    val deeplink: String?,
    @SerializedName("backgroundColor")
    val backgroundColor: String,
    @SerializedName("textColor")
    val textColor: String,
    @SerializedName("strokeColor")
    val strokeColor: String,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("order")
    val order: Int?
)