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
    val ctaLottie: String,
    @SerializedName("collapseCardTiltInterval")
    val collapseCardTiltInterval: Int,
    @SerializedName("collapseExpandIntroInterval")
    val collapseExpandIntroInterval: Int,
    @SerializedName("bottomToCenterTranslationInterval")
    val bottomToCenterTranslationInterval: Int,
    @SerializedName("expandCardStayInterval")
    val expandCardStayInterval: Int,
    @SerializedName("screenType")
    val screenType: String,
    @SerializedName("cohort")
    val cohort: String,
    @SerializedName("combination")
    val combination: String?,
    @SerializedName("seenCount")
    val seenCount: Int?,
    @SerializedName("actionText")
    val actionText: String,
    @SerializedName("shouldShowOnLandingPage")
    val shouldShowOnLandingPage: Boolean,
    @SerializedName("toolBarIcon")
    val toolBarIcon: String,
    @SerializedName("introSubtitleIcon")
    val introSubtitleIcon: String,
    @SerializedName("shouldShowBeforeNavigating")
    val shouldShowBeforeNavigating: Boolean
)