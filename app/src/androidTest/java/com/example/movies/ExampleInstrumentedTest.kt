package com.example.movies

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.movies.data.api.ApiFactoryCoroutines
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.movies", appContext.packageName)
    }

    @Test
    fun example_getMovies() = runBlocking {
        val service = ApiFactoryCoroutines.apiService
        val movies = service.getMovies(0)
        if (movies.isSuccessful) {
//                println(movies.body())
            Log.d("TEST", movies.body().toString())
        } else {
            Log.d("TEST", movies.errorBody().toString())
        }
    }
}