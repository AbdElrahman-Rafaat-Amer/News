package com.abdelrahman.rafaat.news.ui.savednews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdelrahman.rafaat.news.R
import com.abdelrahman.rafaat.news.localdatabase.NewsEntity

class SavedNewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SavedNews(
                listOf(
                    NewsEntity(title = "A", description = "Amer", publishedAt = "3-5-2022"),
                    NewsEntity(title = "B", description = "Amer", publishedAt = "3-5-2022"),
                    NewsEntity(title = "C", description = "Amer", publishedAt = "3-5-2022"),
                    NewsEntity(title = "D", description = "Amer", publishedAt = "3-5-2022"),
                    NewsEntity(title = "E", description = "Amer", publishedAt = "3-5-2022"),
                    NewsEntity(title = "F", description = "Amer", publishedAt = "3-5-2022")
                )
            )
        }
    }
}

@Composable
fun SavedNews(newsList: List<NewsEntity>) {
    LazyColumn {
        items(newsList) { news ->
            NewsCard(news = news)
        }
    }
}

@Composable
fun NewsCard(news: NewsEntity) {
    Surface(
        shape = MaterialTheme.shapes.small,
        shadowElevation = 1.dp,
    ) {
        Row(modifier = Modifier.padding(3.dp)) {
            RoundedCornerImage(
                painter = painterResource(id = R.drawable.entertainment),
                contentDescription = "news image",
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = news.description,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = news.publishedAt,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun RoundedCornerImage(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    cornerRadius: Float = 20.dp.value // Adjust the corner radius as needed
) {
    Box(
        modifier = modifier
            .size(90.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color.Gray)// Clip with rounded corners
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SavedNews(
        listOf(
            NewsEntity(title = "A", description = "Amer", publishedAt = "3-5-2022"),
            NewsEntity(title = "B", description = "Amer", publishedAt = "3-5-2022"),
            NewsEntity(title = "C", description = "Amer", publishedAt = "3-5-2022"),
            NewsEntity(title = "D", description = "Amer", publishedAt = "3-5-2022"),
            NewsEntity(title = "E", description = "Amer", publishedAt = "3-5-2022"),
            NewsEntity(title = "F", description = "Amer", publishedAt = "3-5-2022")
        )
    )
}