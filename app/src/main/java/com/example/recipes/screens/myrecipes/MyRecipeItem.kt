package com.example.recipes.screens.myrecipes

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipes.common.ext.smallSpacer
import com.example.recipes.model.Recipe
import com.example.recipes.theme.DarkBlue

@Composable
@ExperimentalMaterialApi
fun TaskItem(
  recipe: Recipe,
  options: List<String>,
  onCheckChange: () -> Unit,
  onActionClick: (String) -> Unit
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


      Column(modifier = Modifier.weight(1f)) {

        Text(
          text = recipe.Name,
          fontWeight = FontWeight.Bold,
          style = MaterialTheme.typography.subtitle2,
          fontSize = 20.sp
        )
        //Text(text = recipe.Ingredients, style = MaterialTheme.typography.subtitle2)

        Row(verticalAlignment = Alignment.CenterVertically) {
          AsyncImage(
            model  = recipe.url,
            contentDescription = "description",
            modifier = Modifier.size(110.dp).padding(10.dp),
            contentScale = Crop,

          )
          Text(text = recipe.Description, style = MaterialTheme.typography.body1, fontSize = 20.sp)
          //Text(text = recipe.url, style = MaterialTheme.typography.subtitle2)

          if (recipe.flag) {
            Icon(
              Icons.Filled.Favorite,
              tint = DarkBlue,
              contentDescription = "Favorite",
              modifier = Modifier.size(110.dp).padding(10.dp)
              )
          } else {
            Icon(
              Icons.Outlined.Favorite,
              tint = DarkBlue,
              contentDescription = "Favorite",
              modifier = Modifier.size(110.dp).padding(10.dp)

              )
          }
        }


      }
      //DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
    }
  }
}

