package com.example.recipes.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.screens.myrecipes.MyRecipesViewModel


@ExperimentalMaterialApi
@Composable
fun BottomBar(
modifier: Modifier,
onAddClick : () -> Unit,
onSettingsClick : () -> Unit,
onOverviewClick : () -> Unit,
onMyRecipesClick : () -> Unit


){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(60.dp)
    ) {
        TextButton (
            onClick = { onMyRecipesClick() },
            modifier = modifier.padding(8.dp),
            border = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Icon(Icons.Filled.Home, "Add")
        }
        TextButton (
            onClick = { onAddClick() },
            modifier = modifier.padding(8.dp),
            border = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Icon(Icons.Filled.Add, "Add")
        }
        TextButton (
            onClick = { onOverviewClick() },
            modifier = modifier.padding(8.dp),
            border = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Icon(Icons.Filled.ManageSearch, "Add", tint = Color.Black)
        }
        TextButton (
            onClick = { onSettingsClick() },
            modifier = modifier.padding(8.dp),
            border = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Icon(Icons.Filled.Settings, "Add", tint = Color.Black)
        }
    }
}
