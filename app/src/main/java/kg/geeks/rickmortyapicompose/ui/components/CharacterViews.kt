package kg.geeks.rickmortyapicompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kg.geeks.rickmortyapicompose.data.dto.ResponseCharacterModel
import kg.geeks.rickmortyapicompose.ui.theme.DarkGray
import kg.geeks.rickmortyapicompose.ui.theme.fontFamily

@Composable
fun CharacterDetailCard(
    character: ResponseCharacterModel?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkGray),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = character?.name.orEmpty(),
            fontSize = 40.sp,
            style = TextStyle(lineHeight = 44.sp, fontFamily = fontFamily),
            color = Color.White
        )
        Text(text = "Status: ${character?.status}", fontSize = 18.sp, color = Color.LightGray)
        Text(text = "Species: ${character?.species}", fontSize = 18.sp, color = Color.LightGray)
        Text(text = "Gender: ${character?.gender}", fontSize = 18.sp, color = Color.LightGray)

        Spacer(modifier = Modifier.height(8.dp))

        IconButton(
            onClick = onFavoriteClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxSize()

        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite",
                tint = Color.Red,
                modifier = Modifier.size(78.dp)
            )
        }
    }
}