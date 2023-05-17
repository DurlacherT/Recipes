package com.example.recipes.screens.allrecipes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipes.R.drawable as AppIcon
import com.example.recipes.R.string as AppText
import com.example.recipes.common.composable.*
import com.example.recipes.common.ext.smallSpacer
import com.example.recipes.common.ext.toolbarActions


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun OverviewScreen(
  openScreen: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: OverviewViewModel = hiltViewModel()
) {
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = { viewModel.onAddClick(openScreen) },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        modifier = modifier.padding(16.dp)
      ) {
        Icon(Icons.Filled.Add, "Add")
      }
    }
  ) {
    val recipes = viewModel.recipes.collectAsStateWithLifecycle(emptyList())
    val options by viewModel.options


    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
      ActionToolbar(
        title = AppText.recipes,
        modifier = Modifier.toolbarActions(),
        endActionIcon = AppIcon.ic_settings,
        endAction = { viewModel.onSettingsClick(openScreen) }
      )

      Spacer(modifier = Modifier.smallSpacer())

      LazyColumn {
        items(recipes.value, key = { it.id }) { recipes ->
          RecipeItem(
            recipe = recipes,
            options = options,
            onCheckChange = { viewModel.onTaskCheckChange(recipes) },
            onActionClick = { action -> viewModel.onTaskActionClick(openScreen, recipes, action) }
          )
        }
      }
    }


    Button(onClick = {
      viewModel.onOverviewClick(openScreen)
      //your onclick code here
    }) {
      Text(text = "Simple Button")
    }



  }

  //LaunchedEffect(viewModel) { viewModel.startOverview() }
}
