package app.raihan.londri_capstone.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.raihan.londri_capstone.data.response.ResultProfile
import app.raihan.londri_capstone.models.UserModel
import app.raihan.londri_capstone.utils.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository : UserRepository) : ViewModel() {
    val profileResponse: LiveData<List<ResultProfile>> = repository.profileResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getProfile(token: String){
        viewModelScope.launch {
            repository.getProfile(token)
        }
    }
}