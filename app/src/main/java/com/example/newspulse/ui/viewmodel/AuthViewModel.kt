package com.example.newspulse.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class AuthViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    var isLoading by mutableStateOf(false)

    fun login(email: String,pass: String,onResult: (Boolean, String) -> Unit) {
        isLoading = true
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    onResult(true, "Login Successful")
                } else {
                    val errorMessage = when (task.exception) {
                        is FirebaseAuthWeakPasswordException -> "Weak Password"
                        is FirebaseAuthInvalidCredentialsException -> "Invalid Email or Password"
                        else -> task.exception?.message ?: "Login Failed"
                    }
                    onResult(false, errorMessage)
                }
            }
    }

        fun signup(email: String,pass: String,onResult: (Boolean, String) -> Unit){
            isLoading=true
            auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener {
                    task->
                    isLoading=false
                    if(task.isSuccessful){
                        onResult(true,"Signup Successful")
                    }else{
                        val errorMessage =when(task.exception){
                            is FirebaseAuthWeakPasswordException -> "Weak Password"
                            is FirebaseAuthInvalidCredentialsException -> "Invalid Email"
                            is FirebaseAuthUserCollisionException -> "User Already Exists"
                            else -> "Signup Failed"
                        }

                        onResult(false,errorMessage)
                    }
                }
        }

    fun logout(onLogout: () -> Unit) {
        auth.signOut()
        onLogout()
    }
    }
