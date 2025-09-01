package com.example.movies

import android.util.Log
import com.example.movies.data.api.model.MovieDto
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun example_getMovies() {
        val responseJson = "{\n" +
                "  \"id\": 666,\n" +
                "  \"externalId\": {\n" +
                "    \"kpHD\": \"48e8d0acb0f62d8585101798eaeceec5\",\n" +
                "    \"imdb\": \"tt0232500\",\n" +
                "    \"tmdb\": 9799\n" +
                "  },\n" +
                "  \"name\": \"Человек паук\",\n" +
                "  \"alternativeName\": \"Spider man\",\n" +
                "  \"enName\": \"Spider man\",\n" +
                "  \"names\": [\n" +
                "    {\n" +
                "      \"name\": \"string\",\n" +
                "      \"language\": \"string\",\n" +
                "      \"type\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"type\": \"movie\",\n" +
                "  \"typeNumber\": 1,\n" +
                "  \"year\": 2023,\n" +
                "  \"description\": \"string\",\n" +
                "  \"shortDescription\": \"string\",\n" +
                "  \"slogan\": \"string\",\n" +
                "  \"status\": \"completed\",\n" +
                "  \"facts\": [\n" +
                "    {\n" +
                "      \"value\": \"string\",\n" +
                "      \"type\": \"string\",\n" +
                "      \"spoiler\": true\n" +
                "    }\n" +
                "  ],\n" +
                "  \"rating\": {\n" +
                "    \"kp\": 6.2,\n" +
                "    \"imdb\": 8.4,\n" +
                "    \"tmdb\": 3.2,\n" +
                "    \"filmCritics\": 10,\n" +
                "    \"russianFilmCritics\": 5.1,\n" +
                "    \"await\": 6.1\n" +
                "  },\n" +
                "  \"votes\": {\n" +
                "    \"kp\": \"60000\",\n" +
                "    \"imdb\": 50000,\n" +
                "    \"tmdb\": 10000,\n" +
                "    \"filmCritics\": 10000,\n" +
                "    \"russianFilmCritics\": 4000,\n" +
                "    \"await\": 34000\n" +
                "  },\n" +
                "  \"movieLength\": 120,\n" +
                "  \"ratingMpaa\": \"pg13\",\n" +
                "  \"ageRating\": 16,\n" +
                "  \"logo\": {\n" +
                "    \"url\": \"string\"\n" +
                "  },\n" +
                "  \"poster\": {\n" +
                "    \"url\": \"string\",\n" +
                "    \"previewUrl\": \"string\"\n" +
                "  },\n" +
                "  \"backdrop\": {\n" +
                "    \"url\": \"string\",\n" +
                "    \"previewUrl\": \"string\"\n" +
                "  },\n" +
                "  \"videos\": {\n" +
                "    \"trailers\": [\n" +
                "      {\n" +
                "        \"url\": \"https://www.youtube.com/embed/ZsJz2TJAPjw\",\n" +
                "        \"name\": \"Official Trailer\",\n" +
                "        \"site\": \"youtube\",\n" +
                "        \"size\": 0,\n" +
                "        \"type\": \"TRAILER\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"genres\": [\n" +
                "    {\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"countries\": [\n" +
                "    {\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"persons\": [\n" +
                "    {\n" +
                "      \"id\": 6317,\n" +
                "      \"photo\": \"https://st.kp.yandex.net/images/actor_iphone/iphone360_6317.jpg\",\n" +
                "      \"name\": \"Пол Уокер\",\n" +
                "      \"enName\": \"Paul Walker\",\n" +
                "      \"description\": \"string\",\n" +
                "      \"profession\": \"string\",\n" +
                "      \"enProfession\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"reviewInfo\": {\n" +
                "    \"count\": 0,\n" +
                "    \"positiveCount\": 0,\n" +
                "    \"percentage\": \"string\"\n" +
                "  },\n" +
                "  \"seasonsInfo\": [\n" +
                "    {\n" +
                "      \"number\": 0,\n" +
                "      \"episodesCount\": 0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"budget\": {\n" +
                "    \"value\": 207283,\n" +
                "    \"currency\": \"€\"\n" +
                "  },\n" +
                "  \"fees\": {\n" +
                "    \"world\": {\n" +
                "      \"value\": 207283,\n" +
                "      \"currency\": \"€\"\n" +
                "    },\n" +
                "    \"russia\": {\n" +
                "      \"value\": 207283,\n" +
                "      \"currency\": \"€\"\n" +
                "    },\n" +
                "    \"usa\": {\n" +
                "      \"value\": 207283,\n" +
                "      \"currency\": \"€\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"premiere\": {\n" +
                "    \"country\": \"США\",\n" +
                "    \"world\": \"2023-02-25T02:44:39.359Z\",\n" +
                "    \"russia\": \"2023-02-25T02:44:39.359Z\",\n" +
                "    \"digital\": \"string\",\n" +
                "    \"cinema\": \"2023-02-25T02:44:39.359Z\",\n" +
                "    \"bluray\": \"string\",\n" +
                "    \"dvd\": \"string\"\n" +
                "  },\n" +
                "  \"similarMovies\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\",\n" +
                "      \"enName\": \"string\",\n" +
                "      \"alternativeName\": \"string\",\n" +
                "      \"type\": \"string\",\n" +
                "      \"poster\": {\n" +
                "        \"url\": \"string\",\n" +
                "        \"previewUrl\": \"string\"\n" +
                "      },\n" +
                "      \"rating\": {\n" +
                "        \"kp\": 6.2,\n" +
                "        \"imdb\": 8.4,\n" +
                "        \"tmdb\": 3.2,\n" +
                "        \"filmCritics\": 10,\n" +
                "        \"russianFilmCritics\": 5.1,\n" +
                "        \"await\": 6.1\n" +
                "      },\n" +
                "      \"year\": 2030\n" +
                "    }\n" +
                "  ],\n" +
                "  \"sequelsAndPrequels\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\",\n" +
                "      \"enName\": \"string\",\n" +
                "      \"alternativeName\": \"string\",\n" +
                "      \"type\": \"string\",\n" +
                "      \"poster\": {\n" +
                "        \"url\": \"string\",\n" +
                "        \"previewUrl\": \"string\"\n" +
                "      },\n" +
                "      \"rating\": {\n" +
                "        \"kp\": 6.2,\n" +
                "        \"imdb\": 8.4,\n" +
                "        \"tmdb\": 3.2,\n" +
                "        \"filmCritics\": 10,\n" +
                "        \"russianFilmCritics\": 5.1,\n" +
                "        \"await\": 6.1\n" +
                "      },\n" +
                "      \"year\": 2030\n" +
                "    }\n" +
                "  ],\n" +
                "  \"watchability\": {\n" +
                "    \"items\": [\n" +
                "      {\n" +
                "        \"name\": \"string\",\n" +
                "        \"logo\": {\n" +
                "          \"url\": \"string\"\n" +
                "        },\n" +
                "        \"url\": \"string\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"releaseYears\": [\n" +
                "    {\n" +
                "      \"start\": 2022,\n" +
                "      \"end\": 2023\n" +
                "    }\n" +
                "  ],\n" +
                "  \"top10\": 1,\n" +
                "  \"top250\": 200,\n" +
                "  \"ticketsOnSale\": true,\n" +
                "  \"totalSeriesLength\": 155,\n" +
                "  \"seriesLength\": 20,\n" +
                "  \"isSeries\": true,\n" +
                "  \"audience\": [\n" +
                "    {\n" +
                "      \"count\": 1000,\n" +
                "      \"country\": \"Россия\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"lists\": [\n" +
                "    \"250 лучших сериалов\"\n" +
                "  ],\n" +
                "  \"networks\": {\n" +
                "    \"items\": [\n" +
                "      {\n" +
                "        \"name\": \"Netflix\",\n" +
                "        \"logo\": {\n" +
                "          \"url\": \"string\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"updatedAt\": \"2025-08-16T12:03:23.461Z\",\n" +
                "  \"createdAt\": \"2025-08-16T12:03:23.461Z\"\n" +
                "}"
        val deserialized = Json.decodeFromString<MovieDto>(responseJson)
        println(deserialized)
        Log.d("TEST", deserialized.toString())
    }
}