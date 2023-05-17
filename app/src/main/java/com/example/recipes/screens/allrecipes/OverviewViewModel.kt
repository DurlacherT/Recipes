package com.example.recipes.screens.allrecipes

import androidx.compose.runtime.mutableStateOf
import com.example.recipes.EDIT_RECIPE_SCREEN
import com.example.recipes.OVERVIEW_SCREEN
import com.example.recipes.SETTINGS_SCREEN
import com.example.recipes.RECIPE_ID
import com.example.recipes.model.Recipe
import com.example.recipes.model.service.ConfigurationService
import com.example.recipes.model.service.LogService
import com.example.recipes.model.service.StorageService
import com.example.recipes.screens.RecipesViewModel
import com.example.recipes.screens.myrecipes.RecipeActionOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
  logService: LogService,
  private val storageService: StorageService,
  private val configurationService: ConfigurationService
) : RecipesViewModel(logService) {
  val options = mutableStateOf<List<String>>(listOf())

  val recipes = storageService.recipe

  fun loadTaskOptions() {
    val hasEditOption = configurationService.isShowRecipeEditButtonConfig
    options.value = RecipeActionOption.getOptions(hasEditOption)
  }



  fun onTaskCheckChange(task: Recipe) {
    launchCatching { storageService.update(task.copy(completed = !task.completed)) }
  }

  fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_RECIPE_SCREEN)

  fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)


  fun onOverviewClick(openScreen: (String) -> Unit) = openScreen(OVERVIEW_SCREEN)


  fun onTaskActionClick(openScreen: (String) -> Unit, task: Recipe, action: String) {
    when (RecipeActionOption.getByTitle(action)) {
      RecipeActionOption.EditRecipe -> openScreen("$EDIT_RECIPE_SCREEN?$RECIPE_ID={${task.id}}")
      RecipeActionOption.ToggleFlag -> onFlagTaskClick(task)
      RecipeActionOption.DeleteRecipe -> onDeleteTaskClick(task)
    }
  }



  private fun onFlagTaskClick(task: Recipe) {
    launchCatching { storageService.update(task.copy(flag = !task.flag)) }
  }

  private fun onDeleteTaskClick(task: Recipe) {
    launchCatching { storageService.delete(task.id) }
  }
}



