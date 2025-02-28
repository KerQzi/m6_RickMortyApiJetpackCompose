package kg.geeks.rickmortyapicompose.ui.screens.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import kg.geeks.rickmortyapicompose.ui.components.ListItem
import kg.geeks.rickmortyapicompose.ui.components.LoadStateView
import kg.geeks.rickmortyapicompose.ui.navigation.Screen
import kg.geeks.rickmortyapicompose.ui.theme.DarkGray
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EpisodesScreen(navController: NavController, viewModel: EpisodeViewModel = koinViewModel()) {

    val episodes = viewModel.episodesFlow.collectAsLazyPagingItems()
    val state = rememberLazyListState()

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(DarkGray), state = state) {
        items(episodes.itemCount) { index ->
            episodes[index]?.let {
                ListItem(
                    id = it.id ?: 0,
                    name = it.name ?: "Unknown",
                    onClick = {
                        navController.navigate("${Screen.EpisodeDetail.route}/${it.id}")
                    }
                )
            }
        }
        item {
            if (episodes.loadState.append is LoadState.Loading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
    if (episodes.loadState.refresh is LoadState.Loading || episodes.loadState.refresh is LoadState.Error) {
        LoadStateView(
            loadState = episodes.loadState,
            onRetry = { episodes.retry() }
        )
    }
}
