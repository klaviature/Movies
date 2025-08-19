package com.example.movies.domain

import com.example.movies.domain.model.Country
import com.example.movies.domain.model.Genre
import com.example.movies.domain.model.MovieDeprecated
import com.example.movies.domain.model.Poster
import com.example.movies.domain.model.Rating
import com.example.movies.domain.model.Trailer
import com.example.movies.domain.model.VideosDeprecated

val testMovies = listOf(
    MovieDeprecated(
        id = 1,
        name = "Крестный отец",
        alternativeName = "The Godfather",
        type = "movie",
        year = 1972,
        description = "Криминальная сага о семье Корлеоне.",
        movieLength = 175,
        ageRating = 18,
        poster = Poster(url = "https://avatars.mds.yandex.net/get-kinopoisk-image/4774061/4573c546-ef2b-4b6c-8dc7-3e03bb1fcfec/orig"),
        rating = Rating(kp = 8.7, imdb = 9.2),
        videos = VideosDeprecated(trailers = listOf(Trailer(name = "Трейлер", url = "trailer_url_1"))),
        genres = listOf(Genre("криминал"), Genre("драма")),
        countries = listOf(Country("США"))
    ),
    MovieDeprecated(
        id = 2,
        name = "Темный рыцарь",
        alternativeName = "The Dark Knight",
        type = "movie",
        year = 2008,
        description = "Бэтмен сражается с Джокером.",
        movieLength = 152,
        ageRating = 16,
        poster = Poster(url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/7ecfb50e-0ace-4871-8ab0-b7304fc2d975/orig"),
        rating = Rating(kp = 8.5, imdb = 9.0),
        videos = VideosDeprecated(trailers = listOf(Trailer(name = "Основной трейлер", url = "trailer_url_2"))),
        genres = listOf(Genre("фантастика"), Genre("боевик")),
        countries = listOf(Country("США"), Country("Великобритания"))
    ),
    MovieDeprecated(
        id = 3,
        name = "Побег из Шоушенка",
        alternativeName = "The Shawshank Redemption",
        type = "movie",
        year = 1994,
        description = "История невиновного человека в тюрьме.",
        movieLength = 142,
        ageRating = 16,
        poster = Poster(url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/e66a3396-239b-4871-af63-86b520439f22/orig"),
        rating = Rating(kp = 9.1, imdb = 9.3),
        videos = VideosDeprecated(trailers = listOf(Trailer(name = "Классический трейлер", url = "trailer_url_3"))),
        genres = listOf(Genre("драма")),
        countries = listOf(Country("США"))
    ),
    MovieDeprecated(
        id = 4,
        name = "Форрест Гамп",
        alternativeName = "Forrest Gump",
        type = "movie",
        year = 1994,
        description = "Жизнь человека с низким IQ.",
        movieLength = 142,
        ageRating = 12,
        poster = Poster(url = "https://avatars.mds.yandex.net/get-kinopoisk-image/6201401/da241cd4-0828-4aed-bfd2-27332dc4c94c/orig"),
        rating = Rating(kp = 8.9, imdb = 8.8),
        videos = VideosDeprecated(trailers = listOf(Trailer(name = "Официальный трейлер", url = "trailer_url_4"))),
        genres = listOf(Genre("драма"), Genre("комедия")),
        countries = listOf(Country("США"))
    ),
    MovieDeprecated(
        id = 5,
        name = "Начало",
        alternativeName = "Inception",
        type = "movie",
        year = 2010,
        description = "Проникновение в подсознание.",
        movieLength = 148,
        ageRating = 12,
        poster = Poster(url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/c17255b5-9809-41e0-bd89-84622cc77ee8/orig"),
        rating = Rating(kp = 8.7, imdb = 8.8),
        videos = VideosDeprecated(trailers = listOf(Trailer(name = "Захватывающий трейлер", url = "trailer_url_5"))),
        genres = listOf(Genre("фантастика"), Genre("боевик")),
        countries = listOf(Country("США"), Country("Великобритания"))
    ),
    MovieDeprecated(
        id = 6,
        name = "Зеленая миля",
        alternativeName = "The Green Mile",
        type = "movie",
        year = 1999,
        description = "История в тюрьме для смертников.",
        movieLength = 189,
        ageRating = 16,
        poster = Poster(url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/acb932eb-c7d0-42de-92df-f5f306c4c48e/orig"),
        rating = Rating(kp = 9.1, imdb = 8.6),
        videos = VideosDeprecated(trailers = listOf(Trailer(name = "Трейлер 1999", url = "trailer_url_6"))),
        genres = listOf(Genre("драма"), Genre("фэнтези")),
        countries = listOf(Country("США"))
    ),
    MovieDeprecated(
        id = 7,
        name = "Бойцовский клуб",
        alternativeName = "Fight Club",
        type = "movie",
        year = 1999,
        description = "Подпольный бойцовский клуб.",
        movieLength = 139,
        ageRating = 18,
        poster = Poster(url = "https://avatars.mds.yandex.net/get-kinopoisk-image/4483445/0c7664fa-d8ec-4dc3-90ba-747e99b668ed/orig"),
        rating = Rating(kp = 8.7, imdb = 8.8),
        videos = VideosDeprecated(trailers = listOf(Trailer(name = "Культовый трейлер", url = "trailer_url_7"))),
        genres = listOf(Genre("драма")),
        countries = listOf(Country("США"), Country("Германия"))
    ),
    MovieDeprecated(
        id = 8,
        name = "Криминальное чтиво",
        alternativeName = "Pulp Fiction",
        type = "movie",
        year = 1994,
        description = "Несколько связанных криминальных историй.",
        movieLength = 154,
        ageRating = 18,
        poster = Poster(url = "https://avatars.mds.yandex.net/get-kinopoisk-image/6201401/97ed7256-db80-4532-ab6b-6688d2eab4b2/orig"),
        rating = Rating(kp = 8.6, imdb = 8.9),
        videos = VideosDeprecated(trailers = listOf(Trailer(name = "Легендарный трейлер", url = "trailer_url_8"))),
        genres = listOf(Genre("криминал"), Genre("драма")),
        countries = listOf(Country("США"))
    ),
    MovieDeprecated(
        id = 9,
        name = "Список Шиндлера",
        alternativeName = "Schindler's List",
        type = "movie",
        year = 1993,
        description = "История спасения евреев во время Холокоста.",
        movieLength = 195,
        ageRating = 16,
        poster = Poster(url = "https://kinopoisk-ru.clstorage.net/p1b611P35/6db995FR_CUM/9leOnf8BMq9QL6gyCKIn3JCqMCLAZpnblvoqVn-sxdnXJNhGqftQDreqje6gvf3DIa34WzrcZdsHtxMgMDjhKWKCm2lOlMease4NIs3nEwhlKgRTBOY5v9Y5SUScvha6FLKapOsKUSs2X2jr1UnYt0CoSdpb34ZKAGdiNsXcZV_ll62vaNPB_fY9SsDasJT6YtoQdqD3qK_0KOmEDbwXaHeyWAZq6EHYsE1rCbdZixUxSn6bmG92PwB2cmTEL3TsxkXPnDmi4f30vgkTePLnGQD7EweQVuu8dNrLtG3vFmgFUNgFeYlzeIJ96kklaFlVYWo9WxruN59wd9FlVF-VPjcnHvyJ8qKst89rcbrzNxoyWqB3IFaZDAaK-9RPrBZKlXEKNWkZkmgzzfr4JfjqxRO6zwpor3XOE8Wj11QvNj-kVf6--xFijWfM2tH7gXS6kqjBp3EXmcxGafkX3P4ECvUyi_YJuxA70x5KqpY4yzSSmD6aWk1nHNG34hf1vWY_xnUtbCkhIiyUPOpzy_IWSxFIkzcANgv-xsgpF5ychWp2E5hWGxpQO1HdWLt1SVpksOueKVn_dayQVDKk1IyEfZVlDPz6opHONe6KoBnDJTnQWVIloxf5z1dZmTS8PQYIVoOahumIcLtx3UlJ1Vu5d2NafauqH-S9c3UxppdMBVxFNIyuSkEgDzSvWTP7sjZ4IivBp1EEOC2FW5iW3f4HCFXCKnc4ijCYEM-a-paoOyUCGJw5OHzX_XPHcxbWfFT8JnWOvEswQj6EfSjQOtBE6fAZAkTi5uq-Fhr4da6-BZuXElgkmxpg2lA--Qh1SulFshg8S2ocZv4SxeBnZYwEPPZ0H527I5LPJ92a49vRJXgCyNMlUKYaj4Y5OCY-jibolwJqBJm6AJvQ3-pbBzl7JbHIP6lpj1S-cdUTRDSMlgw0Vd3OmdERzFT8ShPoksa48FkyZKKWmq1V6tmkbZzGGafjeoZbK9M54k-6-rQ6-GcSG3_aWG7WHxPF8CdFbMdd5ddc7gpjUvzkbnoTm9FUOLC6clWgNBu_dNo6xk0O5fn0EUvUCYuQ-IOcuGi0O-kk0PmsKvstR5wyBcNmJW7XP7RHD45qk2FsBe4pIpvS1ssTWkFUIOW63sR66bftjWXLdaGqtKipcSqzzypJ5Kqrd2CrfdsIzuePEBRht4QcZR3lhL0-i1HAXiW_CqD6cFR7EUvgxKOX-Y2GKTgEfLzGS7fzOCTrCYP5QPxKyjQq6ufDag0ZqQ7GnZFXEgV2D1Qu9ya_nVsTIY92XNigW3LGKCJ7MSTiN2vsVikZhF_-damEwspl6nmQ-PMtORkVa5olIVpvuWsOtJ7h9TKU528kjvVHH345UzCshUwrMhlgpIowmTAkAZS4byd72oe-Hkc7R4Do9Iv5ANkyfugp14l7VJNKXlnrn9YdEUXTdeQcJxw0VX8_iIHxrUb_WaD743f5QVrzlXO2KQ3Ga-qU3L80akTwuEZryHF4kB36mbXoamaSGKwoOa9lPtNFI_V0PrYeB4eMPkpBsK4F7PhjSZD1-2JaszdRVqvdJvu5t52M1BrXY2n0iHviiSLvmysk6Dh0oDodqaov9D5Dd2EXxK-WzHcFHf_L84IetO8osbuwFPiCCnPUUYZqbZdq2lZuDoUatPLblPnIsUiQ7-s71Dt7JfN7z_raLIVcYSVhJ9feZt3X9K_dWpOijEXuqnPa0EQp0nqydtBH-_5lOKqUPu0WSrewqhS5OkDIoq6oGpWoyiRwCB9ZKP30HwMlAYS3fFUuxXV-jFvQsY5HzKsSadLWuDHrAZUS9lot1QhqpnxdNig3gonUSohw2lKNyTnEuBjkQKnOGfhMpK8h9ZFW5Ux0PqeUvc_4QGGtVY66gPvRRugTS0BUAyb57TfIOdW9zqZaNuGrlup5UgiDPSs5xeha1-F7_6h6PCQ_gwRRdvUsp8_3ZR6MOcER_hZNWkNK0dS5UQkzV5CEqwxXK6jErnwl2qexKKd46lMLIS66qldIWtVDyY-5KZ0XD2BFIicHvHYMlbSv72phUr9HrEijiAE0uxCKghcwdLu952pIRywsVfi3oip2CPgRa8IvOmlm-JglcUmPqwou9J8DN8Mkt3-V_aaVLy_LgyL8JYz68qlC1ovRGhPEsbVYj6SayffejaVa92Nq5El7wCkx_Ik4RynrxNDJbHpbr3TsokbhJDWsJczUNY-M2_FSPFfMm0C4EtUp0cgQRVD0qq_0KKqVzsy0WreymsabugPbMJ24KifIeeUz6f4LqG4X_kNUEqXXTBcehac8jqtj8rwUfLgwCCCE2tIa86Whh6g8RCmaJh3OlxmW4FqF-aqyigOti1m3WZqXccqPuyvuZw8DJxEVlhzU_lfXHW3I43DM5G7I0vlxZ3hT6EE1opbbHja6WJS93ldoF2Da50rKoLii7ckLd4mat_CrrioazLbfohTRhgdftV_n9j88qfGw3WQPGvILYCboodkBRlCEmR2EupvFzH5kS3TzSYYLCTJrY86ZSDeoGyRC6-5JGr4G7KPlgSeFn7c-tFVM7CsDU6wXnpjjm3IVWwAKwZRyJmqMdymY1ixup6v1M6i2GGoA-OLtisu0Oir1MLoPS1q-RW1DBFJkpfxWfZenr3_7IrKdBByaYipwhLlCaTB2YyZKjeZqW6avzwV6hhIph1nZwsng_voqBxgox1AY3QhJHJTOYsexxNZ-lO6F9X1PqmChjpX-CDFKMsToUjpBRBIGmu8GiYnG_y0mO-fBKIaq6mDqwmzaikbr-Obhq6352y8nfBL1E9XVnsSuJpUP3jpyQi6ljSrBi5MGeUDLw8cTRDq_VUu5xw7MRdjXcoo0CKgBa1MdOdlHCil08apNaevvJy6yV5MHZA1kDheVzz1bA5Bc1Z4qUKuQpAlRynE0clRoPDXI-JQfnSW6p9Fatujbgvry_kgJ1UmZZNL43ig4bPf8g"),
        rating = Rating(kp = 8.8, imdb = 9.0),
        videos = VideosDeprecated(trailers = listOf(Trailer(name = "Оскароносный трейлер", url = "trailer_url_9"))),
        genres = listOf(Genre("драма"), Genre("биография")),
        countries = listOf(Country("США"))
    ),
    MovieDeprecated(
        id = 10,
        name = "Интерстеллар",
        alternativeName = "Interstellar",
        type = "movie",
        year = 2014,
        description = "Путешествие через червоточину в поисках нового дома.",
        movieLength = 169,
        ageRating = 12,
        poster = Poster(url = "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/78c36c0f-aefd-4102-bc3b-bac0dd4314d8/orig"),
        rating = Rating(kp = 8.6, imdb = 8.6),
        videos = VideosDeprecated(
            trailers = listOf(
                Trailer(
                    name = "Космический трейлер",
                    url = "trailer_url_10"
                )
            )
        ),
        genres = listOf(Genre("фантастика"), Genre("драма")),
        countries = listOf(Country("США"), Country("Великобритания"))
    )
)