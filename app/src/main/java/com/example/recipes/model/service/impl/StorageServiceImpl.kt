package com.example.recipes.model.service.impl

import com.example.recipes.model.Recipe
import com.example.recipes.model.service.AccountService
import com.example.recipes.model.service.StorageService
import com.example.recipes.model.service.trace
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
  StorageService {

  @OptIn(ExperimentalCoroutinesApi::class)
  override val tasks: Flow<List<Recipe>>
    get() =
      auth.currentUser.flatMapLatest { user ->
        currentCollection(user.id).snapshots().map { snapshot -> snapshot.toObjects() }
      }

  override suspend fun getRecipe(recipeId: String): Recipe? =
    currentCollection(auth.currentUserId).document(recipeId).get().await().toObject()

  override suspend fun getRecipeNEW(recipeId: String): Recipe? =
    completeCollection().document(recipeId).get().await().toObject()

  override suspend fun save(task: Recipe): String =
    trace(SAVE_TASK_TRACE) {completeCollection().add(task).await().id }


  override suspend fun update(task: Recipe): Unit =
    trace(UPDATE_TASK_TRACE) {
      completeCollection().document(task.id).set(task).await()
    }

  override suspend fun delete(recipeId: String) {
    currentCollection(auth.currentUserId).document(recipeId).delete().await()
  }

  override suspend fun updateUserCollection(task: Recipe): Unit =
    trace(UPDATE_TASK_TRACE) {
      currentCollection(auth.currentUserId).document(task.id).set(task).await()
    }


  private fun currentCollection(uid: String): CollectionReference =
    firestore.collection(USER_COLLECTION).document(uid).collection(USER_RECIPE_COLLECTION)



  @OptIn(ExperimentalCoroutinesApi::class)
  override val recipe: Flow<List<Recipe>>
    get() =
      auth.currentUser.flatMapLatest { user ->
      completeCollection().snapshots().map { snapshot -> snapshot.toObjects() }}


  private fun completeCollection(): CollectionReference =
    firestore.collection(RECIPE_COLLECTION)




  companion object {
    private const val USER_COLLECTION = "users"
    private const val USER_RECIPE_COLLECTION = "recipes"
    private const val SAVE_TASK_TRACE = "saveTask"
    private const val UPDATE_TASK_TRACE = "updateTask"
    private const val RECIPE_COLLECTION = "recipe-app-6b055"

  }
}
