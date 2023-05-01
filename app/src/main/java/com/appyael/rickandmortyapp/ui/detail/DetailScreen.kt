package com.appyael.rickandmortyapp.ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.appyael.rickandmortyapp.R
import com.appyael.rickandmortyapp.domain.model.Character
import com.appyael.rickandmortyapp.ui.components.FullScreenLoading
import com.appyael.rickandmortyapp.ui.components.TitleSection
import com.appyael.rickandmortyapp.ui.theme.*

@Composable
fun DetailScreen(
    detailCharacterViewModel: DetailCharacterViewModel = hiltViewModel()
) {
    val isLoading: Boolean by detailCharacterViewModel.isLoading.observeAsState(initial = false)
    val character: Character? by detailCharacterViewModel.character.observeAsState(initial = null)
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true){
        detailCharacterViewModel.getCharacter()
    }
    if(isLoading)
        FullScreenLoading()
    else
    DetailScreenMainContent(
        character = character,
        scrollState = scrollState
    )
}

@Composable
fun DetailScreenMainContent(
    modifier: Modifier = Modifier,
    character: Character?,
    scrollState: ScrollState
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background),
    ) {
        Column {
            TitleSection(
                modifier = modifier.fillMaxWidth(),
                title = stringResource(R.string.detail_title)
            )
            Column(
                modifier = modifier.verticalScroll(scrollState)
            ) {
                CharacterImage(
                    modifier = modifier.padding(vertical = 12.dp, horizontal = 30.dp),
                    image = character?.image ?: ""
                )
                CharacterName(
                    modifier = modifier,
                    name = character?.name ?: "loading"
                )
                CharacterStatus(
                    modifier = modifier,
                    status = character?.status ?: "loading"
                )
                CharacterDetail(
                    modifier = modifier,
                    text = stringResource(R.string.species_label),
                    value = character?.species ?: "loading"
                )
                CharacterDetail(
                    modifier = modifier,
                    text = stringResource(R.string.gender_label),
                    value = character?.gender ?: "loading"
                )
                CharacterDetail(
                    modifier = modifier,
                    text = stringResource(R.string.origin_label),
                    value = character?.origin ?: "loading"
                )
                CharacterDetail(
                    modifier = modifier,
                    text = stringResource(R.string.location_label),
                    value = character?.location ?: "loading"
                )
            }
        }
    }
}

@Composable
private fun CharacterDetail(
    modifier: Modifier = Modifier,
    text: String = "Species",
    value:String = "Human"
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(38.dp)
                .clip(Shapes.large)
                .background(primaryGreen)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = modifier.fillMaxWidth(0.3f)
                ) {
                    Text(
                        text = "$text:",
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Column(
                    modifier = modifier.fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = value,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

        }

    }
}

@Composable
private fun CharacterStatus(
    modifier: Modifier = Modifier,
    status:String = "status"
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = if(status == "Alive"){
                modifier
                    .clip(shape = Shapes.large)
                    .background(secondaryGreen)
                    .width(56.dp)
                    .height(32.dp)
            }else{
                modifier
                    .clip(shape = Shapes.large)
                    .background(Color.Red)
                    .width(56.dp)
                    .height(32.dp)
            },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = status,
                fontFamily = FontFamily.SansSerif,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun CharacterName(
    modifier: Modifier = Modifier,
    name:String = "name"
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 26.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Composable
fun CharacterImage(
    modifier: Modifier = Modifier,
    image: String?
) {
    Box(
        modifier = modifier.size(300.dp)
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
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