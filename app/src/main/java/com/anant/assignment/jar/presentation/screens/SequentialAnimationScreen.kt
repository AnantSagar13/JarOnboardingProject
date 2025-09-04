package com.anant.assignment.jar.presentation.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import coil.compose.AsyncImage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anant.assignment.jar.data.models.EducationData
import com.anant.assignment.jar.presentation.components.CircularCtaButton
import com.anant.assignment.jar.presentation.components.SequentialEducationCard
import com.anant.assignment.jar.presentation.models.OnboardingUiState
import com.anant.assignment.jar.presentation.viewmodel.OnboardingViewModel
import kotlinx.coroutines.delay
import android.util.Log

enum class SequentialAnimationState {
    WELCOME, WELCOME_FADE_OUT, HEADER_FADE_IN,
    CARD_1_SLIDE_UP_EXPANDED, CARD_1_VISIBLE_EXPANDED, CARD_1_COLLAPSED,
    CARD_2_SLIDE_UP_EXPANDED, CARD_2_VISIBLE_EXPANDED, CARD_2_COLLAPSED,
    CARD_3_SLIDE_UP_EXPANDED, CARD_3_VISIBLE_EXPANDED, ANIMATION_COMPLETE
}

@Composable
fun SequentialAnimationScreen(
    onOnboardingComplete: () -> Unit,
    onNavigateBack: () -> Unit = {},
    shouldRestartAnimation: Boolean = true,
    expandedCardIndex: Int = 2,
    onExpandedCardChange: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val currentState = uiState) {
            is OnboardingUiState.Loading -> Log.d("JarOnboarding", "Loading")
            is OnboardingUiState.Success -> SequentialAnimationContent(
                educationData = currentState.educationData,
                onComplete = onOnboardingComplete,
                onNavigateBack = onNavigateBack,
                shouldRestartAnimation = shouldRestartAnimation,
                expandedCardIndex = expandedCardIndex,
                onExpandedCardChange = onExpandedCardChange
            )
            is OnboardingUiState.Error -> ErrorScreen(
                message = currentState.message,
                onRetry = { viewModel.retry() }
            )
        }
    }
}

