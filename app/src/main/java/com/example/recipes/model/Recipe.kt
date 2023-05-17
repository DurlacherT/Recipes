package com.example.recipes.model

import com.google.firebase.firestore.DocumentId

data class Recipe(
  @DocumentId val id: String = "",
  val recipe: String = "",
  val author: String = "",
  val Description: String = "",
  val Ingredients: List<String> = listOf(),
  val method: List<String> = listOf(),
  val Name: String = "",
  val url: String = "",
  val flag: Boolean = false,
  val completed: Boolean = false,

  val title: String = "",
  val priority: String = "",
  val dueDate: String = "",
  val dueTime: String = ""
)
