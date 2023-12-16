package app.raihan.londri_capstone.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.repository.AuthRepository
import app.raihan.londri_capstone.data.response.LoginResponse
import app.raihan.londri_capstone.models.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository : AuthRepository) : ViewModel(){

    private val _loginResponse = MediatorLiveData<Result<LoginResponse>>()
    val loginResponse: LiveData<Result<LoginResponse>> = _loginResponse

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun isLoggedIn() {
        viewModelScope.launch {
            repository.isLoggedIn()
        }
    }

    fun postLogin(email: String, password: String) {
        val liveData = repository.postLogin(email, password)
        _loginResponse.addSource(liveData){ result ->
            _loginResponse.value = result
        }
    }
}