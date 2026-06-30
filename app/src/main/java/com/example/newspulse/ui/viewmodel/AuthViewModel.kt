package com.example.newspulse.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val auth = FirebaseAuth.getInstance()//initializing firebase connections

    var isLoading by mutableStateOf(false)

    val isLoggedIn: Boolean
    get() = auth.currentUser != null


    fun login(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                // await() suspends the coroutine until Firebase finishes
                auth.signInWithEmailAndPassword(email, pass).await()
                onResult(true, "Login Successful")
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is FirebaseAuthInvalidCredentialsException -> "Invalid Email or Password"
                    else -> e.message ?: "Login Failed"
                }
                onResult(false, errorMessage)
            } finally {
                isLoading = false
            }
        }
    }

    fun signup(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                auth.createUserWithEmailAndPassword(email, pass).await()
                onResult(true, "Signup Successful")
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is FirebaseAuthWeakPasswordException -> "Weak Password"
                    is FirebaseAuthInvalidCredentialsException -> "Invalid Email"
                    is FirebaseAuthUserCollisionException -> "User Already Exists"
                    else -> e.message ?: "Signup Failed"
                }
                onResult(false, errorMessage)
            } finally {
                isLoading = false
            }
        }
    }

    fun logout(onLogout: () -> Unit) {
        auth.signOut() //this deletes the session token(ticket) and auth.currentuser becomes null
        onLogout()
    }
    fun notificationFcmToken() {
        viewModelScope.launch {
            try {
                val token = FirebaseMessaging.getInstance().token.await()
                Log.d("FCM", "Token: $token")
            } catch (e: Exception) {
                Log.d("FCM", "Fetching FCM registration token failed", e)
            }
        }
    }
    }


