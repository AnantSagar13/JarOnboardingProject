package com.anant.assignment.jar.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LandingPageScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF201929))
    ) {
        // Add top padding for status bar
        Spacer(modifier = Modifier.height(44.dp))
        
        // Header section - 360 Fill × 56
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Back button
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(20.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Header Landing Page text
            Text(
                text = "Landing Page",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 24.sp,
                maxLines = 1
            )
        }

        // Divider
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Color(0x1AACA1D3),
            thickness = 1.dp
        )

        // Content area
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Centered Landing Page text
            Text(
                text = "Landing Page",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
