package app.raihan.londri_capstone.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.raihan.londri_capstone.data.response.LoginResponse
import app.raihan.londri_capstone.models.UserModel
import app.raihan.londri_capstone.utils.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository : UserRepository) : ViewModel(){
    val isLoading : LiveData<Boolean> = repository.isLoading
    val logResponse : LiveData<LoginResponse> = repository.loginResponse

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login() {
        viewModelScope.launch {
            repository.login()
        }
    }

    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            repository.postLogin(email, password)
        }
    }
}