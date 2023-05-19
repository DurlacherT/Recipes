package com.example.recipes.screens.allrecipes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.common.composable.*
import com.example.recipes.screens.detailrecipes.DetailViewModel


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


Column() {
  Text(text = recipeId, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle2)
  Text(text = recipe.Name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle2)
  Text(text = recipe.Description, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle2)
  Text(text = recipe.url, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle2)
}


}
