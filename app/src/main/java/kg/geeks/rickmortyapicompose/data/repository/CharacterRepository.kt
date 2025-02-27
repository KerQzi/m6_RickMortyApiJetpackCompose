package kg.geeks.rickmortyapicompose.data.repository

import kg.geeks.rickmortyapicompose.data.api.ApiService
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepository(
    private val apiService: ApiService
) {
    fun fetchCharacters() = flow {
        apiService.fetchAllCharacters().body()?.results?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    fun fetchCharacterDetail(id: Int) = flow<ResponseCharacterModel?> {
        apiService.fetchCharacter(id).body()?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}
