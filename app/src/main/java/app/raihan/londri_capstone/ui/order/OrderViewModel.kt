package app.raihan.londri_capstone.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.raihan.londri_capstone.data.response.DataDetailLaundryResponse
import app.raihan.londri_capstone.data.response.LayananByLaundryResponse
import app.raihan.londri_capstone.data.response.LoginResponse
import app.raihan.londri_capstone.data.response.OrderResponse
import app.raihan.londri_capstone.data.response.ResultDataItem
import app.raihan.londri_capstone.data.response.ResultDoneItem
import app.raihan.londri_capstone.data.response.ResultProcessItem
import app.raihan.londri_capstone.data.response.ResultProfile
import app.raihan.londri_capstone.models.UserModel
import app.raihan.londri_capstone.utils.UserRepository
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: UserRepository) : ViewModel() {
    val profileResponse: LiveData<List<ResultProfile>> = repository.profileResponse
    val layananByLaundry: LiveData<List<LayananByLaundryResponse>> = repository.layananByLaundry
    val orderResponse: LiveData<OrderResponse> = repository.orderResponse
    val statusProcessResponse: LiveData<List<ResultProcessItem>?> = repository.statusProcessResponse
    val statusDoneResponse: LiveData<List<ResultDoneItem>?> = repository.statusDoneResponse


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getProfile(token: String) {
        viewModelScope.launch {
            repository.getProfile(token)
        }
    }

    fun getLayananByLaundry(laundryId: String) {
        viewModelScope.launch {
            repository.getLayananByLaundry(laundryId)
        }
    }

    fun postOrder(tokenSession: String, idLaundry: String, layananId: String, estimasiBerat: String, catatan: String) {
        viewModelScope.launch {
            repository.postOrder(tokenSession, idLaundry, layananId, estimasiBerat, catatan)
        }
    }

    fun getStatusProcess(tokenSession: String){
        viewModelScope.launch {
            repository.getStatusProcess(tokenSession)
        }
    }

    fun getStatusDone(tokenSession: String){
        viewModelScope.launch {
            repository.getStatusDone(tokenSession)
        }
    }
}