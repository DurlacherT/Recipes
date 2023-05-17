package com.example.recipes.screens.myrecipes

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipes.R.drawable as AppIcon
import com.example.recipes.common.composable.DropdownContextMenu
import com.example.recipes.common.ext.contextMenu
import com.example.recipes.common.ext.hasDueTime
import com.example.recipes.model.Recipe
import com.example.recipes.theme.DarkBlue
import java.lang.StringBuilder

@Composable
@ExperimentalMaterialApi
fun TaskItem(
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
      Checkbox(
        checked = recipe.completed,
        onCheckedChange = { onCheckChange() },
        modifier = Modifier.padding(8.dp, 0.dp)
      )

      Column(modifier = Modifier.weight(1f)) {
        Text(text = recipe.recipe, style = MaterialTheme.typography.subtitle2)
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
          Text(text = getDueDateAndTime(recipe), fontSize = 12.sp)
        }
      }

      if (recipe.flag) {
        Icon(
          painter = painterResource(AppIcon.ic_flag),
          tint = DarkBlue,
          contentDescription = "Flag"
        )
      }

      DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
    }
  }
}

private fun getDueDateAndTime(task: Recipe): String {
  val stringBuilder = StringBuilder("")

 // if (task.hasDueDate()) {
 //   stringBuilder.append(task.Ingredients)
//    stringBuilder.append(" ")
 // }

  if (task.hasDueTime()) {
    stringBuilder.append("at ")
    stringBuilder.append(task.method)
  }

  return stringBuilder.toString()
}
