package com.example.recipes.domain.repository

import android.net.Uri
import com.example.recipes.domain.model.Response

typealias AddImageToStorageResponse = Response<Uri>
typealias AddImageUrlToFirestoreResponse = Response<Boolean>
typealias GetImageUrlFromFirestoreResponse = Response<String>

interface ProfileImageRepository {
    suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse

    suspend fun addImageUrlToFirestore(downloadUrl: Uri): AddImageUrlToFirestoreResponse

    suspend fun getImageUrlFromFirestore(): GetImageUrlFromFirestoreResponse
}