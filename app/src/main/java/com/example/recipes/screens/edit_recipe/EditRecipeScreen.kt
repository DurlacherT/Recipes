package com.example.recipes.screens.edit_recipe

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.R.drawable as AppIcon
import com.example.recipes.R.string as AppText
import com.example.recipes.common.composable.*
import com.example.recipes.common.ext.card
import com.example.recipes.common.ext.fieldModifier
import com.example.recipes.common.ext.spacer
import com.example.recipes.common.ext.toolbarActions
import com.example.recipes.model.Priority
import com.example.recipes.model.Recipe
import com.example.recipes.theme.DarkBlue
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

@Composable
@ExperimentalMaterialApi
fun EditRecipeScreen(
  popUpScreen: () -> Unit,
  recipeId: String,
  modifier: Modifier = Modifier,
  viewModel: EditRecipeViewModel = hiltViewModel()
) {
  val recipe by viewModel.recipe

  LaunchedEffect(Unit) { viewModel.initialize(recipeId) }

  Column(
    modifier = modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    ActionToolbar(
      title = AppText.edit_recipe,
      modifier = Modifier.toolbarActions(),
      endActionIcon = AppIcon.ic_check,
      endAction = { viewModel.onDoneClick(popUpScreen) }
    )

    Spacer(modifier = Modifier.spacer())

    val fieldModifier = Modifier.fieldModifier()
    BasicField(AppText.name, recipe.recipe, viewModel::onTitleChange, fieldModifier)
    BasicField(AppText.description, recipe.description, viewModel::onDescriptionChange, fieldModifier)
    BasicField(AppText.ingredients, recipe.url, viewModel::onUrlChange, fieldModifier)

    Spacer(modifier = Modifier.spacer())
    //CardEditors(task, viewModel::onDateChange, viewModel::onTimeChange)
    Icon(
      Icons.Outlined.Favorite,
      tint = DarkBlue,
      contentDescription = "Favorite"
    )
    Spacer(modifier = Modifier.spacer())
  }
}

@ExperimentalMaterialApi
@Composable
private fun CardEditors(
  task: Recipe,
  onDateChange: (Long) -> Unit,
  onTimeChange: (Int, Int) -> Unit
) {
  val activity = LocalContext.current as AppCompatActivity

  //RegularCardEditor(AppText.date, AppIcon.ic_calendar, task.Ingredients, Modifier.card()) {
  //  showDatePicker(activity, onDateChange)
  // }

  RegularCardEditor(AppText.time, AppIcon.ic_clock, task.dueTime, Modifier.card()) {
    showTimePicker(activity, onTimeChange)
  }
}

@Composable
@ExperimentalMaterialApi
private fun CardSelectors(
  task: Recipe,
  onPriorityChange: (String) -> Unit,
  onFlagToggle: (String) -> Unit
) {
  val prioritySelection = Priority.getByName(task.author).name
  CardSelector(AppText.priority, Priority.getOptions(), prioritySelection, Modifier.card()) {
      newValue ->
    onPriorityChange(newValue)
  }

  val flagSelection = EditFlagOption.getByCheckedState(task.flag).name
  CardSelector(AppText.flag, EditFlagOption.getOptions(), flagSelection, Modifier.card()) { newValue
    ->
    onFlagToggle(newValue)
  }
}

private fun showDatePicker(activity: AppCompatActivity?, onDateChange: (Long) -> Unit) {
  val picker = MaterialDatePicker.Builder.datePicker().build()

  activity?.let {
    picker.show(it.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener { timeInMillis -> onDateChange(timeInMillis) }
  }
}

private fun showTimePicker(activity: AppCompatActivity?, onTimeChange: (Int, Int) -> Unit) {
  val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

  activity?.let {
    picker.show(it.supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener { onTimeChange(picker.hour, picker.minute) }
  }
}