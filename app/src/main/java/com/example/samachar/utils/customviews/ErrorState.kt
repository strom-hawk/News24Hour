package com.example.samachar.utils.customviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.samachar.R
import com.example.samachar.utils.ColorSystem

@Composable
fun ErrorState(
    errorMessage: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painterResource(R.drawable.something_went_wrong),
            contentDescription = "something went wrong",
            modifier = Modifier.size(100.dp)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(0.7F)
                .padding(vertical = 16.dp),
            text = errorMessage,
            color = ColorSystem.white,
            textAlign = TextAlign.Center
        )
    }
}