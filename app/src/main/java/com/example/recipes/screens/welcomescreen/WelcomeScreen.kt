package com.example.recipes.screens.welcomescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.common.composable.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun WelcomeScreen(
  openScreen: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: WelcomeViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsState(initial = WelcomeUiState(false))

  Scaffold(

    bottomBar = {
      BottomBar(modifier,
        onMyRecipesClick = { viewModel.onMyRecipesClick(openScreen) },
        onAddClick = { viewModel.onAddClick(openScreen) },
        onSettingsClick = { viewModel.onSettingsClick(openScreen) },
        onOverviewClick = { viewModel.onOverviewClick(openScreen)}
      )})
  {

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {}
Text("Welcome Screen")
        }
      }



