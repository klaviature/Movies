package com.example.movies.domain.usecases

import android.util.Log
import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Result
import com.example.movies.domain.repositories.MoviesRepositoryTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovieUseCaseTest(
    private val repositoryTest: MoviesRepositoryTest
) {
    fun getMovie(movieId: Int): Flow<Result<Movie, DataError>> = flow {
        Log.d("GetMovieUseCaseTest", "getMovie: started")
        repositoryTest.getMovieDetailsFromNetwork(movieId).collect { networkResult ->
            when (networkResult) {
                is Result.Error -> {
                    Log.d("GetMovieUseCaseTest", "getMovie: error ${networkResult.error}")
                    emit(Result.Error(networkResult.error))
                    repositoryTest.getMovieDetailsFromLocal(movieId).collect { localResult ->
                        when (localResult) {
                            is Result.Error -> {
                                Log.d("GetMovieUseCaseTest", "getMovie: getMovieDetailsFromLocal: error: ${localResult.error}")
                                emit(Result.Error(localResult.error))
                            }
                            Result.Loading -> emit(Result.Loading)
                            is Result.Success -> {
                                Log.d("GetMovieUseCaseTest", "getMovie: getMovieDetailsFromLocal: success")
                                emit(Result.Success(localResult.data))
                            }
                        }
                    }
                }
                Result.Loading -> emit(Result.Loading)
                is Result.Success -> {
                    Log.d("GetMovieUseCaseTest", "getMovie: getMovieDetailsFromNetwork: success")
                    emit(Result.Success(networkResult.data))
                    repositoryTest.saveOrUpdateMovie(networkResult.data).collect { saveResult ->
                        when (saveResult) {
                            is Result.Error -> emit(Result.Error(saveResult.error))
                            Result.Loading -> {}
                            is Result.Success -> {}
                        }
                    }
                }
            }
        }
    }
    suspend fun getMovieFromNetwork(movieId: Int): Flow<Result<Movie, DataError.Network>> {
        return repositoryTest.getMovieDetailsFromNetwork(movieId)
    }

    suspend fun getMovieFromLocal(movieId: Int): Flow<Result<Movie, DataError.Local>> {
        return repositoryTest.getMovieDetailsFromLocal(movieId)
    }
}