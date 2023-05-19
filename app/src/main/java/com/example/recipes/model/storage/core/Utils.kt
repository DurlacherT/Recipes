package com.example.recipes.core

import android.util.Log
import com.example.recipes.core.Constants.TAG

class Utils {
    companion object {
        fun print(e: Exception?) = e?.apply {
            Log.e(TAG, stackTraceToString())
        }
    }
}