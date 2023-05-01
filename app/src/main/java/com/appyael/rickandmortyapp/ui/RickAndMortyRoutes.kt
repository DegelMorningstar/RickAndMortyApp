package com.appyael.rickandmortyapp.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.appyael.rickandmortyapp.R

sealed class RickAndMortyRoutes(
    val route:String,
    val title:String,
    val resource:Int
    ){

    object Locations: RickAndMortyRoutes(
        route = "locations_screen",
        title = "Locations",
        resource = R.drawable.locations_icon
    )

    object Episodes: RickAndMortyRoutes(
        route = "episodes_screen",
        title = "Episodes",
        resource = R.drawable.episodes_icon
    )

    object Characters: RickAndMortyRoutes(
        route = "characters_screen",
        title = "Characters",
        resource = R.drawable.character_icon
    )

    object Detail: RickAndMortyRoutes(
        route = "character_detail_screen?id={id}",
        title = "Detail",
        resource = R.drawable.character_icon
    ){
        fun passId(id: Int): String {
            return "character_detail_screen?id=$id"
        }
    }
}

class RickAndMortyActions(navController: NavController) {

    val navigateToDetail = { id: Int ->
        navController.navigate(RickAndMortyRoutes.Detail.passId(id)) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}