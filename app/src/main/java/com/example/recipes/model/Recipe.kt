package com.example.recipes.model

import com.google.firebase.firestore.DocumentId

data class Recipe(
  @DocumentId val id: String = "",
  val recipe: String = "",
  val author: String = "",
  val description: String = "",
  val ingredients: List<String> = listOf(),
  val method: List<String> = listOf(),
  val name: String = "",
  val url: String = "",
  var flag: Boolean = false,
  val completed: Boolean = false,

  val title: String = "",
  val priority: String = "",
  val dueDate: String = "",
  val dueTime: String = ""
)
