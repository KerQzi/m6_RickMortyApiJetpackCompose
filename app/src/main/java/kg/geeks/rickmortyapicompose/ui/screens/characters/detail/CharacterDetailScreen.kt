package kg.geeks.rickmortyapicompose.ui.screens.characters.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import kg.geeks.rickmortyapicompose.data.db.FavoriteCharacterEntity
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kg.geeks.rickmortyapicompose.ui.components.CharacterDetailCard
import kg.geeks.rickmortyapicompose.ui.screens.characters.CharacterViewModel
import kg.geeks.rickmortyapicompose.ui.screens.favorites.FavoritesViewModel
import kg.geeks.rickmortyapicompose.ui.theme.DarkGray
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharacterDetailScreen(
    characterId: String,
    navController: NavController,
    characterViewModel: CharacterViewModel = koinViewModel(),
    favoritesViewModel: FavoritesViewModel = koinViewModel()
) {
    val idInt = characterId.toIntOrNull() ?: 0

    LaunchedEffect(idInt) {
        characterViewModel.fetchCharacterDetail(idInt)
    }

    val character by characterViewModel.characterDetailFlow.collectAsState()
    val favoriteCharacters by favoritesViewModel.favoriteCharactersFlow.collectAsState(initial = emptyList())

    val isFavorite = favoriteCharacters.any { it.id == character?.id }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        AsyncImage(
            model = character?.image,
            contentDescription = character?.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp),
            contentScale = ContentScale.Crop
        )

        CharacterDetailCard(
            character = character,
            isFavorite = isFavorite,
            onFavoriteClick = {
                character?.let {
                    if (isFavorite) {
                        favoritesViewModel.removeFavorite(it as FavoriteCharacterEntity)
                    } else {
                        favoritesViewModel.addFavorite(it)
                    }
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun CharacterDetailScreenPreview() {
    CharacterDetailScreen(
        characterId = "1",
        navController = rememberNavController()
    )
}