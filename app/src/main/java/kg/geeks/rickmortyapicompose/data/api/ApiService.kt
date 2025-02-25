package kg.geeks.rickmortyapicompose.data.api

import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacters
import kg.geeks.rickmortyapicompose.data.dto.ResponseEpisodeModel
import kg.geeks.rickmortyapicompose.data.dto.ResponseEpisodes
import kg.geeks.rickmortyapicompose.data.dto.ResponseLocationModel
import kg.geeks.rickmortyapicompose.data.dto.ResponseLocations
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/character")
    suspend fun fetchAllCharacters(): Response<ResponseCharacters>

    @GET("api/character/{id}")
    suspend fun fetchCharacter(@Path("id") id: Int): Response<ResponseCharacterModel>

    @GET("api/episode")
    suspend fun fetchAllEpisodes(): Response<ResponseEpisodes>

    @GET("api/episode/{id}")
    suspend fun fetchEpisode(@Path("id") id: Int): Response<ResponseEpisodeModel>

    @GET("api/location")
    suspend fun fetchAllLocations(): Response<ResponseLocations>

    @GET("api/location/{id}")
    suspend fun fetchLocation(@Path("id") id: Int): Response<ResponseLocationModel>
}