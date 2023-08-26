package com.example.recipes.screens.detailrecipes

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipes.R.drawable as AppIcon
import com.example.recipes.model.Recipe
import com.example.recipes.theme.DarkBlue

@Composable
@ExperimentalMaterialApi
fun DetailItem(
  recipe: Recipe,
  options: List<String>,
  onCheckChange: () -> Unit,
  onActionClick: (String) -> Unit
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

      Column(modifier = Modifier.weight(1f)) {
        Text(text = recipe.Name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle2)
        //Text(text = recipe.Ingredients, style = MaterialTheme.typography.subtitle2)
        Text(text = recipe.Description, style = MaterialTheme.typography.body1)
        //Text(text = recipe.url, style = MaterialTheme.typography.subtitle2)
      }

      if (recipe.flag) {
        Icon(
          painter = painterResource(AppIcon.ic_flag),
          tint = DarkBlue,
          contentDescription = "Flag"
        )
      }

    }
  }
}

