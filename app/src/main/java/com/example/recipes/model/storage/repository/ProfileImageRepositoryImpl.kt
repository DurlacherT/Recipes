package com.example.recipes.data.repository

import android.net.Uri
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import com.example.recipes.core.Constants.CREATED_AT
import com.example.recipes.core.Constants.IMAGES
import com.example.recipes.core.Constants.PROFILE_IMAGE_NAME
import com.example.recipes.core.Constants.UID
import com.example.recipes.core.Constants.URL
import com.example.recipes.domain.model.Response.Failure
import com.example.recipes.domain.model.Response.Success
import com.example.recipes.domain.repository.AddImageToStorageResponse
import com.example.recipes.domain.repository.AddImageUrlToFirestoreResponse
import com.example.recipes.domain.repository.GetImageUrlFromFirestoreResponse
import com.example.recipes.domain.repository.ProfileImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore
) : ProfileImageRepository {
    override suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse {
        return try {
            val downloadUrl = storage.reference.child(IMAGES).child(PROFILE_IMAGE_NAME)
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            Success(downloadUrl)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun addImageUrlToFirestore(downloadUrl: Uri, recipeId: String): AddImageUrlToFirestoreResponse {
        return try {
            db.collection("recipe-app-6b055").document(recipeId).update(mapOf(
                URL to downloadUrl,
                CREATED_AT to FieldValue.serverTimestamp()
            )).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun getImageUrlFromFirestore(): GetImageUrlFromFirestoreResponse {
        return try {
            val imageUrl = db.collection(IMAGES).document(UID).get().await().getString(URL)
            Success(imageUrl)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}