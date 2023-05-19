package com.example.recipes.screens.detailrecipes

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.recipes.EDIT_RECIPE_SCREEN
import com.example.recipes.OVERVIEW_SCREEN
import com.example.recipes.RECIPE_DEFAULT_ID
import com.example.recipes.SETTINGS_SCREEN
import com.example.recipes.RECIPE_ID
import com.example.recipes.common.ext.idFromParameter
import com.example.recipes.domain.model.Response
import com.example.recipes.domain.repository.ProfileImageRepository
import com.example.recipes.model.Recipe
import com.example.recipes.model.service.ConfigurationService
import com.example.recipes.model.service.LogService
import com.example.recipes.model.service.StorageService
import com.example.recipes.screens.RecipesViewModel
import com.example.recipes.screens.myrecipes.RecipeActionOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  logService: LogService,
  private val storageService: StorageService,
  private val configurationService: ConfigurationService,
  private val repo: ProfileImageRepository

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

  var addImageToStorageResponse by mutableStateOf<Response<Uri>>(Response.Success(null))
    private set
  var addImageToDatabaseResponse by mutableStateOf<Response<Boolean>>(Response.Success(null))
    private set
  var getImageFromDatabaseResponse by mutableStateOf<Response<String>>(Response.Success(null))
    private set

  fun addImageToStorage(imageUri: Uri) = viewModelScope.launch {
    addImageToStorageResponse = Response.Loading
    addImageToStorageResponse = repo.addImageToFirebaseStorage(imageUri)
  }

  fun addImageToDatabase(downloadUrl: Uri) = viewModelScope.launch {
    addImageToDatabaseResponse = Response.Loading
    addImageToDatabaseResponse = repo.addImageUrlToFirestore(downloadUrl)
  }

  fun getImageFromDatabase() = viewModelScope.launch {
    getImageFromDatabaseResponse = Response.Loading
    getImageFromDatabaseResponse = repo.getImageUrlFromFirestore()
  }

}



