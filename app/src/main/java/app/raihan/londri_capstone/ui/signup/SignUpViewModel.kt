package app.raihan.londri_capstone.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.raihan.londri_capstone.data.response.RegisterResponse
import app.raihan.londri_capstone.utils.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel (private val repository: UserRepository) : ViewModel() {
    val isLoading: LiveData<Boolean> = repository.isLoading
    val regResponse: LiveData<RegisterResponse> = repository.registerResponse

    fun postRegister(
        name: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            repository.postRegister(name, email, password)
        }
    }
}