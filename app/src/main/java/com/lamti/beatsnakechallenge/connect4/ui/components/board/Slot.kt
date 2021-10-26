package com.lamti.beatsnakechallenge.connect4.ui.components.board

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.lamti.beatsnakechallenge.R
import com.lamti.beatsnakechallenge.connect4.domain.Availability
import com.lamti.beatsnakechallenge.connect4.domain.Slot
import com.lamti.beatsnakechallenge.main.ui.theme.Mint100
import com.lamti.beatsnakechallenge.main.ui.theme.Navy10
import com.lamti.beatsnakechallenge.main.ui.theme.Navy100

@Composable
fun Slot(modifier: Modifier, slot: Slot) {
    val color = when (slot.availability) {
        Availability.Available -> if (isSystemInDarkTheme()) Color.White else Navy10
        Availability.Player -> Mint100
        Availability.Opponent -> Navy100
    }
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_beat),
            tint = Color.White,
            contentDescription = null
        )
    }
}
