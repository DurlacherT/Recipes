package com.example.recipes.screens.detailrecipes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.recipes.R
import com.example.recipes.common.composable.*
import com.example.recipes.common.ext.idFromParameter
import com.example.recipes.common.ext.smallSpacer
import com.example.recipes.common.ext.spacer
import com.example.recipes.common.ext.toolbarActions
import com.example.recipes.core.Constants
import com.example.recipes.model.storage.presentation.components.AddImageToDatabase
import com.example.recipes.model.storage.presentation.components.AddImageToStorage
import com.example.recipes.model.storage.presentation.components.ProfileContent
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun DetailScreen(
  recipeId: String,
  modifier: Modifier = Modifier,
  viewModel: DetailViewModel = hiltViewModel()
) {
  val recipe by viewModel.recipe

  val recipeId = recipeId.idFromParameter()

  LaunchedEffect(Unit) { viewModel.initialize(recipeId) }

  val scaffoldState = rememberScaffoldState()
  val coroutineScope = rememberCoroutineScope()
  val galleryLauncher =
    rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
      imageUri?.let { viewModel.addImageToStorage(imageUri) }
    }


      Column(        modifier = Modifier.padding(10.dp)
        .verticalScroll(rememberScrollState())
      ) {
        ActionToolbar(
          title = R.string.recipes,
          modifier = Modifier.toolbarActions(),
          endActionIcon = R.drawable.ic_settings,
          endAction = {}
        )

        Spacer(modifier = Modifier.smallSpacer())

        Text(
          text = recipe.Name,
          fontWeight = FontWeight.Bold,
          style = MaterialTheme.typography.subtitle2,
          fontSize = 40.sp
        )

        Spacer(modifier = Modifier.smallSpacer())

        Text(text = recipe.Description, style = MaterialTheme.typography.subtitle2,
          fontSize = 25.sp)

        Spacer(modifier = Modifier.smallSpacer())

        Column {
          for (i in recipe.Ingredients) {
            Text(text = "\u2022 $i", fontSize = 25.sp)
          }
          Spacer(modifier = Modifier.smallSpacer())
          for (i in recipe.Method) {
            Text(text = "\u2022 $i", fontSize = 25.sp)
          }
        }
        // Share data with other apps
        val sendIntent: Intent =
          Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, recipe.Name + "\n" + recipe.Description)
            type = "text/plain"
          }
        val shareIntent = Intent.createChooser(sendIntent, null)
        val context = LocalContext.current
        Column(
          modifier = Modifier.fillMaxWidth().fillMaxHeight(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {

          if (recipe.url.isNotEmpty()) {
            AsyncImage(model = recipe.url, contentDescription = "description")
          }
        }

        Column(
          modifier = Modifier.fillMaxWidth().fillMaxHeight(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          AddImageToStorage(
            addImageToDatabase = { downloadUrl ->
              viewModel.addImageToDatabase(downloadUrl, recipeId)
            }
          )
        }
        Spacer(modifier = Modifier.spacer())
        SmileyRatingBarSample()
        Spacer(modifier = Modifier.spacer())

        Column(
        ) {
          Button(onClick = { context.startActivity(shareIntent) }) { Text("Share") }

          ProfileContent(openGallery = { galleryLauncher.launch(Constants.ALL_IMAGES) })

  fun showSnackBar() =
    coroutineScope.launch {
      val result =
        scaffoldState.snackbarHostState.showSnackbar(
          message = Constants.IMAGE_SUCCESSFULLY_ADDED_MESSAGE,
          actionLabel = Constants.DISPLAY_IT_MESSAGE
        )
      if (result == SnackbarResult.ActionPerformed) {
        viewModel.getImageFromDatabase()
      }
    }

          AddImageToDatabase(
              showSnackBar = { isImageAddedToDatabase ->
                if (isImageAddedToDatabase) {
                  showSnackBar()
                }
              }
            )
        }
      }
}

data class SmileyData(
  val url: String,
  val label: String,
)

@Composable
fun Smiley(
  smileyData: SmileyData,
  isSelected: Boolean,
  index: Int,
  count: Int,
  modifier: Modifier = Modifier,
  animationDurationInMillis: Int = 300,
  onClick: () -> Unit,
) {
  val padding: Dp by
  animateDpAsState(
    targetValue =
    if (isSelected) {
      0.dp
    } else {
      4.dp
    },
    animationSpec =
    tween(
      durationMillis = animationDurationInMillis,
      easing = LinearEasing,
    ),
  )
  val size: Dp by
  animateDpAsState(
    targetValue =
    if (isSelected) {
      52.dp
    } else {
      44.dp
    },
    animationSpec =
    tween(
      durationMillis = animationDurationInMillis,
      easing = LinearEasing,
    ),
  )
  val saturation: Float by
  animateFloatAsState(
    targetValue =
    if (isSelected) {
      1F
    } else {
      0F
    },
    animationSpec =
    tween(
      durationMillis = animationDurationInMillis,
      easing = LinearEasing,
    ),
  )
  val matrix = ColorMatrix().apply { setToSaturation(saturation) }

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier.fillMaxWidth(),
  ) {
    AsyncImage(
      model =
      ImageRequest.Builder(LocalContext.current)
        .data(smileyData.url)
        .crossfade(true)
        .build(),
      contentDescription = "",
      contentScale = ContentScale.Crop,
      modifier =
      Modifier.padding(
        top = padding,
      )
        .size(size)
        .clip(CircleShape)
        .clickable { onClick() },
      colorFilter = ColorFilter.colorMatrix(matrix)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = smileyData.label,
      color =
      if (isSelected) {
        if (index < (count / 2)) {
          Color.Red
        } else if (index > (count / 2)) {
          Color(0xFF275A27)
        } else {
          Color.Black
        }
      } else {
        Color.DarkGray
      },
      fontWeight =
      if (isSelected) {
        FontWeight.Bold
      } else {
        FontWeight.Normal
      },
    )
  }
}

@Composable
fun SmileyRatingBar(
  data: List<SmileyData>,
  rating: Int,
  setRating: (rating: Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier.padding(8.dp).fillMaxWidth(),
  ) {
    Divider(
      modifier =
      Modifier.fillMaxWidth()
        .padding(
          top = 24.dp,
          start = 44.dp,
          end = 44.dp,
        ),
      thickness = 4.dp,
    )
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.Top,
      modifier = modifier.height(80.dp).fillMaxWidth(),
    ) {
      data.mapIndexed { index, smileyData ->
        Smiley(
          smileyData = smileyData,
          isSelected = index == rating,
          index = index,
          count = data.size,
          modifier = Modifier.weight(1F),
          onClick = { setRating(index) },
        )
      }
    }
  }
}

@Composable
fun SmileyRatingBarSample() {
  val data: List<SmileyData> =
    listOf(
      SmileyData("https://cdn-icons-png.flaticon.com/512/742/742905.png", "Terrible"),
      SmileyData("https://cdn-icons-png.flaticon.com/512/742/742761.png", "Bad"),
      SmileyData("https://cdn-icons-png.flaticon.com/512/742/742774.png", "Okay"),
      SmileyData("https://cdn-icons-png.flaticon.com/512/742/742940.png", "Good"),
      SmileyData("https://cdn-icons-png.flaticon.com/512/742/742869.png", "Awesome"),
    )

  val (rating, setRating) = remember { mutableStateOf(data.size / 2) }
  SmileyRatingBar(
    data = data,
    rating = rating,
    setRating = setRating,
  )
}
