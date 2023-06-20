package com.example.recipes

import android.Manifest
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipes.common.composable.PermissionDialog
import com.example.recipes.common.composable.RationaleDialog
import com.example.recipes.common.snackbar.SnackbarManager
import com.example.recipes.screens.detailrecipes.DetailScreen
import com.example.recipes.screens.edit_recipe.EditRecipeScreen
import com.example.recipes.screens.login.LoginScreen
import com.example.recipes.screens.allrecipes.OverviewScreen
import com.example.recipes.screens.allrecipes.OverviewScreenSearch
import com.example.recipes.screens.settings.SettingsScreen
import com.example.recipes.screens.sign_up.SignUpScreen
import com.example.recipes.screens.splash.SplashScreen
import com.example.recipes.screens.myrecipes.RecipeScreen
import com.example.recipes.screens.welcomescreen.WelcomeScreen
import com.example.recipes.theme.RecipesTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterialApi
fun RecipesApp() {
  RecipesTheme {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      RequestNotificationPermissionDialog()
    }

    Surface(color = MaterialTheme.colors.background) {
      val appState = rememberAppState()

      Scaffold(
        snackbarHost = {
          SnackbarHost(
            hostState = it,
            modifier = Modifier.padding(8.dp),
            snackbar = { snackbarData ->
              Snackbar(snackbarData, contentColor = MaterialTheme.colors.background)
            }
          )
        },
        scaffoldState = appState.scaffoldState
      ) { innerPaddingModifier ->
        NavHost(
          navController = appState.navController,
          startDestination = SPLASH_SCREEN,
          modifier = Modifier.padding(innerPaddingModifier)
        ) {
          recipesGraph(appState)
        }
      }
    }
  }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {
  val permissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

  if (!permissionState.status.isGranted) {
    if (permissionState.status.shouldShowRationale) RationaleDialog()
    else PermissionDialog { permissionState.launchPermissionRequest() }
  }
}

@Composable
fun rememberAppState(
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  navController: NavHostController = rememberNavController(),
  snackbarManager: SnackbarManager = SnackbarManager,
  resources: Resources = resources(),
  coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
  remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
    RecipesAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
  }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
  LocalConfiguration.current
  return LocalContext.current.resources
}

@ExperimentalMaterialApi
fun NavGraphBuilder.recipesGraph(appState: RecipesAppState) {
  composable(SPLASH_SCREEN) {
    SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
  }

  composable(SETTINGS_SCREEN) {
    SettingsScreen(
      restartApp = { route -> appState.clearAndNavigate(route) },
      openScreen = { route -> appState.navigate(route) }
    )
  }

  composable(LOGIN_SCREEN) {
    LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
  }

  composable(SIGN_UP_SCREEN) {
    SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
  }

  composable(RECIPES_SCREEN) { RecipeScreen(openScreen = { route -> appState.navigate(route) }) }

  composable(
    route = "$DETAIL_SCREEN$RECIPE_ID_ARG",
    arguments = listOf(navArgument(RECIPE_ID)  {type = NavType.StringType })
  ) {
    DetailScreen(
      recipeId = it.arguments?.getString(RECIPE_ID) ?: RECIPE_DEFAULT_ID
    )
  }


  composable(OVERVIEW_SCREEN) {
    OverviewScreen(
      openScreen = { route -> appState.navigate(route) }
    )
  }

  composable(OVERVIEWSEARCH_SCREEN) {
    OverviewScreenSearch(
      openScreen = { route -> appState.navigate(route) }
    )
  }

  composable(WELCOME_SCREEN) {
    WelcomeScreen(
      openScreen = { route -> appState.navigate(route) }

    )
  }


  composable(
    route = "$EDIT_RECIPE_SCREEN$RECIPE_ID_ARG",
    arguments = listOf(navArgument(RECIPE_ID) { defaultValue = RECIPE_DEFAULT_ID })
  ) {
    EditRecipeScreen(
      openScreen = { route -> appState.navigate(route) },
              popUpScreen = { appState.popUp() },
      recipeId = it.arguments?.getString(RECIPE_ID) ?: RECIPE_DEFAULT_ID
    )
  }
}
