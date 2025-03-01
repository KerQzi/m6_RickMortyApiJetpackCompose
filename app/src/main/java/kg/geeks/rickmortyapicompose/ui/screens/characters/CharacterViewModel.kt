package kg.geeks.rickmortyapicompose.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kg.geeks.rickmortyapicompose.data.dto.CharacterFilter
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kg.geeks.rickmortyapicompose.data.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _filter = MutableStateFlow(CharacterFilter())
    val filter = _filter.asStateFlow()

    private val _charactersFlow =
        MutableStateFlow<PagingData<ResponseCharacterModel>>(PagingData.empty())
    val charactersFlow = _charactersFlow.asStateFlow()

    private val _characterDetailFlow = MutableStateFlow<ResponseCharacterModel?>(null)
    val characterDetailFlow = _characterDetailFlow.asStateFlow()

    init {
        fetchCharacters()
    }

    fun updateFilter(newFilter: CharacterFilter) {
        _filter.value = newFilter
        fetchCharacters()
    }

    fun resetFilter() {
        _filter.value = CharacterFilter()
        fetchCharacters()
    }

    fun fetchCharacters() = viewModelScope.launch(Dispatchers.IO) {
        val currentFilter = _filter.value
        repository.fetchCharacters(
            name = currentFilter.name,
            status = currentFilter.status,
            species = currentFilter.species,
            gender = currentFilter.gender
        ).flow
            .cachedIn(viewModelScope)
            .collect { _charactersFlow.value = it }
    }

    fun fetchCharacterDetail(id: Int) = viewModelScope.launch {
        repository.fetchCharacterDetail(id)
            .catch { _characterDetailFlow.value = null }
            .collect { _characterDetailFlow.value = it }
    }

}