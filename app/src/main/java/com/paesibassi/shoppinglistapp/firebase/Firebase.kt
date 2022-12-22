package com.paesibassi.shoppinglistapp.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.paesibassi.shoppinglistapp.database.Item
import com.paesibassi.shoppinglistapp.database.Item.Companion.toItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

object CloudStore {

    fun addData(item: Item) {
        val db = Firebase.firestore
        db.collection("myUser").get().await().max
        db.collection("myUser").document(item.name).set(item)
    }

    suspend fun readData(): Flow<List<Item>> {
        val db = Firebase.firestore
        return callbackFlow<List<Item>> {
            db.collection("myUser")
                .get()
                .await()
                .documents
                .mapNotNull { it.toItem() }
        }
//            .addOnSuccessListener { result ->
//                viewModel.firebase.value = "Document count: ${result.size()}"
//                for (document in result) {
//                    viewModel.addItem(document.data["text"] as String)
//                    Log.d("firebase", "${document.id} => ${document.data}")
//                    break
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("firebase", "Error getting documents.", exception)
//            }
    }
}