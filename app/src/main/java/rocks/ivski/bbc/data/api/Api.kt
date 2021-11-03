package rocks.ivski.bbc.data.api

import retrofit2.Response
import retrofit2.http.GET
import rocks.ivski.bbc.data.dto.Character

interface Api {

    @GET("characters")
    suspend fun getCharacters(): Response<List<Character>>
}