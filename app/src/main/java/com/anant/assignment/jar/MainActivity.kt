package com.anant.assignment.jar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.anant.assignment.jar.presentation.screens.SequentialAnimationScreen
import com.anant.assignment.jar.presentation.theme.OnboardingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            OnboardingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { 
                    SequentialAnimationScreen(
                        onOnboardingComplete = { finish() },
                        onNavigateBack = { finish() }
                    )
                }
            }
        }
    }
}