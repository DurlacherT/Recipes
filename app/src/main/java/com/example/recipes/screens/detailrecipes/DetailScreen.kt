package com.example.recipes.screens.allrecipes

import android.annotation.SuppressLint
import android.media.Image
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.recipes.common.composable.*
import com.example.recipes.core.Constants
import com.example.recipes.presentation.components.AddImageToDatabase
import com.example.recipes.presentation.components.AddImageToStorage
import com.example.recipes.presentation.components.ProfileContent
import com.example.recipes.screens.detailrecipes.DetailViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun DetailScreen(
  recipeId: String,
  modifier: Modifier = Modifier,
  viewModel: DetailViewModel = hiltViewModel()
) {
  val recipe by viewModel.recipe

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
          model  = "https://firebasestorage.googleapis.com/v0/b/recipe-app-6b055.appspot.com/o/images%2FA23Hr7F2.jpg?alt=media&token=63fbff0d-fd2c-4a00-9133-3c6dd5c5cfb5",
          contentDescription = "description"
        )
      }
      Box(
        modifier = Modifier.fillMaxSize().padding(padding)
      ) {
        ProfileContent(
          openGallery = {
            galleryLauncher.launch(Constants.ALL_IMAGES)
          }
        )
      }
    },
    scaffoldState = scaffoldState
  )

  AddImageToStorage(
    addImageToDatabase = { downloadUrl ->
      viewModel.addImageToDatabase(downloadUrl)
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
