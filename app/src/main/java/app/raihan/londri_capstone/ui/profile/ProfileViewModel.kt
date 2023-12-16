package app.raihan.londri_capstone.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.repository.ProfileRepository
import app.raihan.londri_capstone.data.response.ProfileResponse
import app.raihan.londri_capstone.models.UserModel
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository): ViewModel() {

    private val _profileResponse = MediatorLiveData<Result<ProfileResponse>>()
    val profileResponse: LiveData<Result<ProfileResponse>> = _profileResponse
    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getProfile(token: String) {
        val liveData = repository.getProfile(token)
        _profileResponse.addSource(liveData){ result ->
            _profileResponse.value = result
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}