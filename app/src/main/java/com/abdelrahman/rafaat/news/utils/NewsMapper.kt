package com.abdelrahman.rafaat.news.utils

import com.abdelrahman.rafaat.news.localdatabase.NewsEntity
import com.abdelrahman.rafaat.news.model.Article
import com.abdelrahman.rafaat.news.model.Source

object NewsMapper {
    fun fromEntity(entity: NewsEntity): Article {
        return Article(
            url = entity.url,
            title = entity.title,
            description = entity.description,
            author = "",
            content = "",
            publishedAt = entity.publishedAt,
            source = Source(
                id = "", name = ""
            ),
            urlToImage = entity.imageURL
        )
    }

    fun toEntity(model: Article): NewsEntity {
        return NewsEntity(
            url = model.url ?: "",
            title = model.title ?: "",
            description = model.description ?: "",
            publishedAt = model.publishedAt ?: "",
            imageURL = model.urlToImage ?: ""
        )
    }

    fun fromEntityList(entitiesList: List<NewsEntity>): List<Article> {
        val articlesList = mutableListOf<Article>()
        for (entity: NewsEntity in entitiesList) {
            articlesList.add(fromEntity(entity))
        }
        return articlesList
    }

    fun toEntityList(models: List<Article>): List<NewsEntity> {
        val entitiesList = mutableListOf<NewsEntity>()
        for (model: Article in models) {
            entitiesList.add(toEntity(model))
        }
        return entitiesList
    }
}