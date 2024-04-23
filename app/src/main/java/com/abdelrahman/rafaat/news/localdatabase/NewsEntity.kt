package com.abdelrahman.rafaat.news.localdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News_Table")
data class NewsEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,

    val title: String = "",

    val imageURL: String = "",

    val description: String = "",

    val url: String = "",

    val source: String = "",

    val publishedAt: String = "",

    val content: String = "",

    val author: String = ""
)