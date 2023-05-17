package com.example.recipes.model.service

import com.example.recipes.model.Recipe
import kotlinx.coroutines.flow.Flow

interface StorageService {
  val tasks: Flow<List<Recipe>>

  suspend fun getRecipe(recipeId: String): Recipe?

  suspend fun save(recipe: Recipe): String
  suspend fun update(recipe: Recipe)
  suspend fun delete(recipeId: String)

  val recipe: Flow<List<Recipe>>

}
