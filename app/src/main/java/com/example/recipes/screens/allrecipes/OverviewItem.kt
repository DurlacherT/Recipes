package com.example.recipes.screens.allrecipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipes.OVERVIEW_SCREEN
import com.example.recipes.R.drawable as AppIcon
import com.example.recipes.common.composable.DropdownContextMenu
import com.example.recipes.common.ext.contextMenu
import com.example.recipes.model.Recipe
import com.example.recipes.screens.myrecipes.RecipeActionOption
import com.example.recipes.theme.DarkBlue

@Composable
@ExperimentalMaterialApi
fun OverviewItem(
  recipe: Recipe,
  options: List<String>,
  onRecipeClick: (String) -> Unit,
  onCheckChange: () -> Unit,
  onActionClick: (String) -> Unit,
  onFlagTaskClick: () -> Unit
) {
  Card(
    backgroundColor = MaterialTheme.colors.background,
    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth(),
    ) {
      /*Checkbox(
        checked = recipe.completed,
        onCheckedChange = { onCheckChange() },
        modifier = Modifier.padding(8.dp, 0.dp)
      )*/

      Column(modifier = Modifier.weight(1f).clickable { onRecipeClick(recipe.id)  }) {
        Text(text = recipe.id, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle2)

          Text(text = recipe.Name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle2)
          //Text(text = recipe.Ingredients, style = MaterialTheme.typography.subtitle2)
          Text(text = recipe.Description, style = MaterialTheme.typography.body1)
          //Text(text = recipe.url, style = MaterialTheme.typography.subtitle2)

      }

        Icon(
          if (recipe.flag) {Icons.Filled.Favorite} else {Icons.Outlined.FavoriteBorder},
          modifier = Modifier.clickable { onFlagTaskClick() },
          tint = DarkBlue,
          contentDescription = "Flag"
        )



      //DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)

    }
  }
}

