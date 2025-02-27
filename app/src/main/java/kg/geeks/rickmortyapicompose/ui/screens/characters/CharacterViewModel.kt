package kg.geeks.rickmortyapicompose.ui.screens.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kg.geeks.rickmortyapicompose.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _charactersFlow = MutableStateFlow<List<ResponseCharacterModel>>(emptyList())
    val charactersFlow: StateFlow<List<ResponseCharacterModel>> = _charactersFlow.asStateFlow()

    private val _characterDetailFlow = MutableStateFlow<ResponseCharacterModel?>(null)
    val characterDetailFlow: StateFlow<ResponseCharacterModel?> = _characterDetailFlow.asStateFlow()

    init {
        fetchCharacters()
    }

    //не добавил в фунции Dispatchers т.к в репозитории уже есть flowOn о котором я узнал у чат жпт
    fun fetchCharacters() = viewModelScope.launch {
        repository.fetchCharacters()
            .catch { _charactersFlow.value = emptyList() } //при ошибке выводит пустой лист
            .collect { _charactersFlow.value = it }
    }

    fun fetchCharacterDetail(id: Int) = viewModelScope.launch {
        repository.fetchCharacterDetail(id)
            .catch { _characterDetailFlow.value = null }
            .collect { _characterDetailFlow.value = it }
    }

}