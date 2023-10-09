package com.example.petme.data.repository

import com.example.petme.common.Constant.COLLECTION_PATH
import com.example.petme.common.Constant.E_MAIL
import com.example.petme.common.Constant.ID
import com.example.petme.common.Constant.NICKNAME
import com.example.petme.common.Constant.PHONE_NUMBER
import com.example.petme.data.model.User
import com.example.petme.domain.repository.Authenticator
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthenticator @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
):Authenticator {

    override suspend fun getFirebaseUserUid(): String = firebaseAuth.currentUser?.uid.orEmpty()

    override suspend fun signUpWithEmailAndPassword(user: User, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(user.email.trim(), password.trim()).await()
        val userModel = hashMapOf(
            ID to getFirebaseUserUid(),
            E_MAIL to user.email,
            NICKNAME to user.nickname,
            PHONE_NUMBER to user.phoneNumber
        )

        firebaseFirestore.collection(COLLECTION_PATH).document(getFirebaseUserUid())
            .set(userModel).await()
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()

    override suspend fun sendPasswordResetEmail(email: String): Void =
        firebaseAuth.sendPasswordResetEmail(email).await()


    override suspend fun isCurrentUserExist() = firebaseAuth.currentUser != null

    override suspend fun getCurrentUser(): User {
        val user =
            firebaseFirestore.collection(COLLECTION_PATH).document(getFirebaseUserUid())
                .get().await()

        return User(
            user[E_MAIL] as String,
            user[NICKNAME] as String,
            user[PHONE_NUMBER] as String,
        )    }

    override suspend fun signOut() = firebaseAuth.signOut()
}