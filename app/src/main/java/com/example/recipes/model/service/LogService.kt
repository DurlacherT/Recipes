package com.example.recipes.model.service

interface LogService {
  fun logNonFatalCrash(throwable: Throwable)
}
