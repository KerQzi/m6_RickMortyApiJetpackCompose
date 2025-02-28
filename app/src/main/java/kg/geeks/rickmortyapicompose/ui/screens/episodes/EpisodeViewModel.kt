package kg.geeks.rickmortyapicompose.ui.screens.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kg.geeks.rickmortyapicompose.data.dto.ResponseEpisodeModel
import kg.geeks.rickmortyapicompose.data.repository.EpisodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeViewModel(
    private val repository: EpisodeRepository
): ViewModel() {

    private val _episodesFlow = MutableStateFlow<PagingData<ResponseEpisodeModel>>(PagingData.empty())
    val episodesFlow = _episodesFlow.asStateFlow()

    private val _episodeDetailFlow = MutableStateFlow<ResponseEpisodeModel?>(null)
    val episodeDetailFlow: StateFlow<ResponseEpisodeModel?> = _episodeDetailFlow.asStateFlow()

    init {
        fetchEpisodes()
    }

    fun fetchEpisodes() = viewModelScope.launch(Dispatchers.IO) {
        repository.fetchEpisodes().flow.cachedIn(viewModelScope).collectLatest {
            _episodesFlow.value = it
        }
    }

    fun fetchEpisodeDetail(id: Int) = viewModelScope.launch {
        repository.fetchEpisodeDetail(id)
            .catch { _episodeDetailFlow.value = null }
            .collect { _episodeDetailFlow.value = it }
    }
}