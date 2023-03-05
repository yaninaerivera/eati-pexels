package com.eati.pexels.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eati.pexels.domain.Photo
import coil.compose.AsyncImage
import com.eati.pexels.R

@Composable
fun PhotosScreen(viewModel: PhotosViewModel) {
    val result by viewModel.photosFlow.collectAsState()

    Photos(result, viewModel::updateResults)
}


@Composable
fun Photos(results: List<Photo>, updateResults: (String) -> Unit) {
    HomeScreen(results,updateResults)
}

@Composable
fun HomeScreen(results: List<Photo>, updateResults: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier) {
        Spacer(Modifier.height(16.dp))
        SearchBar(updateResults,modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(16.dp))
        HomeBody(results,modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun SearchBar(updateResults: (String) -> Unit,
              modifier: Modifier = Modifier
) {
    var search by remember { mutableStateOf("") }
    Row() {
        TextField(
            value = search,
            onValueChange = { search = it },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
            placeholder = { Text("Search") },
            modifier = modifier.heightIn(min = 56.dp)
        )

        Button(
            onClick =  { updateResults(search) },
            modifier = Modifier.heightIn(min = 56.dp)
        ){
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
    }
}

@Composable
fun HomeBody(results: List<Photo>,
             modifier: Modifier = Modifier ){
    LazyColumn(){
        items(results){ item ->
            BodyElementWithAnimation(item,modifier)
        }
    }
}

@Composable
fun BodyElementWithAnimation( photo: Photo,
                              modifier: Modifier = Modifier
){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var showInfo by remember { mutableStateOf(false) }

    Surface(
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val width by animateDpAsState(if (showInfo) screenWidth else screenWidth / 3)
            val height by animateDpAsState(if (showInfo) screenHeight  else screenHeight / 3)

            AsyncImage(
                model = photo.sourceImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(width, height)
                    .clickable { showInfo = !showInfo }
            )

            AnimatedVisibility(
                visible = !showInfo
            ){
                Column(
                    modifier
                        .padding(10.dp)
                        .size(screenWidth / 2, screenHeight / 3)
                ) {
                    BodyText(photo)
                }
            }
        }
    }
}

@Composable
fun BodyText(photo: Photo) {
    AuthorElement(photo)
    Spacer(Modifier.height(8.dp))
    Paragraph(photo)
    FavoriteIcon(photo.liked)
}

@Composable
fun AuthorElement(photo: Photo) {
    Row(){
        Icon(
            tint = MaterialTheme.colors.primary,
            imageVector = ImageVector.vectorResource(R.drawable.baseline_photo_camera_24),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(Modifier.width(8.dp))

        Text(
            text = photo.photographer,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.h3
        )
    }

}

@Composable
fun Paragraph(photo: Photo, modifier : Modifier = Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = ParagraphStyle(lineHeight = 22.sp)
            ) {
                withStyle(
                    style = SpanStyle(
                        fontSize = 15.sp
                    )
                ) {
                    append("Description: ")
                    append(photo.alt)
                    append("\n")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp
                    )
                ) {
                    append("Size: ")
                    append(photo.width.toString())
                    append("x")
                    append(photo.width.toString())
                    append("\n")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 11.sp
                    )
                ) {
                    append("AvgColor: ")
                    append(photo.avgColor)
                    append("\n")
                }
            }
        }
    )
}

@Composable
fun FavoriteIcon(isFavorite:Boolean) {
    Icon(
        tint = Color(0xFFE4405F),
        imageVector = if (isFavorite) {
            Icons.Filled.Favorite
        } else {
            Icons.Default.FavoriteBorder
        },
        contentDescription = null
    )
}


