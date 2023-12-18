package app.raihan.londri_capstone.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.repository.HomeRepository
import app.raihan.londri_capstone.data.response.OnProcessLaundryResponse
import app.raihan.londri_capstone.models.UserModel

class OrderViewModel(private val homeRepository: HomeRepository, ) : ViewModel() {

    private val _onProcessLaundryResponse = MediatorLiveData<Result<OnProcessLaundryResponse>>()
    val onProcessLaundryResponse: LiveData<Result<OnProcessLaundryResponse>> = _onProcessLaundryResponse

    fun getOnProcessLaundry(token: String){
        val liveData = homeRepository.getProcessLaundry(token)
        _onProcessLaundryResponse.addSource(liveData){ result ->
            _onProcessLaundryResponse.value = result
        }
    }

    fun getSession(): LiveData<UserModel> {
        return homeRepository.getSession().asLiveData()
    }
}