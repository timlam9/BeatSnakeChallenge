package com.lamti.beatsnakechallenge.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.ui.theme.Orange100

@Composable
fun Score(score: String, color: Color = MaterialTheme.colors.background, onSettingsClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "Beat Snake",
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold),
            color = Orange100
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "score: $score",
            style = MaterialTheme.typography.h6.copy(color = color,fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(end = 16.dp)
        )
        IconButton(onClick = onSettingsClicked) {
            Icon(
                Icons.Filled.Settings,
                contentDescription = "Settings button",
                tint = color,
            )
        }
    }
}
