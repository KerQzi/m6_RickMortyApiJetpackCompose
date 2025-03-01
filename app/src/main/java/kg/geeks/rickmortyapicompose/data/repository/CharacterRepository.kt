package kg.geeks.rickmortyapicompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kg.geeks.rickmortyapicompose.data.api.ApiService
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kg.geeks.rickmortyapicompose.data.paging.CharactersPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepository(
    private val apiService: ApiService
) {
    fun fetchCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ): Pager<Int, ResponseCharacterModel> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 100,
                initialLoadSize = 60,
                prefetchDistance = 10
            ),
            pagingSourceFactory = {
                CharactersPagingSource(apiService, name, status, species, gender)
            }
        )
    }

    fun fetchCharacterDetail(id: Int) = flow<ResponseCharacterModel?> {
        apiService.fetchCharacter(id).body()?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}
