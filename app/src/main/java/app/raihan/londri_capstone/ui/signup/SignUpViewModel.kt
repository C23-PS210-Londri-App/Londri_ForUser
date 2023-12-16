package app.raihan.londri_capstone.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.repository.AuthRepository
import app.raihan.londri_capstone.data.response.RegisterResponse
import kotlinx.coroutines.launch

class SignUpViewModel (private val repository: AuthRepository) : ViewModel() {

    private val _registerResponse = MediatorLiveData<Result<RegisterResponse>>()
    val registerResponse: LiveData<Result<RegisterResponse>> = _registerResponse

    fun postRegister(
        name: String,
        email: String,
        telephone: String,
        password: String,
    ) {
        val liveData = repository.postRegister(name, email, telephone, password)
        _registerResponse.addSource(liveData){ result ->
            _registerResponse.value = result
        }
    }
}