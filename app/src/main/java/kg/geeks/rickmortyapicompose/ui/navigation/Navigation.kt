package kg.geeks.rickmortyapicompose.ui.navigation

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kg.geeks.rickmortyapicompose.ui.screens.characters.detail.CharacterDetailScreen
import kg.geeks.rickmortyapicompose.ui.screens.characters.CharactersScreen
import kg.geeks.rickmortyapicompose.ui.screens.episodes.EpisodesScreen
import kg.geeks.rickmortyapicompose.ui.screens.episodes.detail.EpisodeDetailScreen
import kg.geeks.rickmortyapicompose.ui.screens.favorites.FavoritesScreen
import kg.geeks.rickmortyapicompose.ui.screens.locations.LocationsScreen
import kg.geeks.rickmortyapicompose.ui.screens.locations.detail.LocationDetailScreen
import kg.geeks.rickmortyapicompose.ui.theme.DarkGray

data class BottomNavItem(val screen: Screen, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    //скрытие апп бара для детальных экранов
    val showAppBars = when {
        currentRoute?.startsWith(Screen.CharacterDetail.route) == true ||
                currentRoute?.startsWith(Screen.LocationDetail.route) == true ||
                currentRoute?.startsWith(Screen.EpisodeDetail.route) == true -> false

        else -> true
    }

    // Настройка смещения для BottomAppBar
    val bottomBarHeight = 100.dp
    val density = LocalDensity.current
    val bottomBarHeightPx = with(density) { bottomBarHeight.toPx() }
    var bottomBarOffset by remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // available.y > 0 при скролле вниз (контент движется вверх)
                val delta = available.y
                bottomBarOffset = (bottomBarOffset + delta)
                    .coerceIn(-bottomBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    val effectiveBottomPadding = with(density) {
        // (bottomBarHeightPx + bottomBarOffset) может быть меньше 0 – ограничиваем снизу 0
        (bottomBarHeightPx + bottomBarOffset).coerceAtLeast(0f).toDp()
    }

    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        containerColor = DarkGray,
        topBar = {
            if (showAppBars) {
                TopAppBar(
                    title = {
                        Text(
                            text = currentScreenTitle(currentRoute).toString(),
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = DarkGray
                    )
                )
            }
        },
        bottomBar = {
            if (showAppBars) {
                BottomNavigationBar(navController, currentRoute, modifier = Modifier.graphicsLayer { translationY = -bottomBarOffset })
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Favorites.route,
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = effectiveBottomPadding
            )
        ) {
            composable(Screen.Characters.route) {
                CharactersScreen(navController)
            }
            composable(Screen.Locations.route) {
                LocationsScreen(navController)
            }
            composable(Screen.Episodes.route) {
                EpisodesScreen(navController)
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(navController)
            }
            composable(
                "${Screen.CharacterDetail.route}/{characterId}",
                enterTransition = {
                    slideIn(
                        initialOffset = { IntOffset(0, -it.height) }, // Появление сверху
                        animationSpec = tween(durationMillis = 500, easing = EaseInOut)
                    ) + fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    slideOut(
                        targetOffset = { IntOffset(0, it.height) }, // Уход вниз
                        animationSpec = tween(durationMillis = 400, easing = EaseInOut)
                    ) + fadeOut(animationSpec = tween(300))
                },
                popEnterTransition = {
                    slideIn(
                        initialOffset = { IntOffset(0, it.height) }, // Возвращение снизу
                        animationSpec = tween(durationMillis = 500, easing = EaseInOut)
                    ) + fadeIn(animationSpec = tween(500))
                },
                popExitTransition = {
                    slideOut(
                        targetOffset = { IntOffset(0, it.height) }, // Уход вверх при возврате
                        animationSpec = tween(durationMillis = 400, easing = EaseInOut)
                    ) + fadeOut(animationSpec = tween(300))
                }) { backStackEntry ->
                backStackEntry.arguments?.getString("characterId")?.let {
                    CharacterDetailScreen(
                        characterId = it,
                        navController = navController
                    )
                }
            }
            composable(
                "${Screen.LocationDetail.route}/{locationId}",
                enterTransition = {
                    slideIn(
                        initialOffset = { IntOffset(0, -it.height) },
                        animationSpec = tween(durationMillis = 500, easing = EaseInOut)
                    ) + fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    slideOut(
                        targetOffset = { IntOffset(0, it.height) },
                        animationSpec = tween(durationMillis = 400, easing = EaseInOut)
                    ) + fadeOut(animationSpec = tween(300))
                },
                popEnterTransition = {
                    slideIn(
                        initialOffset = { IntOffset(0, it.height) },
                        animationSpec = tween(durationMillis = 500, easing = EaseInOut)
                    ) + fadeIn(animationSpec = tween(500))
                },
                popExitTransition = {
                    slideOut(
                        targetOffset = { IntOffset(0, it.height) },
                        animationSpec = tween(durationMillis = 400, easing = EaseInOut)
                    ) + fadeOut(animationSpec = tween(300))
                }) { backStackEntry ->
                backStackEntry.arguments?.getString("locationId")?.let {
                    LocationDetailScreen(
                        locationId = it,
                        navController = navController
                    )
                }
            }
            composable(
                "${Screen.EpisodeDetail.route}/{episodeId}",
                enterTransition = {
                    slideIn(
                        initialOffset = { IntOffset(0, -it.height) },
                        animationSpec = tween(durationMillis = 500, easing = EaseInOut)
                    ) + fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    slideOut(
                        targetOffset = { IntOffset(0, it.height) },
                        animationSpec = tween(durationMillis = 400, easing = EaseInOut)
                    ) + fadeOut(animationSpec = tween(300))
                },
                popEnterTransition = {
                    slideIn(
                        initialOffset = { IntOffset(0, it.height) },
                        animationSpec = tween(durationMillis = 500, easing = EaseInOut)
                    ) + fadeIn(animationSpec = tween(500))
                },
                popExitTransition = {
                    slideOut(
                        targetOffset = { IntOffset(0, it.height) },
                        animationSpec = tween(durationMillis = 400, easing = EaseInOut)
                    ) + fadeOut(animationSpec = tween(300))
                }) { backStackEntry ->
                backStackEntry.arguments?.getString("episodeId")?.let {
                    EpisodeDetailScreen(
                        episodeId = it,
                        navController = navController
                    )
                }
            }
        }
    }
}


fun currentScreenTitle(currentRoute: String?): String? {
    return when {
        currentRoute == Screen.Characters.route -> Screen.Characters.title
        currentRoute == Screen.Locations.route -> Screen.Locations.title
        currentRoute == Screen.Episodes.route -> Screen.Episodes.title
        currentRoute == Screen.Favorites.route -> Screen.Favorites.title
        currentRoute?.startsWith(Screen.CharacterDetail.route) == true -> "Детали персонажа"
        currentRoute?.startsWith(Screen.LocationDetail.route) == true -> "Детали локации"
        currentRoute?.startsWith(Screen.EpisodeDetail.route) == true -> "Детали эпизода"
        else -> ""
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?, modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavItem(Screen.Characters, androidx.compose.material.icons.Icons.Filled.Home),
        BottomNavItem(Screen.Locations, androidx.compose.material.icons.Icons.Filled.Place),
        BottomNavItem(Screen.Episodes, androidx.compose.material.icons.Icons.Filled.DateRange),
        BottomNavItem(Screen.Favorites, androidx.compose.material.icons.Icons.Filled.Favorite)
    )

    NavigationBar(
        containerColor = DarkGray,
        modifier = modifier
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.screen.route
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.2f else 1f,
                animationSpec = tween(durationMillis = 200)
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.screen.title,
                        modifier = Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
                    )
                },
                label = { item.screen.title?.let { Text(it) } },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = Color.Black
                )
            )
        }
    }
}
