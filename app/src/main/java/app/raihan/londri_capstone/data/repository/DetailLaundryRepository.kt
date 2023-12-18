package app.raihan.londri_capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.api.ApiService
import app.raihan.londri_capstone.data.response.DetailLaundryResponse
import app.raihan.londri_capstone.models.UserModel
import app.raihan.londri_capstone.pref.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class DetailLaundryRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun getDetailLaundry(token: String, laundryId : String): LiveData<Result<DetailLaundryResponse>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getDetailLaundry(token, laundryId)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    companion object {
        private const val TAG = "DetailLaundryRepository"

        @Volatile
        private var instance: DetailLaundryRepository? = null

        fun getInstance(
            detailpreference: UserPreference,
            apiService: ApiService
        ): DetailLaundryRepository =
            instance ?: synchronized(this) {
                instance ?: DetailLaundryRepository(detailpreference, apiService)
            }.also { instance = it }
    }
}
