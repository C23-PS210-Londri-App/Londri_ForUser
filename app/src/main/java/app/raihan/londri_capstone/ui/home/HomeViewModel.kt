package app.raihan.londri_capstone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.repository.HomeRepository
import app.raihan.londri_capstone.data.repository.ProfileRepository
import app.raihan.londri_capstone.data.response.AllLaundryResponse
import app.raihan.londri_capstone.data.response.ProfileResponse
import app.raihan.londri_capstone.models.UserModel

class HomeViewModel(private val profileRepository: ProfileRepository, private val homeRepository: HomeRepository) : ViewModel() {

    private val _profileResponse = MediatorLiveData<Result<ProfileResponse>>()
    val profileResponse: LiveData<Result<ProfileResponse>> = _profileResponse

    private val _allLaundryResponse = MediatorLiveData<Result<AllLaundryResponse>>()
    val allLaundryResponse: LiveData<Result<AllLaundryResponse>> = _allLaundryResponse
    fun getProfile(token: String) {
        val liveData = profileRepository.getProfile(token)
        _profileResponse.addSource(liveData){ result ->
            _profileResponse.value = result
        }
    }

    fun getSession(): LiveData<UserModel> {
        return profileRepository.getSession().asLiveData()
    }

    fun getAllLaundry(token: String){
        val liveData = homeRepository.getAllLaundry(token)
        _allLaundryResponse.addSource(liveData){ result ->
            _allLaundryResponse.value = result
        }
    }
}