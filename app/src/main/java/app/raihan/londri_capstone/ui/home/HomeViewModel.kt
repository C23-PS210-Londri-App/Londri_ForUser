package app.raihan.londri_capstone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.raihan.londri_capstone.data.response.AllLaundryResponse
import app.raihan.londri_capstone.data.response.ResultDataItem
import app.raihan.londri_capstone.data.response.ResultDataSearchItem
import app.raihan.londri_capstone.data.response.ResultNearestDataItem
import app.raihan.londri_capstone.data.response.ResultProfile
import app.raihan.londri_capstone.models.UserModel
import app.raihan.londri_capstone.utils.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository) : ViewModel() {
    val profileResponse: LiveData<List<ResultProfile>> = repository.profileResponse
    val laundryResponse: LiveData<List<ResultDataItem>?> = repository.laundryResponse
    val nearestLaundryResponse: LiveData<List<ResultNearestDataItem>?> = repository.nearestLaundryResponse
    val listSearchItem: LiveData<List<ResultDataSearchItem>?> = repository.listSearchResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun getProfile(token: String){
        viewModelScope.launch {
            repository.getProfile(token)
        }
    }

    fun getAllLaundry(){
        viewModelScope.launch {
            repository.getAllLaundry()
        }
    }

    fun getNearestLaundry(latitude: String, longitude: String){
        viewModelScope.launch {
            repository.getNearestLaundry(latitude, longitude)
        }
    }

    fun getSearch(q : String){
        viewModelScope.launch {
            repository.getSearch(q)
        }
    }

}