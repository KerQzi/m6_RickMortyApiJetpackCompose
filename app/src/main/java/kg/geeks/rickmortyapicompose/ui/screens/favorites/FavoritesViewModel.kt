package kg.geeks.rickmortyapicompose.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.geeks.rickmortyapicompose.data.db.FavoriteCharacterEntity
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kg.geeks.rickmortyapicompose.data.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {

    val favoriteCharactersFlow: Flow<List<FavoriteCharacterEntity>> = repository.fetchFavoriteCharacters()

    fun addFavorite(character: ResponseCharacterModel) {
        val favoriteCharacter = FavoriteCharacterEntity(
            id = character.id ?: 0,
            name = character.name.orEmpty(),
            status = character.status.orEmpty(),
            species = character.species.orEmpty(),
            type = character.type.orEmpty(),
            gender = character.gender.orEmpty(),
            image = character.image.orEmpty()
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteCharacter(favoriteCharacter)
        }
    }

    fun removeFavorite(favoriteCharacter: FavoriteCharacterEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteCharacter(favoriteCharacter)
        }
    }
}