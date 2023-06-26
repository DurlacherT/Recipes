package com.example.recipes.screens.allrecipessearch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipes.common.ext.smallSpacer
import com.example.recipes.model.Recipe
import com.example.recipes.theme.DarkBlue

@Composable
@ExperimentalMaterialApi
fun OverviewItemSearch(
  recipe: Recipe,
  options: List<String>,
  onCheckChange: () -> Unit,
  onRecipeClick: (String) -> Unit,
  onActionClick: (String) -> Unit,
  onFlagTaskClick: () -> Unit
) {
  Spacer(modifier = Modifier.smallSpacer())
  Card(
    backgroundColor = MaterialTheme.colors.background,
    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth(),
    ) {
      Column(modifier = Modifier.weight(1f).clickable { onRecipeClick(recipe.id) }) {
       Row(modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween) {
          Text(
            text = recipe.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 20.sp
          )
          Icon(
            if (recipe.flag) {
              Icons.Filled.Favorite
            } else {
              Icons.Outlined.FavoriteBorder
            },
            modifier = Modifier.clickable { onFlagTaskClick() },
            tint = DarkBlue,
            contentDescription = "Flag"
          )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column {
          Text(
            text = "Ingredients:",
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 15.sp
          )
          Spacer(modifier = Modifier.height(8.dp))
          for (ingredient in recipe.ingredients) {
            Row(modifier = Modifier.padding(start = 16.dp)) {
              Text(
                text = "•",
                style = TextStyle(fontSize = 16.sp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = ingredient,
                fontSize = 15.sp
              )
            }
          }
        }
        }
      }
    }
  }
