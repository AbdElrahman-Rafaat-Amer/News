package com.abdelrahman.rafaat.news.ui.composeui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abdelrahman.rafaat.news.R

@Composable
fun RoundedCornerImage(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.ic_default_image),
    imageURL: String,
    contentDescription: String?,
    cornerRadius: Float = 20.dp.value
) {
    Box(
        modifier = modifier
            .size(90.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color.Gray)
    ) {
        AsyncImage(
            model = imageURL,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            placeholder = painter,
            error = painter,
            contentScale = ContentScale.Crop,
        )
    }
}