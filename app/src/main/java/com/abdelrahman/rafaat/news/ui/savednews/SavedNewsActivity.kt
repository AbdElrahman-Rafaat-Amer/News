package com.abdelrahman.rafaat.news.ui.savednews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.abdelrahman.rafaat.news.R
import com.abdelrahman.rafaat.news.localdatabase.LocalSource
import com.abdelrahman.rafaat.news.localdatabase.NewsEntity
import com.abdelrahman.rafaat.news.model.Article
import com.abdelrahman.rafaat.news.model.Repository
import com.abdelrahman.rafaat.news.network.NewsClient
import com.abdelrahman.rafaat.news.ui.composeui.RoundedCornerImage
import com.abdelrahman.rafaat.news.ui.mainscreen.viewmodel.MainActivityFactory
import com.abdelrahman.rafaat.news.ui.mainscreen.viewmodel.MainActivityViewModel
import com.abdelrahman.rafaat.news.utils.NewsMapper

class SavedNewsActivity : ComponentActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private val savedNewsList = mutableStateListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observeViewModel()
        viewModel.getAllSavedNews()
        setContent {
            SavedNews(
                savedNewsList
            )
        }
    }

    private fun initViewModel() {
        val viewModelFactory = MainActivityFactory(
            Repository.getNewsClient(
                NewsClient.getNewsClient(),
                LocalSource.getInstance(this),
                this.application
            ), this.application
        )

        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[MainActivityViewModel::class.java]
    }

    private fun observeViewModel() {
        viewModel.savedNews.observe(this) {
            savedNewsList.clear()
            if (it.isNotEmpty()) {
                savedNewsList.addAll(it)
            }

        }
    }
}

@Composable
fun SavedNews(newsList: List<Article>) {
    LazyColumn {
        items(newsList) { news ->
            NewsCard(news = news)
        }
    }
}

@Composable
fun NewsCard(news: Article) {
    Surface(
        shape = MaterialTheme.shapes.small,
        shadowElevation = 1.dp,
        modifier = Modifier
            .padding(
                horizontal = 7.dp,
                vertical = 4.dp
            )
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFEBE7E7))
        ) {
            RoundedCornerImage(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterVertically),
                imageURL = news.urlToImage,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .padding(end = 7.dp, bottom = 7.dp)
            ) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2

                )
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = news.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(9.dp))

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

@Preview(showBackground = true)
@Composable
fun SavedNewsPreview() {
    SavedNews(
        NewsMapper.fromEntityList(
            listOf(
                NewsEntity(
                    title = "Binance founder Changpeng Zhao sentenced to 4 months for allowing money laundering - The Associated Press",
                    description = "Former Binance CEO Changpeng Zhao has been sentenced to four months in prison for allowing rampant money laundering on the world’s largest cryptocurrency exchange. A judge on Tuesday credited Zhao for taking responsibility for his wrongdoing. But he said he w…",
                    publishedAt = "3-5-2022",
                    url = "",
                    imageURL = "",
                ),
                NewsEntity(
                    title = "F",
                    description = "Amer",
                    publishedAt = "3-5-2022",
                    url = "",
                    imageURL = "",
                ),
                NewsEntity(
                    title = "F",
                    description = "Amer",
                    publishedAt = "3-5-2022",
                    url = "",
                    imageURL = ""
                ),
                NewsEntity(
                    title = "F",
                    description = "Amer",
                    publishedAt = "3-5-2022",
                    url = "",
                    imageURL = ""
                ),
                NewsEntity(
                    title = "F",
                    description = "Amer",
                    publishedAt = "3-5-2022",
                    url = "",
                    imageURL = ""
                )
            )
        )
    )
}