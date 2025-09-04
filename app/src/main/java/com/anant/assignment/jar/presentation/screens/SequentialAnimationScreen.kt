package com.anant.assignment.jar.presentation.screens

/**
 * Sequential Card Animation Screen with exact specifications
 *
 * Animation Flow:
 * 1. Welcome screen (same as before)
 * 2. Header fade in (same as before)
 * 3. Card 1 slides up from bottom, expands, then collapses
 * 4. Card 2 slides up below Card 1, expands, then collapses
 * 5. Card 3 slides up below Card 2, expands, then collapses
 *
 * Card positioning:
 * - Card 1: top: 178px, left: 16px
 * - Card 2: below Card 1 with gap
 * - Card 3: below Card 2 with gap
 */

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

/**
 * Sequential animation states for the new flow
 */
enum class SequentialAnimationState {
    WELCOME,                 // Show welcome text
    WELCOME_FADE_OUT,        // Fade out welcome text
    HEADER_FADE_IN,          // Fade in header

    // Card 1 sequence - appears expanded, then collapses
    CARD_1_SLIDE_UP_EXPANDED,    // Card 1 slides up already expanded
    CARD_1_VISIBLE_EXPANDED,     // Card 1 stays expanded for display
    CARD_1_COLLAPSED,            // Card 1 collapses

    // Card 2 sequence - appears expanded, then collapses  
    CARD_2_SLIDE_UP_EXPANDED,    // Card 2 slides up below Card 1, already expanded
    CARD_2_VISIBLE_EXPANDED,     // Card 2 stays expanded for display
    CARD_2_COLLAPSED,            // Card 2 collapses

    // Card 3 sequence - appears expanded and STAYS expanded
    CARD_3_SLIDE_UP_EXPANDED,    // Card 3 slides up below Card 2, already expanded
    CARD_3_VISIBLE_EXPANDED,     // Card 3 stays expanded (final state - no collapse)

    ANIMATION_COMPLETE       // All animations done
}

/**
 * Main sequential animation screen
 */
@Composable
fun SequentialAnimationScreen(
    onOnboardingComplete: () -> Unit,
    onNavigateBack: () -> Unit = {},
    viewModel: OnboardingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (val currentState = uiState) {
            is OnboardingUiState.Loading -> {
                LoadingScreen()
            }
            is OnboardingUiState.Success -> {
                SequentialAnimationContent(
                    educationData = currentState.educationData,
                    onComplete = onOnboardingComplete,
                    onNavigateBack = onNavigateBack
                )
            }
            is OnboardingUiState.Error -> {
                ErrorScreen(
                    message = currentState.message,
                    onRetry = { viewModel.retry() }
                )
            }
        }
    }
}

