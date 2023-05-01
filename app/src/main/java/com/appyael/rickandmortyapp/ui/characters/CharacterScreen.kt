package com.appyael.rickandmortyapp.ui.characters

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.appyael.rickandmortyapp.R
import com.appyael.rickandmortyapp.domain.model.Character
import com.appyael.rickandmortyapp.domain.model.Episode
import com.appyael.rickandmortyapp.ui.components.FullScreenLoading
import com.appyael.rickandmortyapp.ui.components.Pagination
import com.appyael.rickandmortyapp.ui.components.TitleSection
import com.appyael.rickandmortyapp.ui.theme.background
import com.appyael.rickandmortyapp.ui.theme.primaryGreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CharacterScreen(
    charactersViewModel: CharactersViewModel = hiltViewModel(),
    onItemClicked: (Int) -> Unit,
) {
    val isLoading: Boolean by charactersViewModel.isLoading.observeAsState(initial = false)
    val showPrev: Boolean by charactersViewModel.showPrev.observeAsState(initial = false)
    val showNext: Boolean by charactersViewModel.showNext.observeAsState(initial = false)
    val charactersList: List<Character>? by charactersViewModel.charactersList.observeAsState(initial = null)

    LaunchedEffect(key1 = true) {
        charactersViewModel.getCharactersList(false)
    }

    if(isLoading)
        FullScreenLoading()
    else
    CharacterScreenMainContent(
        showPrev = showPrev,
        showNext = showNext,
        charactersList = charactersList,
        isLoading = isLoading,
        onShowNext = {
            charactersViewModel.getCharactersList(true)
        },
        onShowPrev = {
            charactersViewModel.getCharactersList(false)
        },
        onItemClicked = { onItemClicked(it) },
        onRefresh = {
            charactersViewModel.getCharactersList(false)
        }
    )
}

@Composable
fun CharacterScreenMainContent(
    modifier: Modifier = Modifier,
    showPrev: Boolean = false,
    showNext: Boolean = false,
    isLoading:Boolean,
    charactersList: List<Character>?,
    onShowNext: () -> Unit,
    onShowPrev: () -> Unit,
    onItemClicked: (Int) -> Unit,
    onRefresh: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background),
    ) {
        Column {
            TitleSection(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                title = stringResource(R.string.characters_title)
            )
            Pagination(
                modifier = modifier.fillMaxWidth(),
                showPrev = showPrev,
                showNext = showNext,
                onShowNext = { onShowNext() },
                onShowPrev = { onShowPrev()}
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
                CharactersList(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 56.dp),
                    charactersList = charactersList,
                    onItemClicked = { onItemClicked(it) }
                )
            }
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CharactersList(
    modifier: Modifier = Modifier,
    charactersList: List<Character>?,
    onItemClicked: (Int) -> Unit,
) {
    val size = 3
    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(size),
        content = {
            if (charactersList != null){
                items(charactersList){ character ->
                    CharacterImage(
                        item = character,
                        onItemClicked = { onItemClicked(it) }
                    )
                }
            }else{
                item(span = { GridItemSpan(size) }) {
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

@Composable
fun CharacterImage(
    modifier: Modifier = Modifier,
    item: Character,
    onItemClicked: (Int) -> Unit,
) {
    Box(
        modifier = modifier.clickable {
            onItemClicked(item.id)
        }
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.image)
                .size(Size.ORIGINAL)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}