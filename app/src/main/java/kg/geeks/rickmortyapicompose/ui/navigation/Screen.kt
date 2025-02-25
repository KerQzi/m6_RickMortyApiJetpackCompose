package kg.geeks.rickmortyapicompose.ui.navigation

sealed class Screen(val route: String, val title: String?) {
    data object Characters : Screen("characters", "Персонажи")
    data object Locations : Screen("locations", "Локации")
    data object Episodes : Screen("episodes", "Эпизоды")
    data object CharacterDetail : Screen("character_detail", "Детали персонажа")
    data object LocationDetail : Screen("location_detail", "Детали локации")
    data object EpisodeDetail : Screen("episode_detail", "Детали эпизода")
}