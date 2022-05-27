package cl.robveloper.movieapp.data.remote

import cl.robveloper.movieapp.application.AppConstants
import cl.robveloper.movieapp.data.model.MovieList
import cl.robveloper.movieapp.repository.WebService

class MovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)
    
}