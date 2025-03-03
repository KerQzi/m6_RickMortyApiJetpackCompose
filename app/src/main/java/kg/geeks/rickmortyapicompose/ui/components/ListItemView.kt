package kg.geeks.rickmortyapicompose.ui.components

import AnimatedCard
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kg.geeks.rickmortyapicompose.ui.theme.Gray


//одна айтемка для locations/episodes
@Composable
fun ListItem(
    id: Int,
    name: String,
    onClick: () -> Unit
) {
    AnimatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Gray),
        onClick = onClick
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "№: $id", color = White, fontSize = 14.sp)
                Text(text = name, color = White, fontSize = 18.sp)
            }
        }
    }
}