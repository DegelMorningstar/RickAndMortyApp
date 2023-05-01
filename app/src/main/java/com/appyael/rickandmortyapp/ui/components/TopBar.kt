package com.appyael.rickandmortyapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.appyael.rickandmortyapp.R
import com.appyael.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import com.appyael.rickandmortyapp.ui.theme.primaryGreen
import com.appyael.rickandmortyapp.ui.theme.primaryWhite

@Composable
fun RickAndMortyTopBar(
    navController: NavController? = null,
    topBarState: Boolean = true,
    title: String = stringResource(R.string.app_title)
) {
    AnimatedVisibility(
        visible = topBarState,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    )
                },
                backgroundColor = primaryGreen,
                navigationIcon = if (navController?.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                tint = primaryWhite,
                                contentDescription = "Back"
                            )
                        }
                    }
                } else {
                    null
                }
            )
        }
    )
}

@Preview
@Composable
fun TopBarPreview() {
    RickAndMortyAppTheme {
        RickAndMortyTopBar()
    }
}