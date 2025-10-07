package com.example.praktek // Sesuaikan dengan nama package-mu

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.praktek.ui.theme.PraktekTheme // Sesuaikan dengan tema-mu
import kotlinx.serialization.Serializable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import com.google.firebase.auth.FirebaseAuth


@Serializable object Login

@Serializable object Register
@Serializable object Profile
@Serializable object NextScreen
@Serializable object ThirdScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraktekTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Login,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ){

                        composable<Login> {
                            LoginPage(navController = navController)
                        }


                        composable<Profile> {
                            ProfileScreen(navController = navController)
                        }


                        composable<NextScreen> {
                            Tscreen(navController)
                        }
                        composable<ThirdScreen> {
                            Trescreen(navController)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun LoginPage(navController: NavController, modifier: Modifier = Modifier){
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val firebaseAuth = FirebaseAuth.getInstance()

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Silakan Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text("email")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {Text("Password")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = {isPasswordVisible = !isPasswordVisible}) {
                    val image = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                    Icon(imageVector = image, contentDescription = "Toggle password visibility")
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {

                if (email.isNotBlank() && password.isNotBlank()) {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            task ->
                            if (task.isSuccessful){
                                Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                                navController.navigate(Profile){
                                    popUpTo(Login){ inclusive = true }
                                }
                            }else{
                                val errorMassage = task.exception?.message ?: "Login gagal, tolong coba lagi"
                                Toast.makeText(context, errorMassage, Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {

                    Toast.makeText(context, "email atau Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}

@Composable
fun RegisterPage(navController: NavController, modifier: Modifier = Modifier){
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Buat Akun Baru", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text("Email")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

    }
}



@Composable
fun ProfileScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ProfileCard(
            name = "Muhammad Arifin Ilham",
            desc = "Student at Binus University",
            Qqa = "XML tidak asik",
            navigation = navController
        )
    }
}

@Composable
fun ProfileCard(
    name: String,
    desc: String,
    Qqa : String,
    navigation: NavController
){
    Card (
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 40.dp, vertical = 140.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        Column (
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 60.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .size(160.dp)
            )
            Text(name, fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp))
            Text(desc, fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp))
            Text(Qqa, fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 20.dp))
            Button(onClick = { navigation.navigate(NextScreen) }) {
                Text("Go to Next")
            }
            Button(onClick = { navigation.navigate(ThirdScreen) }) {
                Text("Go to Third")
            }
        }
    }
}

@Composable
fun Tscreen(navigation: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is Next Screen")
        Button(onClick = { navigation.navigate(Profile) }) {
            Text("Go to Profile")
        }
        Button(onClick = { navigation.navigate(ThirdScreen) }) {
            Text("Go to Third")
        }
    }
}

@Composable
fun Trescreen(navigation: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is Third Screen")
        Button(onClick = { navigation.navigate(Profile) }) {
            Text("Go to Profile")
        }
        Button(onClick = { navigation.navigate(NextScreen) }) {
            Text("Go to Next Screen")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    PraktekTheme {

        LoginPage(navController = rememberNavController())
    }
}