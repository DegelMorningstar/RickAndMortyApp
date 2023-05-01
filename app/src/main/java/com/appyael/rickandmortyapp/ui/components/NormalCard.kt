package com.appyael.rickandmortyapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appyael.rickandmortyapp.ui.theme.Shapes
import com.appyael.rickandmortyapp.ui.theme.primaryGreen
import com.appyael.rickandmortyapp.ui.theme.primaryWhite
import com.appyael.rickandmortyapp.ui.theme.secondaryGreen

@Composable
fun NormalCard(
    modifier: Modifier = Modifier,
    text1:String = "Text1",
    text2:String = "Text2",
    text3:String = "Text3"
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 12.dp),
        elevation = 4.dp,
        backgroundColor = primaryWhite,
        shape = Shapes.small
    ) {
        Column(
            modifier = modifier.padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = text1,
                    color = primaryGreen,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = modifier.fillMaxWidth(0.5f),
                    textAlign = TextAlign.Start,
                    text = text2,
                    color = secondaryGreen,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = modifier.fillMaxWidth(0.5f),
                    textAlign = TextAlign.End,
                    text = text3,
                    color = secondaryGreen,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}