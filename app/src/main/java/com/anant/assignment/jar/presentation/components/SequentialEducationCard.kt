package com.anant.assignment.jar.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.anant.assignment.jar.data.models.EducationCard

@Composable
fun SequentialEducationCard(
    card: EducationCard,
    isExpanded: Boolean,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val cardBackground = Color(0x5228085C)
    
    val borderBrush = if (isExpanded) {
        try {
            Brush.linearGradient(
                colors = listOf(
                    Color(android.graphics.Color.parseColor(card.strokeStartColor)),
                    Color(android.graphics.Color.parseColor(card.strokeEndColor))
                ),
                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                end = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY)
            )
        } catch (e: Exception) {
            Brush.linearGradient(
                colors = listOf(Color(0x3DFFFFFF), Color(0xFFFFFFFF)),
                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                end = androidx.compose.ui.geometry.Offset(0f, Float.POSITIVE_INFINITY)
            )
        }
    } else {
        Brush.linearGradient(colors = listOf(Color(0x1FFFFFFF), Color(0x1FFFFFFF)))
    }

    val cardHeight by animateDpAsState(
        targetValue = if (isExpanded) 444.dp else 68.dp,
        animationSpec = tween(durationMillis = 600, easing = EaseOut),
        label = "card_height"
    )

    Box(
        modifier = modifier
            .width(328.dp)
            .height(cardHeight)
            .background(color = cardBackground, shape = RoundedCornerShape(28.dp))
            .border(width = 1.dp, brush = borderBrush, shape = RoundedCornerShape(28.dp))
            .clip(RoundedCornerShape(28.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        if (isExpanded) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = card.image,
                    contentDescription = "Education card image",
                    modifier = Modifier
                        .size(296.dp, 340.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = card.expandStateText,
                    modifier = Modifier.width(240.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp,
                    minLines = 2,
                    maxLines = 2
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = card.image,
                    contentDescription = "Education card image",
                    modifier = Modifier
                        .size(31.dp, 36.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = card.collapsedStateText,
                    modifier = Modifier
                        .width(209.dp)
                        .height(20.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    maxLines = 1
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand card",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }
    }
}