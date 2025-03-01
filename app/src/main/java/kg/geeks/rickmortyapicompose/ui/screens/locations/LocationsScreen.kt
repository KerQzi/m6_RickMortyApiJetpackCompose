package kg.geeks.rickmortyapicompose.ui.screens.locations

import LocationFilterDialog
import SearchBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import kg.geeks.rickmortyapicompose.data.dto.LocationFilter
import kg.geeks.rickmortyapicompose.ui.components.ListItem
import kg.geeks.rickmortyapicompose.ui.components.LoadStateView
import kg.geeks.rickmortyapicompose.ui.navigation.Screen
import kg.geeks.rickmortyapicompose.ui.theme.DarkGray
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LocationsScreen(navController: NavController, viewModel: LocationViewModel = koinViewModel()) {

    val locations = viewModel.locationsFlow.collectAsLazyPagingItems()
    var showFilterDialog by remember { mutableStateOf(false) }
    val filterState by viewModel.filter.collectAsState(initial = LocationFilter())
    val state = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        SearchBar(
            searchText = filterState.name.orEmpty(),
            onSearchTextChanged = { newText ->
                viewModel.updateFilter(filterState.copy(name = newText.ifEmpty { null }))
            },
            onFilterClick = { showFilterDialog = true }
        )
        if (locations.itemCount == 0 && locations.loadState.refresh !is LoadState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nothing found",
                    fontSize = 24.sp,
                    color = White
                )
            }
        } else {
            LazyColumn(
                state = state,
                modifier = Modifier.fillMaxSize()
            ) {
                items(locations.itemCount) { index ->
                    locations[index]?.let { location ->
                        ListItem(
                            id = location.id ?: 0,
                            name = location.name ?: "Unknown",
                            onClick = {
                                navController.navigate("${Screen.LocationDetail.route}/${location.id}")
                            }
                        )
                    }
                }
                item {
                    if (locations.loadState.append is LoadState.Loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
            if (locations.loadState.refresh is LoadState.Loading || locations.loadState.refresh is LoadState.Error) {
                LoadStateView(
                    loadState = locations.loadState,
                    onRetry = { locations.retry() }
                )
            }
        }
    }
    if (showFilterDialog) {
        LocationFilterDialog(
            currentFilter = filterState,
            onApply = { newFilter ->
                viewModel.updateFilter(newFilter)
                showFilterDialog = false
            },
            onDismiss = { showFilterDialog = false },
            onReset = {
                viewModel.resetFilter()
                showFilterDialog = false
            }
        )
    }
}