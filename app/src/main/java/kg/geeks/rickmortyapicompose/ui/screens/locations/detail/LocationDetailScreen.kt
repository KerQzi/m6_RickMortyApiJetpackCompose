package kg.geeks.rickmortyapicompose.ui.screens.locations.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kg.geeks.rickmortyapicompose.data.dto.ResponseLocationModel
import kg.geeks.rickmortyapicompose.ui.screens.locations.LocationViewModel
import kg.geeks.rickmortyapicompose.ui.theme.DarkGray
import kg.geeks.rickmortyapicompose.ui.theme.fontFamily
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LocationDetailScreen(
    locationId: String,
    navController: NavController,
    viewModel: LocationViewModel = koinViewModel()
) {
    var location by remember { mutableStateOf<ResponseLocationModel?>(null) }
    val idInt = locationId.toIntOrNull() ?: 0

    LaunchedEffect(locationId) {
        viewModel.fetchLocationDetail(idInt) {
            location = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .padding(16.dp)
    ) {
        Text(
            text = location?.name.toString(),
            fontSize = 50.sp,
            style = TextStyle(lineHeight = 54.sp, fontFamily = fontFamily),
            color = White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Type: ${location?.type ?: "Unknown"}", fontSize = 18.sp)
        Text(text = "Dimension: ${location?.dimension ?: "Unknown"}", fontSize = 18.sp)
        Text(text = "Created: ${location?.created ?: "Unknown"}", fontSize = 18.sp)
    }
}

@Preview
@Composable
fun PreviewLocationDetailScreen() {
    LocationDetailScreen(
        locationId = "1",
        navController = rememberNavController()
    )
}
