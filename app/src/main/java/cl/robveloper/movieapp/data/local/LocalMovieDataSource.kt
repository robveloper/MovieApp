package cl.robveloper.movieapp.data.local

import cl.robveloper.movieapp.application.AppConstants
import cl.robveloper.movieapp.data.model.MovieEntity
import cl.robveloper.movieapp.data.model.MovieList
import cl.robveloper.movieapp.data.model.toMovieList

class LocalMovieDataSource(private val movieDao: MovieDao) {

    suspend fun getUpcomingMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "upcoming" }.toMovieList()
    }
    suspend fun getTopRatedMovies(): MovieList{
        return movieDao.getAllMovies().filter { it.movie_type == "toprated" }.toMovieList()
    }
    suspend fun getPopularMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "popular" }.toMovieList()
    }

    suspend fun saveMovie(movie: MovieEntity) {
        movieDao.saveMovie(movie)
    }

}