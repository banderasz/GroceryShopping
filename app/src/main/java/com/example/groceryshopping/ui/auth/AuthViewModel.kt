import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState?>(null)
    val authState = _authState.asStateFlow()

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthState.SUCCESS
                    } else {
                        _authState.value =
                            AuthState.ERROR(task.exception?.message ?: "Unknown error")
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.ERROR(e.message ?: "Unknown error")
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthState.SUCCESS
                    } else {
                        _authState.value =
                            AuthState.ERROR(task.exception?.message ?: "Unknown error")
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.ERROR(e.message ?: "Unknown error")
            }
        }
    }

    sealed class AuthState {
        object SUCCESS : AuthState()
        class ERROR(val message: String) : AuthState()
    }
}
