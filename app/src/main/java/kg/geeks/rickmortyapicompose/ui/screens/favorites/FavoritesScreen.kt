package kg.geeks.rickmortyapicompose.ui.screens.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import kg.geeks.rickmortyapicompose.R
import kg.geeks.rickmortyapicompose.data.db.FavoriteCharacterEntity
import kg.geeks.rickmortyapicompose.ui.theme.DarkGray
import kg.geeks.rickmortyapicompose.ui.theme.Gray
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoritesScreen(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = koinViewModel()
) {
    val favoriteCharacters by favoritesViewModel.favoriteCharactersFlow.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        items(favoriteCharacters) { favorite ->
            FavoriteItem(
                favorite = favorite,
                navController = navController,
                onRemoveFavorite = { favoritesViewModel.removeFavorite(favorite) }
            )
        }
    }
}

@Composable
fun FavoriteItem(
    favorite: FavoriteCharacterEntity,
    navController: NavController,
    onRemoveFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Gray)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .width(148.dp)
                    .fillMaxHeight(),
                contentDescription = "Favorite Character Image",
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(favorite.image)
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = favorite.name ?: "Unknown",
                    color = White,
                    fontSize = 26.sp
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
                        colorFilter = when (favorite.status) {
                            "Dead" -> ColorFilter.tint(Red)
                            "Alive" -> ColorFilter.tint(Green)
                            else -> ColorFilter.tint(White)
                        }

                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .width(146.dp),
                        text = "${favorite.status} - ${favorite.species}",
                        color = White,
                        fontSize = 18.sp
                    )
                    IconButton(onClick = onRemoveFavorite) {
                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(2.dp),
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Remove Favorite",
                            tint = Red,
                        )
                    }
                }
            }
        }
    }
}
