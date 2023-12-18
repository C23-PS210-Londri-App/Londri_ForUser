package app.raihan.londri_capstone.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.repository.DetailLaundryRepository
import app.raihan.londri_capstone.data.repository.HomeRepository
import app.raihan.londri_capstone.data.repository.ProfileRepository
import app.raihan.londri_capstone.data.response.FormOrderResponse
import app.raihan.londri_capstone.data.response.ProfileResponse
import app.raihan.londri_capstone.models.UserModel

class FormOrderViewModel(private val repository: DetailLaundryRepository, private val profileRepository: ProfileRepository, private val homeRepository: HomeRepository) : ViewModel() {
    private val _profileResponse = MediatorLiveData<Result<ProfileResponse>>()
    val profileResponse: LiveData<Result<ProfileResponse>> = _profileResponse

    private val _formOrderResponse = MediatorLiveData<Result<FormOrderResponse>>()
    val formOrderResponse: LiveData<Result<FormOrderResponse>> = _formOrderResponse

    fun getProfile(token: String) {
        val liveData = profileRepository.getProfile(token)
        _profileResponse.addSource(liveData){ result ->
            _profileResponse.value = result
        }
    }

    fun postOrderLaundry(token: String, laundryId: String) {
        val liveData = homeRepository.postOrderLaundry(token, laundryId)
        _formOrderResponse.addSource(liveData){ result ->
            _formOrderResponse.value = result
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}