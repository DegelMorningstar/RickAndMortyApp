package com.appyael.rickandmortyapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.appyael.rickandmortyapp.ui.RickAndMortyRoutes
import com.appyael.rickandmortyapp.ui.theme.primaryGreen
import com.appyael.rickandmortyapp.ui.theme.primaryWhite
import com.appyael.rickandmortyapp.ui.theme.secondaryWhite

@Composable
fun RickAndMortyBottomBar(
    navController: NavHostController,
    bottomBarState: Boolean) {
    val screens = listOf(
        RickAndMortyRoutes.Episodes,
        RickAndMortyRoutes.Characters,
        RickAndMortyRoutes.Locations
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    )
}

@Composable
fun RowScope.AddItem(
    screen: RickAndMortyRoutes,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(
                text = screen.title,
                fontSize = 12.sp,
                fontFamily = FontFamily.SansSerif ,
                fontWeight = FontWeight.Bold
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.resource),
                contentDescription = screen.title
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        modifier = Modifier
            .background(primaryGreen)
            .fillMaxWidth(),
        selectedContentColor = primaryWhite,
        unselectedContentColor = secondaryWhite,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(0){
                }
            }
        }
    )
}
