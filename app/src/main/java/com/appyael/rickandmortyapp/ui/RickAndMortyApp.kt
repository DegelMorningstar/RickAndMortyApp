package com.appyael.rickandmortyapp.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.appyael.rickandmortyapp.RickAndMortyApplication
import com.appyael.rickandmortyapp.ui.components.RickAndMortyBottomBar
import com.appyael.rickandmortyapp.ui.components.RickAndMortyTopBar

@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val topBarState = rememberSaveable { (mutableStateOf(true)) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val navigationActions = remember(navController) {
        RickAndMortyActions(navController)
    }

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        RickAndMortyRoutes.Detail.route -> {
            bottomBarState.value = false
            topBarState.value = true
        }
        else -> {
            bottomBarState.value = true
            topBarState.value = true
        }
    }

    Scaffold(
        topBar = {
            RickAndMortyTopBar(
                navController = navController,
                topBarState = topBarState.value
            )
        },
        bottomBar = {
            RickAndMortyBottomBar(
                navController = navController,
                bottomBarState = bottomBarState.value
            )
        }
    ) {
        RickAndMortyNavGraph(
            navController = navController,
            navigateToDetail = navigationActions.navigateToDetail
        )
    }
}