package com.example.samachar.utils.customviews

import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
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
    selectedCardIndex: Int,
    index: Int,
    article: Articles,
    newsCardFocusRequester: FocusRequester,
    newCardDirectionsClicked: (Int, Int) -> Unit
) {
    val width: Dp = ((LocalConfiguration.current.screenWidthDp.dp)) / 5
    val height: Dp = ((LocalConfiguration.current.screenHeightDp.dp)) / 4
    val borderColor = if (selectedCardIndex == index) ColorSystem.red_800 else Color.Transparent

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorSystem.grey_300, shape = RoundedCornerShape(8.dp))
            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
            .focusRequester(newsCardFocusRequester)
            .clickable {}
            .onKeyEvent { keyEvent ->
                if (keyEvent.nativeKeyEvent.action == KeyEvent.ACTION_UP) {
                    when (keyEvent.nativeKeyEvent.keyCode) {
                        KeyEvent.KEYCODE_DPAD_DOWN -> {
                            newCardDirectionsClicked(
                                KeyEvent.KEYCODE_DPAD_DOWN,
                                selectedCardIndex + 4
                            )
                            true
                        }

                        KeyEvent.KEYCODE_DPAD_UP -> {
                            newCardDirectionsClicked(
                                KeyEvent.KEYCODE_DPAD_UP,
                                selectedCardIndex - 4
                            )

                            true
                        }

                        KeyEvent.KEYCODE_DPAD_RIGHT -> {
                            newCardDirectionsClicked(
                                KeyEvent.KEYCODE_DPAD_RIGHT,
                                selectedCardIndex + 1
                            )
                            true
                        }

                        KeyEvent.KEYCODE_DPAD_LEFT -> {
                            newCardDirectionsClicked(
                                KeyEvent.KEYCODE_DPAD_LEFT,
                                selectedCardIndex - 1
                            )
                            true
                        }

                        else -> {
                            false
                        }

                    }
                } else {
                    true
                }
            }
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