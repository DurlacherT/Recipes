package com.example.recipes.screens.myrecipes

enum class RecipeActionOption(val title: String) {
  EditRecipe("Edit recipe"),
  ToggleFlag("Toggle flag"),
  DeleteRecipe("Delete task");

  companion object {
    fun getByTitle(title: String): RecipeActionOption {
      values().forEach { action -> if (title == action.title) return action }

      return EditRecipe
    }

    fun getOptions(hasEditOption: Boolean): List<String> {
      val options = mutableListOf<String>()
      values().forEach { taskAction ->
        if (hasEditOption || taskAction != EditRecipe) {
          options.add(taskAction.title)
        }
      }
      return options
    }
  }
}
