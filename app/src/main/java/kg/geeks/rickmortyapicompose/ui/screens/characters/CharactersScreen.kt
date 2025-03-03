package kg.geeks.rickmortyapicompose.ui.screens.characters


import AnimatedCard
import CharacterFilterDialog
import SearchBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter.Companion
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kg.geeks.rickmortyapicompose.R
import kg.geeks.rickmortyapicompose.data.dto.CharacterFilter
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kg.geeks.rickmortyapicompose.ui.components.LoadStateView
import kg.geeks.rickmortyapicompose.ui.navigation.Screen
import kg.geeks.rickmortyapicompose.ui.theme.DarkGray
import kg.geeks.rickmortyapicompose.ui.theme.Gray
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharactersScreen(
    navController: NavController,
    viewModel: CharacterViewModel = koinViewModel()
) {
//    val characters = listOf(
//        Character(
//            id = 1,
//            name = "Dracula",
//            status = Status.Dead,
//            image = "https://rickandmortyapi.com/api/character/avatar/709.jpeg",
//            species = "Mythological Creature",
//            gender = "Male",
//            location = "Interdimensional Cable"
//        ),
//    )


    val characters = viewModel.charactersFlow.collectAsLazyPagingItems()
    var showFilterDialog by remember { mutableStateOf(false) }
    val filterState by viewModel.filter.collectAsState(initial = CharacterFilter())
    val state = rememberLazyListState()

    val isRefreshing = characters.loadState.refresh is LoadState.Loading

    val coroutineScope = rememberCoroutineScope()
    var totalDrag by remember { mutableStateOf(0f) }
    val refreshThreshold = 150f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { _, dragAmount ->
                        totalDrag += dragAmount
                    },
                    onDragEnd = {
                        if (totalDrag > refreshThreshold) {
                            // Скроллим к началу списка и запускаем refresh
                            coroutineScope.launch {
                                state.animateScrollToItem(0)
                            }
                            characters.refresh()
                        }
                        totalDrag = 0f
                    }
                )
            }
    ) {
        SearchBar(
            searchText = filterState.name.orEmpty(),
            onSearchTextChanged = { newText ->
                viewModel.updateFilter(filterState.copy(name = newText.ifEmpty { null }))
            },
            onFilterClick = { showFilterDialog = true }
        )
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { coroutineScope.launch {
                state.animateScrollToItem(0)
            }
                characters.refresh()},
            modifier = Modifier.fillMaxSize()
        ) {
            if (characters.itemCount == 0 && characters.loadState.refresh !is LoadState.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ничего не найдено",
                        fontSize = 24.sp,
                        color = White
                    )
                }
            } else {
                LazyColumn(
                    state = state,
                    modifier = Modifier.fillMaxSize()

                ) {
                    items(characters.itemCount) { index ->
                        characters[index]?.let {
                            CharactersItem(character = it, navController = navController)
                        }
                    }
                }
                if (characters.loadState.refresh is LoadState.Loading || characters.loadState.refresh is LoadState.Error) {
                    LoadStateView(
                        loadState = characters.loadState,
                        onRetry = { characters.retry() }
                    )
                }
            }
        }
    }
    if (showFilterDialog) {
        CharacterFilterDialog(
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

@Composable
fun CharactersItem(
    character: ResponseCharacterModel,
    navController: NavController
) {
    AnimatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Gray
        ),
        onClick = {
            navController.navigate("${Screen.CharacterDetail.route}/${character.id}")
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .width(148.dp)
                    .fillMaxHeight(),
                contentDescription = "Character image",
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(character.image),
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    text = character.name.toString(), color = White, fontSize = 26.sp
                )
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(
                            R.drawable.circle
                        ),
                        colorFilter = when (character.status) {
                            "Dead" -> Companion.tint(Red)
                            "Alive" -> Companion.tint(Green)
                            else -> Companion.tint(White)
                        }

                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "${character.status} - ${character.species}",
                        color = White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharactersScreenPreview() {
    CharactersScreen(navController = NavController(LocalContext.current))
}