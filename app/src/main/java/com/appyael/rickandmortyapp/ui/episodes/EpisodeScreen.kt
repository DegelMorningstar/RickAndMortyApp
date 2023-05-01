package com.appyael.rickandmortyapp.ui.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appyael.rickandmortyapp.R
import com.appyael.rickandmortyapp.domain.model.Episode
import com.appyael.rickandmortyapp.ui.components.FullScreenLoading
import com.appyael.rickandmortyapp.ui.components.NormalCard
import com.appyael.rickandmortyapp.ui.components.Pagination
import com.appyael.rickandmortyapp.ui.components.TitleSection
import com.appyael.rickandmortyapp.ui.theme.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun EpisodeScreen(
    episodesViewModel: EpisodesViewModel = hiltViewModel()
) {
    val isLoading: Boolean by episodesViewModel.isLoading.observeAsState(initial = false)
    val showPrev: Boolean by episodesViewModel.showPrev.observeAsState(initial = false)
    val showNext: Boolean by episodesViewModel.showNext.observeAsState(initial = false)
    val episodesList: List<Episode>? by episodesViewModel.episodeList.observeAsState(initial = null)

    LaunchedEffect(key1 = true) {
        episodesViewModel.getEpisodesList(false)
    }
    if (isLoading)
        FullScreenLoading()
    else
        EpisodeScreenMainContent(
            showPrev = showPrev,
            showNext = showNext,
            episodesList = episodesList,
            onShowNext = {
                episodesViewModel.getEpisodesList(true)
            },
            onShowPrev = {
                episodesViewModel.getEpisodesList(false)
            },
            isLoading = isLoading,
            onRefresh = {
                episodesViewModel.getEpisodesList(false)
            }
        )
}

@Composable
fun EpisodeScreenMainContent(
    modifier: Modifier = Modifier,
    showPrev: Boolean = false,
    showNext: Boolean = false,
    isLoading:Boolean,
    episodesList: List<Episode>?,
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
                stringResource(R.string.episodes_section_title)
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
                EpisodesList(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 56.dp),
                    episodesList = episodesList
                )
            }
        }
    }

}


@Composable
private fun EpisodesList(
    modifier: Modifier = Modifier,
    episodesList: List<Episode>?
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 24.dp),
        modifier = modifier,
        content = {
            if (episodesList != null) {
                items(episodesList.size) { index ->
                    NormalCard(
                        text1 = episodesList[index].name,
                        text2 = episodesList[index].airDate,
                        text3 = episodesList[index].episode
                    )
                }
            } else {
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