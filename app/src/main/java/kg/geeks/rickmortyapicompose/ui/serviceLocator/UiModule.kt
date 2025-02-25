package kg.geeks.rickmortyapicompose.ui.serviceLocator

import kg.geeks.rickmortyapicompose.ui.screens.characters.CharacterViewModel
import kg.geeks.rickmortyapicompose.ui.screens.episodes.EpisodeViewModel
import kg.geeks.rickmortyapicompose.ui.screens.locations.LocationViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule: Module = module {
    viewModel { CharacterViewModel(get()) }
    viewModel { LocationViewModel(get()) }
    viewModel { EpisodeViewModel(get()) }
}