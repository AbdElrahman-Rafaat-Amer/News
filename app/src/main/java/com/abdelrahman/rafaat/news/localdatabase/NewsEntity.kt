package com.abdelrahman.rafaat.news.localdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News_Table")
data class NewsEntity(

    @PrimaryKey(autoGenerate = false)
    val url: String,

    val title: String,

    val imageURL: String,

    val description: String,

    val publishedAt: String
)