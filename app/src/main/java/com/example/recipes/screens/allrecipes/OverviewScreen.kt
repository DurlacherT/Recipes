package com.example.recipes.screens.allrecipes

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipes.EDIT_RECIPE_SCREEN
import com.example.recipes.OVERVIEW_SCREEN
import com.example.recipes.R
import com.example.recipes.SETTINGS_SCREEN
import com.example.recipes.R.drawable as AppIcon
import com.example.recipes.R.string as AppText
import com.example.recipes.common.composable.*
import com.example.recipes.common.ext.smallSpacer
import com.example.recipes.common.ext.toolbarActions
import com.example.recipes.model.Recipe


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun OverviewScreen(
  openScreen: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: OverviewViewModel = hiltViewModel()
) {

  Scaffold(

            bottomBar = {
      BottomBar(modifier,
              onMyRecipesClick = { viewModel.onMyRecipesClick(openScreen) },
              onAddClick = { viewModel.onAddClick(openScreen) },
        onSearchClick = { viewModel.onOverviewSearchClick(openScreen) },
        onSettingsClick = { viewModel.onSettingsClick(openScreen) },
              onOverviewClick = { viewModel.onOverviewClick(openScreen)}
      )
    }
  ) {
    val recipes = viewModel.recipes.collectAsStateWithLifecycle(emptyList())      // Recipes Liste von Objekten wird von Viewmodel übergeben
    val options by viewModel.options
    var filteredRecipes: List<Recipe>


    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
      ActionToolbar(
        title = AppText.recipes,
        modifier = Modifier.toolbarActions(),
        endActionIcon = AppIcon.ic_settings,
        endAction = { viewModel.onSettingsClick(openScreen) }
      )

      Spacer(modifier = Modifier.smallSpacer())


      LazyColumn {

        items(recipes.value, key = { it.id }) { recipe ->   //Darstellung einzelner Rezepte
          OverviewItem(
            recipe = recipe,
            options = options,
            onCheckChange = { viewModel.onTaskCheckChange(recipe) },
            onActionClick = {},
            onRecipeClick = { viewModel.onRecipeClick(openScreen, recipe) },
            onFlagTaskClick = { viewModel.onFlagTaskClick(recipe) }
          )

        }
      }
    }


  }

  LaunchedEffect(viewModel) { viewModel.loadTaskOptions() }
}


