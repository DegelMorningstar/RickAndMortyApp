package com.appyael.rickandmortyapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.appyael.rickandmortyapp.ui.characters.CharacterScreen
import com.appyael.rickandmortyapp.ui.detail.DetailScreen
import com.appyael.rickandmortyapp.ui.episodes.EpisodeScreen
import com.appyael.rickandmortyapp.ui.locations.LocationScreen

@Composable
fun RickAndMortyNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination:String = RickAndMortyRoutes.Characters.route,
    navigateToDetail: (Int) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){

        composable(RickAndMortyRoutes.Characters.route){
            CharacterScreen(
                onItemClicked = {
                    navigateToDetail(it)
                }
            )
        }

        composable(
            route = RickAndMortyRoutes.Detail.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            DetailScreen()
        }

        composable(RickAndMortyRoutes.Episodes.route){
            EpisodeScreen()
        }

        composable(RickAndMortyRoutes.Locations.route){
            LocationScreen()
        }

    }

}