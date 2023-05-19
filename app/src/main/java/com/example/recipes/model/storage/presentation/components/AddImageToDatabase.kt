package com.example.recipes.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.components.ProgressBar
import com.example.recipes.core.Utils.Companion.print
import com.example.recipes.domain.model.Response.*
import com.example.recipes.presentation.ProfileViewModel

@Composable
fun AddImageToDatabase(
    viewModel: ProfileViewModel = hiltViewModel(),
    showSnackBar: (isImageAddedToDatabase: Boolean) -> Unit
) {
    when(val addImageToDatabaseResponse = viewModel.addImageToDatabaseResponse) {
        is Loading -> ProgressBar()
        is Success -> addImageToDatabaseResponse.data?.let { isImageAddedToDatabase ->
            LaunchedEffect(isImageAddedToDatabase) {
                showSnackBar(isImageAddedToDatabase)
            }
        }
        is Failure -> print(addImageToDatabaseResponse.e)
    }
}