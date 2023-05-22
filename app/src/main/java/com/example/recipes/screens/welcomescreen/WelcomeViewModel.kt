package com.example.recipes.screens.welcomescreen

import com.example.recipes.EDIT_RECIPE_SCREEN
import com.example.recipes.OVERVIEW_SCREEN
import com.example.recipes.RECIPES_SCREEN
import com.example.recipes.SETTINGS_SCREEN
import com.example.recipes.model.service.AccountService
import com.example.recipes.model.service.LogService
import com.example.recipes.model.service.StorageService
import com.example.recipes.screens.RecipesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class WelcomeViewModel @Inject constructor(
  logService: LogService,
  private val accountService: AccountService,
  private val storageService: StorageService
) : RecipesViewModel(logService) {
  val uiState = accountService.currentUser.map { WelcomeUiState(it.isAnonymous) }


  fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_RECIPE_SCREEN)

  fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

  fun onOverviewClick(openScreen: (String) -> Unit) = openScreen(OVERVIEW_SCREEN)

  fun onMyRecipesClick(openScreen: (String) -> Unit) = openScreen(RECIPES_SCREEN)

}
