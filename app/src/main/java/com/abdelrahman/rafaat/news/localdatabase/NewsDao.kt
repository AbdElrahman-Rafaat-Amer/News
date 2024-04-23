package com.abdelrahman.rafaat.news.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: NewsEntity)

    @Query("SELECT * FROM News_Table")
    fun getNewsFromDatabase(): LiveData<List<NewsEntity>>

    @Delete
    fun deleteNews(news: NewsEntity)
}
