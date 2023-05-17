package com.example.recipes.model.service

interface ConfigurationService {
  suspend fun fetchConfiguration(): Boolean
  val isShowRecipeEditButtonConfig: Boolean
}
