package com.example.recipes.model.storage.presentation.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult.ActionPerformed
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import com.example.recipes.core.Constants.ALL_IMAGES
import com.example.recipes.core.Constants.DISPLAY_IT_MESSAGE
import com.example.recipes.core.Constants.IMAGE_SUCCESSFULLY_ADDED_MESSAGE
import com.example.recipes.screens.detailrecipes.DetailViewModel

@Composable
fun ProfileImageScreen(
    viewModel: DetailViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val galleryLauncher =  rememberLauncherForActivityResult(GetContent()) { imageUri ->
        imageUri?.let {
            viewModel.addImageToStorage(imageUri)
        }
    }
/*
    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                ProfileContent(
                    openGallery = {
                        galleryLauncher.launch(ALL_IMAGES)
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
            message = IMAGE_SUCCESSFULLY_ADDED_MESSAGE,
            actionLabel = DISPLAY_IT_MESSAGE
        )
        if (result == ActionPerformed) {
            viewModel.getImageFromDatabase()
        }
    }

    AddImageToDatabase(
        showSnackBar = { isImageAddedToDatabase ->
            if (isImageAddedToDatabase) {
                showSnackBar()
            }
        }
    )*/
}