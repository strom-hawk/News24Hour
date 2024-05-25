package com.example.samachar.utils.customviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.data.Articles
import com.example.samachar.utils.ColorSystem

@Composable
fun NewsCard(
    article: Articles
) {
    val width: Dp = ((LocalConfiguration.current.screenWidthDp.dp)) / 5
    val height: Dp = ((LocalConfiguration.current.screenHeightDp.dp)) / 4

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorSystem.grey_300, shape = RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = rememberAsyncImagePainter(article.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .width(width)
                .height(height)
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp)),
            contentScale = ContentScale.Crop,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            text = article.title ?: "Title not available",
            color = ColorSystem.black,
            maxLines = 2,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}