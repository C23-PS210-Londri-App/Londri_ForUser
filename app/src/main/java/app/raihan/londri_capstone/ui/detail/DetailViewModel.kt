package app.raihan.londri_capstone.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.raihan.londri_capstone.data.response.DataDetailLaundryResponse
import app.raihan.londri_capstone.data.response.LayananByLaundryResponse
import app.raihan.londri_capstone.data.response.ResultDataLayananByLaundryItem
import app.raihan.londri_capstone.models.UserModel
import app.raihan.londri_capstone.utils.UserRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository : UserRepository): ViewModel() {
    val isLoading : LiveData<Boolean> = repository.isLoading
    val detailResponse : LiveData<List<DataDetailLaundryResponse>> = repository.detailLaundryResponse
    val layananByLaundry: LiveData<List<LayananByLaundryResponse>> = repository.layananByLaundry
    val insideLayananByLaundry: LiveData<List<ResultDataLayananByLaundryItem>?> = repository.insideLayananByLaundry
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getDetailLaundry(token: String, laundryId : String) {
        viewModelScope.launch {
            repository.getDetailLaundry(token, laundryId)
        }
    }

    fun getLayananByLaundry(laundryId: String) {
        viewModelScope.launch {
            repository.getLayananByLaundry(laundryId)
        }
    }
}