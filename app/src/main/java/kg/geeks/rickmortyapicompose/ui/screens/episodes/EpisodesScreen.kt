package kg.geeks.rickmortyapicompose.ui.screens.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kg.geeks.rickmortyapicompose.ui.components.ListItem
import kg.geeks.rickmortyapicompose.ui.navigation.Screen
import kg.geeks.rickmortyapicompose.ui.theme.DarkGray
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EpisodesScreen(navController: NavController, viewModel: EpisodeViewModel = koinViewModel()) {

    val episodesFlow by viewModel.episodesFlow.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().background(DarkGray)) {
        items(episodesFlow) { episode ->
            ListItem(
                id = episode.id ?: 0,
                name = episode.name ?: "Unknown",
                onClick = {
                    navController.navigate(Screen.EpisodeDetail.route + "/${episode.id}")
                }
            )
        }
    }
}
