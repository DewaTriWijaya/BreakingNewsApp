package com.andflube.breakingnewsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andflube.breakingnewsapp.db.Repository
import com.andflube.breakingnewsapp.db.data.NewsDB
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {

    fun getNews() = repository.getHeadlineNews()

    fun getBookmarked() = repository.getBookmarkedNews()

    fun saveNews(news: NewsDB) {
        viewModelScope.launch {
            repository.setNewsBookmark(news, true)
        }
    }

    fun deleteNews(news: NewsDB) {
        viewModelScope.launch {
            repository.setNewsBookmark(news, false)
        }
    }
}
