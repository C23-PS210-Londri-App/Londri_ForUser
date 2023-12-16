package app.raihan.londri_capstone.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.data.api.ApiService
import app.raihan.londri_capstone.data.response.ProfileResponse
import app.raihan.londri_capstone.models.UserModel
import app.raihan.londri_capstone.pref.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Tag

class ProfileRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
){
    suspend fun logout() {
        userPreference.logout()
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun getProfile(token: String):
            LiveData<Result<ProfileResponse>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getProfile(token)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    companion object {
        private const val TAG = "ProfileRepository"

        @Volatile
        private var instance: ProfileRepository? = null
        fun getInstance(
            profilePreference: UserPreference,
            apiService: ApiService
        ): ProfileRepository =
            instance ?: synchronized(this) {
                instance ?: ProfileRepository(profilePreference, apiService)
            }.also { instance = it }
    }
}