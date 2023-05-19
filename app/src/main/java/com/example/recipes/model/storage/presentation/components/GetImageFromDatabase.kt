package com.example.recipes.presentation.components

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.components.ProgressBar
import com.example.recipes.core.Utils.Companion.print
import com.example.recipes.domain.model.Response.*
import com.example.recipes.presentation.ProfileViewModel

@Composable
fun GetImageFromDatabase(
    viewModel: ProfileViewModel = hiltViewModel(),
    createProfileImageContent: @Composable (imageUrl: String) -> Unit
) {
    when(val getImageFromDatabaseResponse = viewModel.getImageFromDatabaseResponse) {
        is Loading -> ProgressBar()
        is Success -> getImageFromDatabaseResponse.data?.let { imageUrl ->
            createProfileImageContent(imageUrl)
        }
        is Failure -> print(getImageFromDatabaseResponse.e)
    }
}