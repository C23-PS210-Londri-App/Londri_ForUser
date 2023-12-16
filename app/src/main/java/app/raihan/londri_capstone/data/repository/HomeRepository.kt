package app.raihan.londri_capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.api.ApiService
import app.raihan.londri_capstone.data.response.AllLaundryResponse
import app.raihan.londri_capstone.data.response.ResultDataItem
import app.raihan.londri_capstone.pref.UserPreference
import kotlinx.coroutines.Dispatchers

class HomeRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
){
    fun getAllLaundry(token: String): LiveData<Result<AllLaundryResponse>> =
        liveData(Dispatchers.IO){
            emit(Result.Loading)
            try {
                val response = apiService.getAllLaundry(token)
                emit(Result.Success(response))
            }catch (e:Exception){
                emit(Result.Error(e.message.toString()))
            }
        }

    companion object {
        private const val TAG = "HomeRepository"

        @Volatile
        private var instance: HomeRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): HomeRepository =
            instance ?: synchronized(this) {
                instance ?: HomeRepository(userPreference, apiService)
            }.also { instance = it }
    }
}