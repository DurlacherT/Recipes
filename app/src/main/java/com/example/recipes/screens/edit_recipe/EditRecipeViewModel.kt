package com.example.recipes.screens.edit_recipe

import androidx.compose.runtime.mutableStateOf
import com.example.recipes.EDIT_RECIPE_SCREEN
import com.example.recipes.OVERVIEWSEARCH_SCREEN
import com.example.recipes.OVERVIEW_SCREEN
import com.example.recipes.RECIPES_SCREEN
import com.example.recipes.RECIPE_DEFAULT_ID
import com.example.recipes.SETTINGS_SCREEN
import com.example.recipes.common.ext.idFromParameter
import com.example.recipes.model.Recipe
import com.example.recipes.model.service.LogService
import com.example.recipes.model.service.StorageService
import com.example.recipes.screens.RecipesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditRecipeViewModel @Inject constructor(
  logService: LogService,
  private val storageService: StorageService,
) : RecipesViewModel(logService) {
  val recipe = mutableStateOf(Recipe())

  fun initialize(recipeId: String) {
    launchCatching {
      if (recipeId != RECIPE_DEFAULT_ID) {
        recipe.value = storageService.getRecipe(recipeId.idFromParameter()) ?: Recipe()
      }
    }
  }

  fun onTitleChange(newValue: String) {
    recipe.value = recipe.value.copy(recipe = newValue)
  }

  fun onDescriptionChange(newValue: String) {
    recipe.value = recipe.value.copy(description = newValue)
  }

  fun onUrlChange(newValue: String) {
    recipe.value = recipe.value.copy(url = newValue)
  }

  fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_RECIPE_SCREEN)

  fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

  fun onOverviewSearchClick(openScreen: (String) -> Unit) = openScreen(OVERVIEWSEARCH_SCREEN)

  fun onOverviewClick(openScreen: (String) -> Unit) = openScreen(OVERVIEW_SCREEN)

  fun onMyRecipesClick(openScreen: (String) -> Unit) = openScreen(RECIPES_SCREEN)


  /*fun onDateChange(newValue: Long) {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
    calendar.timeInMillis = newValue
    val newDueDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
    task.value = task.value.copy(Ingredients = newDueDate)
  }*/

  fun onTimeChange(hour: Int, minute: Int) {
    val newDueTime = "${hour.toClockPattern()}:${minute.toClockPattern()}"
    //task.value = task.value.copy(method = newDueTime)
  }

  fun onFlagToggle(newValue: String) {
    val newFlagOption = EditFlagOption.getBooleanValue(newValue)
    recipe.value = recipe.value.copy(flag = newFlagOption)
  }

  fun onPriorityChange(newValue: String) {
    recipe.value = recipe.value.copy(author = newValue)
  }

  fun onDoneClick(popUpScreen: () -> Unit) {
    launchCatching {
      val editedTask = recipe.value
      if (editedTask.id.isBlank()) {
        storageService.save(editedTask)
      } else {
        storageService.update(editedTask)
      }
      popUpScreen()
    }
  }

  private fun Int.toClockPattern(): String {
    return if (this < 10) "0$this" else "$this"
  }

  companion object {
    private const val UTC = "UTC"
    private const val DATE_FORMAT = "EEE, d MMM yyyy"
  }
}