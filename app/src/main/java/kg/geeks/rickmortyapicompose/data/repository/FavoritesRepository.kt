package kg.geeks.rickmortyapicompose.data.repository

import kotlinx.coroutines.flow.Flow
import kg.geeks.rickmortyapicompose.data.db.FavoriteCharacterEntity
import kg.geeks.rickmortyapicompose.data.db.FavoriteCharactersDao

class FavoritesRepository(private val dao: FavoriteCharactersDao) {

    fun fetchFavoriteCharacters(): Flow<List<FavoriteCharacterEntity>> = dao.fetchAllFavoriteCharacters()

    suspend fun insertFavoriteCharacter(character: FavoriteCharacterEntity) {
        dao.insertFavoriteCharacter(character)
    }

    suspend fun deleteFavoriteCharacter(character: FavoriteCharacterEntity) {
        dao.deleteFavoriteCharacter(character)
    }
}
