package com.example.recipes.screens.allrecipessearch

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.example.recipes.R
import com.example.recipes.R.drawable as AppIcon
import com.example.recipes.R.string as AppText
import com.example.recipes.common.composable.*
import com.example.recipes.common.ext.smallSpacer
import com.example.recipes.common.ext.toolbarActions
import com.example.recipes.model.Recipe

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun OverviewScreenSearch(
  openScreen: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: OverviewViewModelSearch = hiltViewModel()
) {
  val textState = remember { mutableStateOf(TextFieldValue("")) }
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
    val recipes = viewModel.recipes.collectAsStateWithLifecycle(emptyList())
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
      SearchView(textState)
      LazyColumn {
        val searchedText = textState.value.text                                // Filter/Search function
        filteredRecipes =
          if (searchedText.isEmpty()) {
            recipes.value
          } else {
            val resultList = mutableListOf<Recipe>()
            for (recipe in recipes.value) {
              if (
                recipe.name.lowercase().contains(searchedText.lowercase()) or  // Search in recipe name and recipe ingredients
                recipe.ingredients
                  .joinToString()
                  .lowercase()
                  .contains(searchedText.lowercase())
              ) {
                resultList.add(recipe)
              }
            }
            resultList
          }
        if (!filteredRecipes.isNullOrEmpty()) {
          items(filteredRecipes, key = { it.id }) { recipe ->
            OverviewItemSearch(
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
  }
  LaunchedEffect(viewModel) { viewModel.loadTaskOptions() }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
  TextField(
    value = state.value,
    onValueChange = { value -> state.value = value },
    modifier = Modifier.fillMaxWidth(),
    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
    leadingIcon = {
      Icon(
        Icons.Default.Search,
        contentDescription = "",
        modifier = Modifier.padding(15.dp).size(24.dp)
      )
    },
    trailingIcon = {
      if (state.value != TextFieldValue("")) {
        IconButton(onClick = { state.value = TextFieldValue("") }) {
          Icon(
            Icons.Default.Close,
            contentDescription = "",
            modifier = Modifier.padding(15.dp).size(24.dp)
          )
        }
      }
    },
    singleLine = true,
    shape = RectangleShape,
    colors =
    TextFieldDefaults.textFieldColors(
      textColor = Color.White,
      cursorColor = Color.White,
      leadingIconColor = Color.White,
      trailingIconColor = Color.White,
      backgroundColor = colorResource(id = R.color.bright_orange),
      focusedIndicatorColor = Color.Transparent,
      unfocusedIndicatorColor = Color.Transparent,
      disabledIndicatorColor = Color.Transparent
    )
  )
}
