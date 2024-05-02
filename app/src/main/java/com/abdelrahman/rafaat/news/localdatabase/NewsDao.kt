package com.abdelrahman.rafaat.news.localdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: NewsEntity)

    @Query("SELECT * FROM News_Table")
    fun getNewsFromDatabase(): List<NewsEntity>

    @Delete
    fun deleteNews(news: NewsEntity): Int
}
