package com.anant.assignment.jar.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import com.airbnb.lottie.compose.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anant.assignment.jar.data.models.SaveButtonCta

@Composable
fun CircularCtaButton(
    saveButtonCta: SaveButtonCta,
    ctaLottieUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isClicked by remember { mutableStateOf(false) }
    
    val offsetY by animateFloatAsState(
        targetValue = if (isClicked) 1000f else 0f,
        animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = Spring.StiffnessLow
        ),
        label = "button_offset"
    )
    
    Box(
        modifier = modifier
            .width(165.dp)
            .height(48.dp)
            .graphicsLayer(translationY = offsetY)
            .clip(RoundedCornerShape(31.dp))
            .background(Color(0xFF272239))
            .clickable {
                isClicked = true
                onClick()
            }
            .padding(top = 2.dp, end = 16.dp, bottom = 2.dp, start = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = saveButtonCta.text,
                modifier = Modifier.width(85.dp).height(20.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFDF3D6),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                maxLines = 1
            )
            
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .rotate(180f),
                contentAlignment = Alignment.Center
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.Url(ctaLottieUrl))
                val progress by animateLottieCompositionAsState(
                    composition,
                    iterations = LottieConstants.IterateForever
                )
                
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(22.dp))
                )
            }
        }
    }
}