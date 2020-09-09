package com.example.t04

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MovieItemRepository(private val movieDao: MovieItemDao){

    val allMovies: LiveData<List<MovieItem>> = movieDao.getAllMovies()

    @WorkerThread
    fun insert(movie: MovieItem){
        movieDao.insertMovie(movie)
    }

    @WorkerThread
    fun deleteAll(){
        movieDao.DeleteAll()
    }

    @WorkerThread
    fun showiked(id: Long, liked: Boolean) {
        movieDao.showLiked(id, liked)
    }


}