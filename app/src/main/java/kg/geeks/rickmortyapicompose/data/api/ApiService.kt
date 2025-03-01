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
import retrofit2.http.Query

interface ApiService {

    @GET("api/character")
    suspend fun fetchAllCharacters(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("gender") gender: String? = null
    ): Response<ResponseCharacters>

    @GET("api/character/{id}")
    suspend fun fetchCharacter(@Path("id") id: Int): Response<ResponseCharacterModel>

    @GET("api/episode")
    suspend fun fetchAllEpisodes(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("episode") episode: String? = null
    ): Response<ResponseEpisodes>

    @GET("api/episode/{id}")
    suspend fun fetchEpisode(@Path("id") id: Int): Response<ResponseEpisodeModel>

    @GET("api/location")
    suspend fun fetchAllLocations(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("dimension") dimension: String? = null
    ): Response<ResponseLocations>

    @GET("api/location/{id}")
    suspend fun fetchLocation(@Path("id") id: Int): Response<ResponseLocationModel>
}