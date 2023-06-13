package com.example.recipes.screens.welcomescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.recipes.common.composable.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun WelcomeScreen(
  openScreen: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: WelcomeViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsState(initial = WelcomeUiState(false))

  Column(    modifier = Modifier.padding(40.dp), verticalArrangement = Arrangement.Center) {


    Row(){
      BoxExample1()
    }
    BottomBar(modifier,
      onMyRecipesClick = { viewModel.onMyRecipesClick(openScreen) },
      onAddClick = { viewModel.onAddClick(openScreen) },
      onSettingsClick = { viewModel.onSettingsClick(openScreen) },
      onOverviewClick = { viewModel.onOverviewClick(openScreen)}
    )

  }
      }




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxExample1() {

  Column(
    modifier = Modifier
      .padding(40.dp)
        .clip(shape = RoundedCornerShape(20.dp))
      .background(Color.Black, shape = RoundedCornerShape(20.dp)))
   // verticalArrangement: Arrangement.Vertical = Arrangement.Top,
   // horizontalAlignment: Alignment.Horizontal = Alignment.Start  )
  {
    Text(text = "Explore recipes collection",
      modifier = Modifier.padding(45.dp),
      color = Color.White
    )
  Box(
    modifier = Modifier
      .size(180.dp, 300.dp)
      .align(Alignment.CenterHorizontally)
  ) {


    Box(modifier = Modifier.height(250.dp)){

      AsyncImage(
        model = "https://firebasestorage.googleapis.com/v0/b/recipe-app-6b055.appspot.com/o/images%2Fpablo-merchan-montes-hyIE90CN6b0-unsplash.jpg?alt=media&token=4240b6b6-7c43-4c34-a101-80cebf43b720",
        contentDescription = "Food",
        modifier = Modifier.align(Alignment.BottomCenter),
        contentScale = ContentScale.FillHeight

      )
    }
  }


  }


}


