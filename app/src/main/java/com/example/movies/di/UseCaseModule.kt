package com.example.movies.di

import com.example.movies.domain.repositories.MoviesRepositoryTest
import com.example.movies.domain.repositories.ReviewsRepository
import com.example.movies.domain.usecases.AddMovieToFavouritesUseCase
import com.example.movies.domain.usecases.AddMovieToWatchedUseCase
import com.example.movies.domain.usecases.CheckIfMovieIsFavouriteUseCase
import com.example.movies.domain.usecases.CheckIfMovieIsWatchedUseCase
import com.example.movies.domain.usecases.GetMovieUseCaseTest
import com.example.movies.domain.usecases.GetReviewsUseCase
import com.example.movies.domain.usecases.RemoveMovieFromFavouritesUseCase
import com.example.movies.domain.usecases.RemoveMovieFromWatchedUseCase
import com.example.movies.domain.usecases.recommended.GetRecommendedMoviesUseCase
import com.example.movies.domain.usecases.recommended.SearchMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetRecommendedMoviesUseCase(
        moviesRepository: MoviesRepositoryTest
    ) : GetRecommendedMoviesUseCase {
        return GetRecommendedMoviesUseCase(moviesRepository)
    }

    @Provides
    @ViewModelScoped
    fun providesGetMovieUseCase(
        moviesRepository: MoviesRepositoryTest
    ): GetMovieUseCaseTest {
        return GetMovieUseCaseTest(moviesRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetReviewsUseCase(
        reviewsRepository: ReviewsRepository
    ): GetReviewsUseCase {
        return GetReviewsUseCase(reviewsRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAddMovieToFavouritesUseCase(
        moviesRepository: MoviesRepositoryTest
    ): AddMovieToFavouritesUseCase {
        return AddMovieToFavouritesUseCase(moviesRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRemoveMovieFromFavouritesUseCase(
        moviesRepository: MoviesRepositoryTest
    ): RemoveMovieFromFavouritesUseCase {
        return RemoveMovieFromFavouritesUseCase(moviesRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCheckIfMovieIsFavouriteUseCase(
        moviesRepository: MoviesRepositoryTest
    ): CheckIfMovieIsFavouriteUseCase {
        return CheckIfMovieIsFavouriteUseCase(moviesRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideAddMovieToWatchedUseCase(
        moviesRepository: MoviesRepositoryTest
    ): AddMovieToWatchedUseCase {
        return AddMovieToWatchedUseCase(moviesRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRemoveMovieFromWatchedUseCase(
        moviesRepository: MoviesRepositoryTest
    ): RemoveMovieFromWatchedUseCase {
        return RemoveMovieFromWatchedUseCase(moviesRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCheckIfMovieIsWatchedUseCase(
        moviesRepository: MoviesRepositoryTest
    ): CheckIfMovieIsWatchedUseCase {
        return CheckIfMovieIsWatchedUseCase(moviesRepository)
    }

}