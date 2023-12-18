package app.raihan.londri_capstone.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.repository.DetailLaundryRepository
import app.raihan.londri_capstone.data.response.DetailLaundryResponse
import app.raihan.londri_capstone.models.UserModel

class DetailLaundryViewModel(private val repository: DetailLaundryRepository) : ViewModel() {

    private val _detailLaundryResponse = MediatorLiveData<Result<DetailLaundryResponse>>()
    val detailLaundryResponse: LiveData<Result<DetailLaundryResponse>> = _detailLaundryResponse

    fun getDetailLaundry(token: String, laundryId: String,) {
        val liveData = repository.getDetailLaundry(token, laundryId,)
        _detailLaundryResponse.addSource(liveData){ result ->
            _detailLaundryResponse.value = result
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}