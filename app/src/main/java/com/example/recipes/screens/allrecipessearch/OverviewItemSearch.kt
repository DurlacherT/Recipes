package com.example.recipes.screens.allrecipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipes.R.drawable as AppIcon
import com.example.recipes.common.composable.DropdownContextMenu
import com.example.recipes.common.ext.contextMenu
import com.example.recipes.common.ext.hasDueTime
import com.example.recipes.common.ext.smallSpacer
import com.example.recipes.model.Recipe
import com.example.recipes.theme.DarkBlue
import java.lang.StringBuilder

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


      Column(modifier = Modifier.weight(1f).clickable {
        onRecipeClick(recipe.id)
      }) {


        //Text(text = recipe.Ingredients, style = MaterialTheme.typography.subtitle2)

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
          Text(
            text = recipe.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 20.sp
          )
          /*   Text(
               text = recipe.description,
               style = MaterialTheme.typography.body1,
               fontSize = 20.sp
             )*/
          //Text(text = recipe.url, style = MaterialTheme.typography.subtitle2)


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
        Column {
for (i in recipe.ingredients) {
  Text(
    text = i,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp
  )
}



        }

      }


    }


      //DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)

    }

}

