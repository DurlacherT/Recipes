package com.example.recipes.model

import com.google.firebase.firestore.DocumentId

data class Recipe(
  @DocumentId val id: String = "",
  val recipe: String = "",
  val Author: String = "",
  val Description: String = "",
  val Ingredients: List<String> = listOf(),
  val Method: List<String> = listOf(),
  val Name: String = "",
  val url: String = "",
  var flag: Boolean = false,
  val completed: Boolean = false,
  val category: String ="",
  val time: Float = 0f,

  val title: String = "",
  val priority: String = "",
  val dueDate: String = "",
  val dueTime: String = ""
)
