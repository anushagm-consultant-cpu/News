package com.example.newspulse.ui.components.onBoarding

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.newspulse.R
import com.example.newspulse.ui.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun rememberGoogleSignInHandler(
    authViewModel: AuthViewModel,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
): () -> Unit {
    val context = LocalContext.current
    val webClientId = stringResource(R.string.default_web_client_id)

    val gso = remember(webClientId) {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()
    }

    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { token ->
                authViewModel.signInWithGoogle(token) { success, msg ->
                    if (success) onSuccess() else onError(msg)
                }
            } ?: onError("Google Sign-In failed: ID token is null")
        } catch (e: ApiException) {
            onError("Google Sign-In failed: ${e.message}")
        }
    }

    // Returns a function that triggers the launcher
    return {
        launcher.launch(googleSignInClient.signInIntent)
    }
}
