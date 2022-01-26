package com.lamti.beatsnakechallenge.connect4.ui.components.board

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.R
import com.lamti.beatsnakechallenge.connect4.domain.Availability
import com.lamti.beatsnakechallenge.connect4.domain.Slot
import com.lamti.beatsnakechallenge.main.ui.theme.Mint100
import com.lamti.beatsnakechallenge.main.ui.theme.Navy10
import com.lamti.beatsnakechallenge.main.ui.theme.Navy100

@Composable
fun Slot(modifier: Modifier, slot: Slot, size: Dp = 36.dp) {
    val color = when (slot.availability) {
        Availability.Available -> if (isSystemInDarkTheme()) Color.White else Navy10
        Availability.Player -> Mint100
        Availability.Opponent -> Navy100
    }
    BoxWithConstraints(
        modifier = modifier
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(size - 14.dp),
            painter = painterResource(id = R.drawable.ic_bolt),
            tint = Color.White,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun SlotPreview() {
    Slot(Modifier, Slot(5, Availability.Opponent))
}