@Composable
private fun SequentialAnimationContent(
    educationData: EducationData,
    onComplete: () -> Unit,
    onNavigateBack: () -> Unit,
    shouldRestartAnimation: Boolean,
    expandedCardIndex: Int,
    onExpandedCardChange: (Int) -> Unit
) {
    var animationState by remember { 
        mutableStateOf(
            if (shouldRestartAnimation) SequentialAnimationState.WELCOME 
            else SequentialAnimationState.ANIMATION_COMPLETE
        )
    }
    // expandedCardIndex is now passed as parameter and managed by MainActivity
    var animationKey by remember { mutableStateOf(0) }

    val welcomeAlpha by animateFloatAsState(
        targetValue = when (animationState) {
            SequentialAnimationState.WELCOME -> 1f
            else -> 0f
        },
        animationSpec = tween(durationMillis = 800, easing = EaseInOut),
        label = "welcome_alpha"
    )

    val headerAlpha by animateFloatAsState(
        targetValue = when (animationState) {
            SequentialAnimationState.HEADER_FADE_IN,
            SequentialAnimationState.CARD_1_SLIDE_UP_EXPANDED,
            SequentialAnimationState.CARD_1_VISIBLE_EXPANDED,
            SequentialAnimationState.CARD_1_COLLAPSED,
            SequentialAnimationState.CARD_2_SLIDE_UP_EXPANDED,
            SequentialAnimationState.CARD_2_VISIBLE_EXPANDED,
            SequentialAnimationState.CARD_2_COLLAPSED,
            SequentialAnimationState.CARD_3_SLIDE_UP_EXPANDED,
            SequentialAnimationState.CARD_3_VISIBLE_EXPANDED,
            SequentialAnimationState.ANIMATION_COMPLETE -> 1f
            else -> 0f
        },
        animationSpec = tween(durationMillis = 800, easing = EaseInOut),
        label = "header_alpha"
    )

    val card1OffsetY by animateFloatAsState(
        targetValue = when (animationState) {
            SequentialAnimationState.CARD_1_SLIDE_UP_EXPANDED,
            SequentialAnimationState.CARD_1_VISIBLE_EXPANDED,
            SequentialAnimationState.CARD_1_COLLAPSED,
            SequentialAnimationState.CARD_2_SLIDE_UP_EXPANDED,
            SequentialAnimationState.CARD_2_VISIBLE_EXPANDED,
            SequentialAnimationState.CARD_2_COLLAPSED,
            SequentialAnimationState.CARD_3_SLIDE_UP_EXPANDED,
            SequentialAnimationState.CARD_3_VISIBLE_EXPANDED,
            SequentialAnimationState.ANIMATION_COMPLETE -> 0f
            else -> 1000f
        },
        animationSpec = tween(durationMillis = 800, easing = EaseOutCubic),
        label = "card1_offset"
    )

    val card2OffsetY by animateFloatAsState(
        targetValue = when (animationState) {
            SequentialAnimationState.CARD_2_SLIDE_UP_EXPANDED,
            SequentialAnimationState.CARD_2_VISIBLE_EXPANDED,
            SequentialAnimationState.CARD_2_COLLAPSED,
            SequentialAnimationState.CARD_3_SLIDE_UP_EXPANDED,
            SequentialAnimationState.CARD_3_VISIBLE_EXPANDED,
            SequentialAnimationState.ANIMATION_COMPLETE -> 0f
            else -> 1000f
        },
        animationSpec = tween(durationMillis = 800, easing = EaseOutCubic),
        label = "card2_offset"
    )

    val card3OffsetY by animateFloatAsState(
        targetValue = when (animationState) {
            SequentialAnimationState.CARD_3_SLIDE_UP_EXPANDED,
            SequentialAnimationState.CARD_3_VISIBLE_EXPANDED,
            SequentialAnimationState.ANIMATION_COMPLETE -> 0f
            else -> 1000f
        },
        animationSpec = tween(durationMillis = 800, easing = EaseOutCubic),
        label = "card3_offset"
    )

    val currentActiveCard = when {
        animationState == SequentialAnimationState.ANIMATION_COMPLETE -> {
            if (expandedCardIndex >= 0) {
                educationData.educationCardList.getOrNull(expandedCardIndex)
            } else {
                null
            }
        }
        animationState == SequentialAnimationState.CARD_1_SLIDE_UP_EXPANDED ||
                animationState == SequentialAnimationState.CARD_1_VISIBLE_EXPANDED ||
                animationState == SequentialAnimationState.CARD_1_COLLAPSED -> educationData.educationCardList.getOrNull(0)
        animationState == SequentialAnimationState.CARD_2_SLIDE_UP_EXPANDED ||
                animationState == SequentialAnimationState.CARD_2_VISIBLE_EXPANDED ||
                animationState == SequentialAnimationState.CARD_2_COLLAPSED -> educationData.educationCardList.getOrNull(1)
        animationState == SequentialAnimationState.CARD_3_SLIDE_UP_EXPANDED ||
                animationState == SequentialAnimationState.CARD_3_VISIBLE_EXPANDED -> educationData.educationCardList.getOrNull(2)
        else -> null
    }

    val initialBackgroundColor = Color(0xFF201929)
    val backgroundStartColor by animateColorAsState(
        targetValue = when (animationState) {
            SequentialAnimationState.WELCOME,
            SequentialAnimationState.WELCOME_FADE_OUT,
            SequentialAnimationState.HEADER_FADE_IN -> initialBackgroundColor
            else -> currentActiveCard?.let {
                Color(android.graphics.Color.parseColor(it.startGradient))
            } ?: initialBackgroundColor
        },
        animationSpec = tween(durationMillis = 1000, easing = EaseInOut),
        label = "bg_start_color"
    )

    val backgroundEndColor by animateColorAsState(
        targetValue = when (animationState) {
            SequentialAnimationState.WELCOME,
            SequentialAnimationState.WELCOME_FADE_OUT,
            SequentialAnimationState.HEADER_FADE_IN -> initialBackgroundColor
            else -> currentActiveCard?.let {
                Color(android.graphics.Color.parseColor(it.endGradient))
            } ?: initialBackgroundColor
        },
        animationSpec = tween(durationMillis = 1000, easing = EaseInOut),
        label = "bg_end_color"
    )

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(backgroundStartColor, backgroundEndColor)
    )

    LaunchedEffect(animationKey) {
        // Only run animation sequence if we should restart
        if (!shouldRestartAnimation && animationKey == 0) return@LaunchedEffect
        
        // Reset to welcome state when restarting
        animationState = SequentialAnimationState.WELCOME
        delay(2000)
        animationState = SequentialAnimationState.WELCOME_FADE_OUT
        delay(800)
        animationState = SequentialAnimationState.HEADER_FADE_IN
        delay(800)
        animationState = SequentialAnimationState.CARD_1_SLIDE_UP_EXPANDED
        delay(educationData.bottomToCenterTranslationInterval.toLong())
        animationState = SequentialAnimationState.CARD_1_VISIBLE_EXPANDED
        delay(educationData.expandCardStayInterval.toLong())
        animationState = SequentialAnimationState.CARD_1_COLLAPSED
        delay(educationData.collapseExpandIntroInterval.toLong())
        animationState = SequentialAnimationState.CARD_2_SLIDE_UP_EXPANDED
        delay(educationData.bottomToCenterTranslationInterval.toLong())
        animationState = SequentialAnimationState.CARD_2_VISIBLE_EXPANDED
        delay(educationData.expandCardStayInterval.toLong())
        animationState = SequentialAnimationState.CARD_2_COLLAPSED
        delay(educationData.collapseExpandIntroInterval.toLong())
        animationState = SequentialAnimationState.CARD_3_SLIDE_UP_EXPANDED
        delay(educationData.bottomToCenterTranslationInterval.toLong())
        animationState = SequentialAnimationState.CARD_3_VISIBLE_EXPANDED
        delay(educationData.collapseCardTiltInterval.toLong())
        animationState = SequentialAnimationState.ANIMATION_COMPLETE
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = welcomeAlpha),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = educationData.introTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEEEBF5),
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = educationData.introSubtitle,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF8DC83),
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                    AsyncImage(
                        model = educationData.introSubtitleIcon,
                        contentDescription = "Lightning Icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .graphicsLayer(alpha = headerAlpha)
            ) {
                IconButton(
                    onClick = {
                        // Restart animation from the beginning
                        animationState = SequentialAnimationState.WELCOME
                        onExpandedCardChange(2) // Reset to Card 3 as default expanded
                        animationKey++ // This will trigger LaunchedEffect to restart
                    },
                    modifier = Modifier
                        .size(20.dp)
                        .offset(x = 16.dp, y = 62.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Row(
                    modifier = Modifier.offset(x = 52.dp, y = 60.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = educationData.toolBarText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 24.sp,
                        maxLines = 1
                    )
                    AsyncImage(
                        model = educationData.toolBarIcon,
                        contentDescription = "Toolbar Icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height((-18).dp))

            if (animationState.ordinal >= SequentialAnimationState.CARD_1_SLIDE_UP_EXPANDED.ordinal) {
                educationData.educationCardList.getOrNull(0)?.let { card ->
                    SequentialEducationCard(
                        card = card,
                        isExpanded = if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                            expandedCardIndex == 0
                        } else {
                            animationState == SequentialAnimationState.CARD_1_SLIDE_UP_EXPANDED ||
                                    animationState == SequentialAnimationState.CARD_1_VISIBLE_EXPANDED
                        },
                        onClick = {
                            if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                                Log.d("JarOnboarding", "Card 1 clicked - ${educationData.actionText}")
                                onExpandedCardChange(0)
                            }
                        },
                        modifier = Modifier.graphicsLayer(translationY = card1OffsetY)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (animationState.ordinal >= SequentialAnimationState.CARD_2_SLIDE_UP_EXPANDED.ordinal) {
                educationData.educationCardList.getOrNull(1)?.let { card ->
                    SequentialEducationCard(
                        card = card,
                        isExpanded = if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                            expandedCardIndex == 1
                        } else {
                            animationState == SequentialAnimationState.CARD_2_SLIDE_UP_EXPANDED ||
                                    animationState == SequentialAnimationState.CARD_2_VISIBLE_EXPANDED
                        },
                        onClick = {
                            if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                                Log.d("JarOnboarding", "Card 2 clicked - ${educationData.actionText}")
                                onExpandedCardChange(1)
                            }
                        },
                        modifier = Modifier.graphicsLayer(translationY = card2OffsetY)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (animationState.ordinal >= SequentialAnimationState.CARD_3_SLIDE_UP_EXPANDED.ordinal) {
                educationData.educationCardList.getOrNull(2)?.let { card ->
                    SequentialEducationCard(
                        card = card,
                        isExpanded = if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                            expandedCardIndex == 2
                        } else {
                            animationState == SequentialAnimationState.CARD_3_SLIDE_UP_EXPANDED ||
                                    animationState == SequentialAnimationState.CARD_3_VISIBLE_EXPANDED
                        },
                        onClick = {
                            if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                                Log.d("JarOnboarding", "Card 3 clicked - ${educationData.actionText}")
                                onExpandedCardChange(2)
                            }
                        },
                        modifier = Modifier.graphicsLayer(translationY = card3OffsetY)
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

        if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                CircularCtaButton(
                    saveButtonCta = educationData.saveButtonCta,
                    ctaLottieUrl = educationData.ctaLottie,
                    onClick = {
                        Log.d("JarOnboarding", "CTA Button clicked - ${educationData.saveButtonCta.text}")
                        if (educationData.saveButtonCta.deeplink != null) {
                            Log.d("JarOnboarding", "Deeplink: ${educationData.saveButtonCta.deeplink}")
                        }
                        onComplete()
                    },
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF201929)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color(0xFFF8DC83))
    }
}

@Composable
private fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFF201929)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Something went wrong",
                color = Color(0xFFEEEBF5),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = message,
                color = Color(0xFFEEEBF5).copy(alpha = 0.7f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF8DC83))
            ) {
                Text(
                    text = "Try Again",
                    color = Color(0xFF201929),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}