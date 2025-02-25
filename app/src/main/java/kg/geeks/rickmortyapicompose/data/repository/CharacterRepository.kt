package kg.geeks.rickmortyapicompose.data.repository

import kg.geeks.rickmortyapicompose.data.api.ApiService
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel

class CharacterRepository(
    private val apiService: ApiService
) {
    suspend fun fetchCharacters(): List<ResponseCharacterModel>? {
        val response = apiService.fetchAllCharacters()
        return if (response.isSuccessful) response.body()?.results else null
    }

    suspend fun fetchCharacterDetail(id: Int): ResponseCharacterModel? {
        val response = apiService.fetchCharacter(id)
        return if (response.isSuccessful) response.body() else null
    }
}