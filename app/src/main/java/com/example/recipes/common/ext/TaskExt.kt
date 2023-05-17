package com.example.recipes.common.ext

import com.example.recipes.model.Recipe

fun Recipe?.hasDueDate(): Boolean {
  return true //this?.ingredients.orEmpty().isNotBlank()
}

fun Recipe?.hasDueTime(): Boolean {
  return true //this?.method.orEmpty().isNotBlank()
}
