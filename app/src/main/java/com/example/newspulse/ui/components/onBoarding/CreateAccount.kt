package com.example.newspulse.ui.components.onBoarding

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newspulse.ui.viewmodel.AuthViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateAccountScreen(
onLoginClick: () -> Unit = {},
onSignUpSuccess: () -> Unit = {},
authViewModel: AuthViewModel = viewModel()
){
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.padding(horizontal = 24.dp),

            text = "Join the Community and stay updated with the latest news",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {name =it},
            label = {Text("Full Name")},
            placeholder = {Text("Enter your Full Name", fontSize = 13.sp)},
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(12.dp)


        )

Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email =it},
            label = {Text("Email")},
            placeholder = {Text("Enter your email", fontSize = 13.sp)},
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(12.dp)


        )
        Spacer(modifier = Modifier.height(8.dp))

        var passwordVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password,
            onValueChange = {password =it},
            label = {Text("Password")},
            placeholder = {Text("Enter your password", fontSize = 13.sp)},
            visualTransformation =if(passwordVisible){
                androidx.compose.ui.text.input.VisualTransformation.None
            }else{
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                    )
                }

            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
           val annotatedString = buildAnnotatedString {
               append("By creating an account, you agree to our ")

               pushStringAnnotation(tag = "terms", annotation = "terms")
               withStyle(style=SpanStyle(color = MaterialTheme.colorScheme.primary,fontWeight = FontWeight.Bold)) {
                   append("Terms of Service")
               }
               pop()

               append(" and ")

               pushStringAnnotation(tag = "privacy", annotation = "privacy")
               withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary ,fontWeight = FontWeight.Bold)) {
                   append("Privacy Policy")
               }
               pop()


           }
            ClickableText(
                text = annotatedString,
                style= TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                ),
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "terms",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        // Handle terms click
                    }
                    annotatedString.getStringAnnotations(
                        tag = "privacy",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        // Handle privacy click
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
       Button(
            onClick = {

                authViewModel.signup(email,password) { success, message ->
                    if (success) {
                        onSignUpSuccess()
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp),
           enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && isChecked,
        ){
           if(authViewModel.isLoading){
               androidx.compose.material3.CircularProgressIndicator(
                   color = Color.White,
                   modifier = Modifier.size(24.dp))
           }
            Text(text = "Create Account",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(text = "Or Sign up With",
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp))
            HorizontalDivider(modifier = Modifier.weight(1f))

        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Continue with Google",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,)

        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Continue with Facebook",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                text = "Already have an account?"
            )
            TextButton(
                onClick = onLoginClick,
            ) {
                Text(
                    text = "Log In",
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}