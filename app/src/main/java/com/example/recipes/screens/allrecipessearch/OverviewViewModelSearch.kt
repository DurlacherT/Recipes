package com.example.recipes.screens.allrecipessearch

import androidx.compose.runtime.mutableStateOf
import com.example.recipes.DETAIL_SCREEN
import com.example.recipes.EDIT_RECIPE_SCREEN
import com.example.recipes.OVERVIEWSEARCH_SCREEN
import com.example.recipes.OVERVIEW_SCREEN
import com.example.recipes.RECIPES_SCREEN
import com.example.recipes.RECIPE_ID
import com.example.recipes.SETTINGS_SCREEN
import com.example.recipes.model.Recipe
import com.example.recipes.model.service.ConfigurationService
import com.example.recipes.model.service.LogService
import com.example.recipes.model.service.StorageService
import com.example.recipes.screens.RecipesViewModel
import com.example.recipes.screens.myrecipes.RecipeActionOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OverviewViewModelSearch
@Inject
constructor(
  logService: LogService,
  private val storageService: StorageService,
  private val configurationService: ConfigurationService
) : RecipesViewModel(logService) {
  val options = mutableStateOf<List<String>>(listOf())

  val recipes = storageService.recipe

  fun loadTaskOptions() {
    val hasEditOption = true
    options.value = RecipeActionOption.getOptions(hasEditOption)
  }

  fun onTaskCheckChange(task: Recipe) {
    launchCatching { storageService.update(task.copy(completed = !task.completed)) }
  }

  fun onRecipeClick(openScreen: (String) -> Unit, recipe: Recipe) =
    openScreen("$DETAIL_SCREEN?$RECIPE_ID={${recipe.id}}")

  fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_RECIPE_SCREEN)

  fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

  fun onOverviewSearchClick(openScreen: (String) -> Unit) = openScreen(OVERVIEWSEARCH_SCREEN)

  fun onOverviewClick(openScreen: (String) -> Unit) = openScreen(OVERVIEW_SCREEN)

  fun onMyRecipesClick(openScreen: (String) -> Unit) = openScreen(RECIPES_SCREEN)

  fun onFlagTaskClick(task: Recipe) {
    launchCatching {
      task.flag = !task.flag
      storageService.updateUserCollection(task.copy())
    }
  }
}
