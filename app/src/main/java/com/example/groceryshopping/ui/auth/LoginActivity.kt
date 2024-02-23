import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.groceryshopping.ui.theme.GroceryShoppingTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroceryShoppingTheme {
                LoginScreen(navController = rememberNavController())
            }
        }
    }
}

@Composable
fun LoginScreen(authViewModel: AuthViewModel = viewModel(), navController: NavHostController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardActions = KeyboardActions(onDone = {
                authViewModel.signIn(email, password)
            })
        )
        Button(onClick = { authViewModel.signIn(email, password) }) {
            Text("Login")
        }
        Button(onClick = {
            navController.navigate("register")
        }) {
            Text("Register")
        }

    }

    // Observe authentication state and react accordingly
    val authState by authViewModel.authState.collectAsState()
    LaunchedEffect(authState) {
        when (authState) {
            is AuthViewModel.AuthState.SUCCESS -> navController.navigate("groceryListScreen") {
                popUpTo("login") { inclusive = true } // Clear back stack
            }

            is AuthViewModel.AuthState.ERROR -> {
            }

            else -> {}
        }
    }
}
