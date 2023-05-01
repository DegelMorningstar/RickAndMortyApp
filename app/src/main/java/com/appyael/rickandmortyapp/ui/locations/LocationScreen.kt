package com.appyael.rickandmortyapp.ui.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appyael.rickandmortyapp.R
import com.appyael.rickandmortyapp.domain.model.Episode
import com.appyael.rickandmortyapp.domain.model.Location
import com.appyael.rickandmortyapp.ui.components.FullScreenLoading
import com.appyael.rickandmortyapp.ui.components.NormalCard
import com.appyael.rickandmortyapp.ui.components.Pagination
import com.appyael.rickandmortyapp.ui.components.TitleSection
import com.appyael.rickandmortyapp.ui.episodes.EpisodesViewModel
import com.appyael.rickandmortyapp.ui.theme.background
import com.appyael.rickandmortyapp.ui.theme.primaryGreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun LocationScreen(
    locationsViewModel: LocationsViewModel = hiltViewModel()
) {
    val isLoading: Boolean by locationsViewModel.isLoading.observeAsState(initial = false)
    val showPrev: Boolean by locationsViewModel.showPrev.observeAsState(initial = false)
    val showNext: Boolean by locationsViewModel.showNext.observeAsState(initial = false)
    val locationsList: List<Location>? by locationsViewModel.locationsList.observeAsState(initial = null)

    LaunchedEffect(key1 = true) {
        locationsViewModel.getLocationsList(false)
    }
    if(isLoading)
        FullScreenLoading()
    else
        LocationScreenMainContent(
            showPrev = showPrev,
            showNext = showNext,
            locationsList = locationsList,
            isLoading = isLoading,
            onShowNext = {
                locationsViewModel.getLocationsList(true)
            },
            onShowPrev = {
                locationsViewModel.getLocationsList(false)
            },
            onRefresh = {
                locationsViewModel.getLocationsList(false)
            }
        )
}

@Composable
fun LocationScreenMainContent(
    modifier: Modifier = Modifier,
    showPrev: Boolean = false,
    showNext: Boolean = false,
    isLoading:Boolean,
    locationsList: List<Location>?,
    onShowNext: () -> Unit,
    onShowPrev: () -> Unit,
    onRefresh: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background),
    ) {
        Column {
            TitleSection(
                modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                stringResource(R.string.locations_title)
            )
            Pagination(
                modifier = modifier.fillMaxWidth(),
                showPrev = showPrev,
                showNext = showNext,
                onShowNext = onShowNext,
                onShowPrev = onShowPrev
            )
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isLoading),
                onRefresh = {
                    onRefresh()
                },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        contentColor = primaryGreen,
                    )
                }
            ) {
                LocationList(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 56.dp),
                    locationsList = locationsList
                )
            }
        }
    }

}


@Composable
private fun LocationList(
    modifier: Modifier = Modifier,
    locationsList: List<Location>?
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 24.dp),
        modifier = modifier,
        content = {
            if (locationsList != null) {
                items(locationsList.size){ index ->
                    NormalCard(
                        text1 = locationsList[index].name,
                        text2 = locationsList[index].dimension,
                        text3 = locationsList[index].type
                    )
                }
            }else{
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No se encontraron registros",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = Color.Black
                        )

                    }
                }
            }
        })
}