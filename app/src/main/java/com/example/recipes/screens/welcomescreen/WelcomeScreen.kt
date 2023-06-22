package com.example.recipes.screens.welcomescreen

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.R
import com.example.recipes.common.composable.*
import com.example.recipes.proxima

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun WelcomeScreen(
  openScreen: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: WelcomeViewModel = hiltViewModel()
) {

  val image = painterResource(R.drawable.chef)

  Scaffold(
    bottomBar = {
      BottomBar(
        modifier,
        onMyRecipesClick = { viewModel.onMyRecipesClick(openScreen) },
        onAddClick = { viewModel.onAddClick(openScreen) },
        onSettingsClick = { viewModel.onSettingsClick(openScreen) },
        onSearchClick = { viewModel.onOverviewSearchClick(openScreen) },
        onOverviewClick = { viewModel.onOverviewClick(openScreen) }
      )
    }
  ) {
    Column(
      verticalArrangement = Arrangement.Bottom,
    horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = "Recipe reservoir",
        modifier = Modifier.padding(40.dp),
        fontFamily = proxima,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold
      )


      Image(painter = image, contentDescription = null)
      Text(
        text = "Welcome! " + "We are happy to see you here!",
        modifier = Modifier.padding(30.dp),
        fontFamily = proxima,
        fontSize = 30.sp
      )
    }
  }
}
