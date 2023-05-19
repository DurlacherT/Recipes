package com.example.recipes.screens.detailrecipes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.recipes.EDIT_RECIPE_SCREEN
import com.example.recipes.OVERVIEW_SCREEN
import com.example.recipes.RECIPE_DEFAULT_ID
import com.example.recipes.SETTINGS_SCREEN
import com.example.recipes.RECIPE_ID
import com.example.recipes.common.ext.idFromParameter
import com.example.recipes.model.Recipe
import com.example.recipes.model.service.ConfigurationService
import com.example.recipes.model.service.LogService
import com.example.recipes.model.service.StorageService
import com.example.recipes.screens.RecipesViewModel
import com.example.recipes.screens.myrecipes.RecipeActionOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  logService: LogService,
  private val storageService: StorageService,
  private val configurationService: ConfigurationService
) : RecipesViewModel(logService) {



  val recipe = mutableStateOf(Recipe())

  fun initialize(recipeId: String) {
    launchCatching {
      if (recipeId != RECIPE_DEFAULT_ID) {
        recipe.value = storageService.getRecipeNEW(recipeId.idFromParameter()) ?: Recipe()
      }
    }
  }


  val recipeId: String = savedStateHandle.get<String>(RECIPE_ID_SAVED_STATE_KEY)!!



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

  companion object {
    private const val RECIPE_ID_SAVED_STATE_KEY = "recipeId"
  }



}



