package com.example.recipes.screens.detailrecipes

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.recipes.common.composable.*
import com.example.recipes.core.Constants
import com.example.recipes.model.storage.presentation.components.AddImageToDatabase
import com.example.recipes.model.storage.presentation.components.AddImageToStorage
import com.example.recipes.model.storage.presentation.components.ProfileContent
import kotlinx.coroutines.launch
import com.example.recipes.common.ext.idFromParameter


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun DetailScreen(
  recipeId: String,
  modifier: Modifier = Modifier,
  viewModel: DetailViewModel = hiltViewModel()
) {
  val recipe by viewModel.recipe

  val recipeId = recipeId.idFromParameter()

  LaunchedEffect(Unit) { viewModel.initialize(recipeId) }

  val scaffoldState = rememberScaffoldState()
  val coroutineScope = rememberCoroutineScope()
  val galleryLauncher =  rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
    imageUri?.let {
      viewModel.addImageToStorage(imageUri)
    }
  }


  Scaffold(
    content = { padding ->
      Column() {
        Text(text = recipeId, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle2)
        Text(text = recipe.Name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle2)
        Text(text = recipe.Description, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle2)
        Text(text = recipe.url, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle2)
        AsyncImage(
          model  = recipe.url,
          contentDescription = "description"
        )

      }
    },
    scaffoldState = scaffoldState
  )
  ProfileContent(
    openGallery = {
      galleryLauncher.launch(Constants.ALL_IMAGES)
    }
  )
  AddImageToStorage(
    addImageToDatabase = { downloadUrl ->
      viewModel.addImageToDatabase(downloadUrl, recipeId )
    }
  )

  fun showSnackBar() = coroutineScope.launch {
    val result = scaffoldState.snackbarHostState.showSnackbar(
      message = Constants.IMAGE_SUCCESSFULLY_ADDED_MESSAGE,
      actionLabel = Constants.DISPLAY_IT_MESSAGE
    )
    if (result == SnackbarResult.ActionPerformed) {
      viewModel.getImageFromDatabase()
    }
  }

  AddImageToDatabase(
    showSnackBar = { isImageAddedToDatabase ->
      if (isImageAddedToDatabase) {
        showSnackBar()
      }
    }
  )

}
