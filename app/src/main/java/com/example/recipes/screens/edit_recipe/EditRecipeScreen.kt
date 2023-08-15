package com.example.recipes.screens.edit_recipe

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.R.drawable as AppIcon
import com.example.recipes.R.string as AppText
import com.example.recipes.common.composable.*
import com.example.recipes.common.ext.fieldModifier
import com.example.recipes.common.ext.spacer
import com.example.recipes.common.ext.toolbarActions

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun EditRecipeScreen(
  openScreen: (String) -> Unit,
  popUpScreen: () -> Unit,
  recipeId: String,
  modifier: Modifier = Modifier,
  viewModel: EditRecipeViewModel = hiltViewModel()
) {
  val recipe by viewModel.recipe

  LaunchedEffect(Unit) { viewModel.initialize(recipeId) }
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
      modifier =
      modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      ActionToolbar(
        title = AppText.edit_recipe,
        modifier = Modifier.toolbarActions(),
        endActionIcon = AppIcon.ic_check,
        endAction = { viewModel.onDoneClick(popUpScreen) }
      )

      Spacer(modifier = Modifier.spacer())

      val fieldModifier = Modifier.fieldModifier()
      BasicField(AppText.name, recipe.name, viewModel::onTitleChange, fieldModifier)
      BasicField(
        AppText.description,
        recipe.description,
        viewModel::onDescriptionChange,
        fieldModifier
      )
      BasicField(AppText.ingredients, recipe.title, viewModel::onUrlChange, fieldModifier)
      BasicField(AppText.category, recipe.category, viewModel::onCategoryChange, fieldModifier)
    }
  }
/*  val categories = listOf("Frühstück", "Mittagessen", "Abendessen", "Dessert", "Snacks")
  var selectedCategory by remember { mutableStateOf(recipe.category) }

  DropdownMenu(
    expanded = true,
    onDismissRequest = { *//* Schließen des Menüs *//* }
  ) {
    categories.forEach { category ->
      DropdownMenuItem(onClick = {
        selectedCategory = category
        viewModel.onCategoryChange(category) // Hier die ausgewählte Kategorie speichern
      }) {
        Text(text = category)
      }
    }
  }

  Text(text = "Ausgewählte Kategorie: $selectedCategory")*/
}