@Composable
private fun SequentialAnimationContent(
    educationData: EducationData,
    onComplete: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var animationState by remember { mutableStateOf(SequentialAnimationState.WELCOME) }
    var expandedCardIndex by remember { mutableStateOf(2) } // Card 3 (index 2) expanded by default

    // Welcome text animation (same as before)
    val welcomeAlpha by animateFloatAsState(
        targetValue = when (animationState) {
            SequentialAnimationState.WELCOME -> 1f
            else -> 0f
        },
        animationSpec = tween(durationMillis = 800, easing = EaseInOut),
        label = "welcome_alpha"
    )

    // Header animation with updated states
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

    // Individual card animations with updated states
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

    // Background color animation - changes based on expanded card or animation state
    val currentActiveCard = when {
        // After animation complete, use expandedCardIndex for dynamic background
        animationState == SequentialAnimationState.ANIMATION_COMPLETE -> {
            if (expandedCardIndex >= 0) {
                educationData.educationCardList.getOrNull(expandedCardIndex)
            } else {
                null // No card expanded = original background
            }
        }
        // During animation sequence, use animation state
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

    // Updated sequential animation logic
    LaunchedEffect(Unit) {
        // 1. Show welcome for 2 seconds
        delay(2000)

        // 2. Start welcome text fade out
        animationState = SequentialAnimationState.WELCOME_FADE_OUT
        delay(800)

        // 3. Start header fade in
        animationState = SequentialAnimationState.HEADER_FADE_IN
        delay(800)

        // 4. Card 1 sequence - appears expanded first
        animationState = SequentialAnimationState.CARD_1_SLIDE_UP_EXPANDED
        delay(800) // Wait for slide animation

        animationState = SequentialAnimationState.CARD_1_VISIBLE_EXPANDED
        delay(1500) // Keep expanded for display

        animationState = SequentialAnimationState.CARD_1_COLLAPSED
        delay(600) // Wait for collapse animation

        // 5. Card 2 sequence - appears expanded first
        animationState = SequentialAnimationState.CARD_2_SLIDE_UP_EXPANDED
        delay(800)

        animationState = SequentialAnimationState.CARD_2_VISIBLE_EXPANDED
        delay(1500)

        animationState = SequentialAnimationState.CARD_2_COLLAPSED
        delay(600)

        // 6. Card 3 sequence - appears expanded and STAYS expanded
        animationState = SequentialAnimationState.CARD_3_SLIDE_UP_EXPANDED
        delay(800)

        animationState = SequentialAnimationState.CARD_3_VISIBLE_EXPANDED
        // NO COLLAPSE for Card 3 - it stays expanded!
        delay(1000) // Brief pause before completing

        // 7. Animation complete
        animationState = SequentialAnimationState.ANIMATION_COMPLETE
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        // Welcome text (same as before)
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
                Text(
                    text = educationData.introSubtitle,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF8DC83),
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp
                )
            }
        }

        // Entire screen scrollable including header
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header area - now scrollable
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp) // Header height
                    .graphicsLayer(alpha = headerAlpha)
            ) {
                IconButton(
                    onClick = onNavigateBack,
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

                Text(
                    text = educationData.toolBarText,
                    modifier = Modifier.offset(x = 52.dp, y = 60.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 24.sp,
                    maxLines = 1
                )
            }

            // Cards Area with correct top positioning: 112px
            Spacer(modifier = Modifier.height((-18).dp)) // Adjust to get 112dp from top (130-18=112)

            // Card 1
            if (animationState.ordinal >= SequentialAnimationState.CARD_1_SLIDE_UP_EXPANDED.ordinal) {
                educationData.educationCardList.getOrNull(0)?.let { card ->
                    SequentialEducationCard(
                        card = card,
                        isExpanded = if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                            expandedCardIndex == 0
                        } else {
                            // Card 1 is expanded during SLIDE_UP and VISIBLE states, collapsed after
                            animationState == SequentialAnimationState.CARD_1_SLIDE_UP_EXPANDED ||
                                    animationState == SequentialAnimationState.CARD_1_VISIBLE_EXPANDED
                        },
                        onClick = {
                            if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                                expandedCardIndex = 0 // Always ensure this card expands, others collapse
                            }
                        },
                        modifier = Modifier.graphicsLayer(translationY = card1OffsetY)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp)) // gap: 16px between cards
            }

            // Card 2
            if (animationState.ordinal >= SequentialAnimationState.CARD_2_SLIDE_UP_EXPANDED.ordinal) {
                educationData.educationCardList.getOrNull(1)?.let { card ->
                    SequentialEducationCard(
                        card = card,
                        isExpanded = if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                            expandedCardIndex == 1
                        } else {
                            // Card 2 is expanded during SLIDE_UP and VISIBLE states, collapsed after
                            animationState == SequentialAnimationState.CARD_2_SLIDE_UP_EXPANDED ||
                                    animationState == SequentialAnimationState.CARD_2_VISIBLE_EXPANDED
                        },
                        onClick = {
                            if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                                expandedCardIndex = 1 // Always ensure this card expands, others collapse
                            }
                        },
                        modifier = Modifier.graphicsLayer(translationY = card2OffsetY)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp)) // gap: 16px between cards
            }

            // Card 3 - stays expanded after animation
            if (animationState.ordinal >= SequentialAnimationState.CARD_3_SLIDE_UP_EXPANDED.ordinal) {
                educationData.educationCardList.getOrNull(2)?.let { card ->
                    SequentialEducationCard(
                        card = card,
                        isExpanded = if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                            expandedCardIndex == 2
                        } else {
                            // Card 3 stays expanded - no collapse state in animation
                            animationState == SequentialAnimationState.CARD_3_SLIDE_UP_EXPANDED ||
                                    animationState == SequentialAnimationState.CARD_3_VISIBLE_EXPANDED
                        },
                        onClick = {
                            if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
                                expandedCardIndex = 2 // Always ensure this card expands, others collapse
                            }
                        },
                        modifier = Modifier.graphicsLayer(translationY = card3OffsetY)
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp)) // Bottom padding for scroll including button
        }

        // Circular CTA Button (appears after all animations) - fixed at bottom
        if (animationState == SequentialAnimationState.ANIMATION_COMPLETE) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                CircularCtaButton(
                    saveButtonCta = educationData.saveButtonCta,
                    ctaLottieUrl = educationData.ctaLottie,
                    onClick = onComplete,
                    modifier = Modifier
                        .padding(2.dp) // 2px padding as requested
                )
            }
        }
    }
}

// Loading and Error screens (same as original)
@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF201929)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color(0xFFF8DC83)
        )
    }
}

@Composable
private fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF201929)),
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF8DC83)
                )
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
