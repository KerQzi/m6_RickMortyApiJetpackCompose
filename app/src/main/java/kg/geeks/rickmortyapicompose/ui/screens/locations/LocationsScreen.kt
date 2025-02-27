package kg.geeks.rickmortyapicompose.ui.screens.locations

import androidx.compose.foundation.*
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
fun LocationsScreen(navController: NavController, viewModel: LocationViewModel = koinViewModel()) {

    val locationsFlow by viewModel.locationsFlow.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        items(locationsFlow) { location ->
            ListItem(
                id = location.id ?: 0,
                name = location.name ?: "Unknown",
                onClick = {
                    navController.navigate("${Screen.LocationDetail.route}/${location.id}")
                }
            )
        }
    }
}
