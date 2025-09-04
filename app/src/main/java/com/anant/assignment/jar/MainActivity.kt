package com.anant.assignment.jar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.anant.assignment.jar.presentation.screens.SequentialAnimationScreen
import com.anant.assignment.jar.presentation.screens.LandingPageScreen
import com.anant.assignment.jar.presentation.theme.OnboardingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            OnboardingTheme {
                var showLandingPage by remember { mutableStateOf(false) }
                var shouldRestartAnimation by remember { mutableStateOf(true) }
                var expandedCardIndex by remember { mutableStateOf(2) } // Maintain card state
                
                Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
                    // Handle system back gesture
                    BackHandler {
                        if (showLandingPage) {
                            // Back from landing page - go to onboarding screen
                            showLandingPage = false
                            shouldRestartAnimation = false
                        } else {
                            // Back from onboarding - restart animation (same as back button behavior)
                            shouldRestartAnimation = true
                            expandedCardIndex = 2
                        }
                    }
                    
                    if (showLandingPage) {
                        LandingPageScreen(
                            onBackClick = { 
                                showLandingPage = false
                                shouldRestartAnimation = false // Don't restart animation when coming back
                            },
                            modifier = Modifier.padding(contentPadding)
                        )
                    } else {
                        SequentialAnimationScreen(
                            onOnboardingComplete = { showLandingPage = true },
                            onNavigateBack = { 
                                // Restart animation instead of closing app
                                shouldRestartAnimation = true
                                expandedCardIndex = 2
                            },
                            shouldRestartAnimation = shouldRestartAnimation,
                            expandedCardIndex = expandedCardIndex,
                            onExpandedCardChange = { newIndex -> expandedCardIndex = newIndex },
                            modifier = Modifier.padding(contentPadding)
                        )
                    }
                }
            }
        }
    }
}