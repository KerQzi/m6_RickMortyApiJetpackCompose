package kg.geeks.rickmortyapicompose.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kg.geeks.rickmortyapicompose.data.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {



    private val _charactersFlow = MutableStateFlow<PagingData<ResponseCharacterModel>>(PagingData.empty())
    val charactersFlow = _charactersFlow.asStateFlow()

    private val _characterDetailFlow = MutableStateFlow<ResponseCharacterModel?>(null)
    val characterDetailFlow = _characterDetailFlow.asStateFlow()

    init {
        fetchCharacters()
    }

    fun fetchCharacters() = viewModelScope.launch(Dispatchers.IO) {
        repository.fetchCharacters().flow.cachedIn(viewModelScope)
            .collectLatest { _charactersFlow.value = it }
    }

    fun fetchCharacterDetail(id: Int) = viewModelScope.launch {
        repository.fetchCharacterDetail(id)
            .catch { _characterDetailFlow.value = null }
            .collect { _characterDetailFlow.value = it }
    }

}