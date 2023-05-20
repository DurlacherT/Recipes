package com.example.recipes.model.storage.presentation.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.components.ProgressBar
import com.example.recipes.core.Utils.Companion.print
import com.example.recipes.domain.model.Response.*
import com.example.recipes.screens.detailrecipes.DetailViewModel

@Composable
fun AddImageToStorage(
    viewModel: DetailViewModel = hiltViewModel(),
    addImageToDatabase: (downloadUrl: Uri) -> Unit
) {
    when(val addImageToStorageResponse = viewModel.addImageToStorageResponse) {
        is Loading -> ProgressBar()
        is Success -> addImageToStorageResponse.data?.let { downloadUrl ->
            LaunchedEffect(downloadUrl) {
                addImageToDatabase(downloadUrl)
            }
        }
        is Failure -> print(addImageToStorageResponse.e)
    }
}