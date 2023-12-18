package app.raihan.londri_capstone.di

import android.content.Context
import app.raihan.londri_capstone.data.api.ApiConfig
import app.raihan.londri_capstone.data.repository.AuthRepository
import app.raihan.londri_capstone.data.repository.DetailLaundryRepository
import app.raihan.londri_capstone.data.repository.HomeRepository
import app.raihan.londri_capstone.data.repository.ProfileRepository
import app.raihan.londri_capstone.pref.UserPreference
import app.raihan.londri_capstone.pref.dataStore

object Injection {
    fun provideAuthRepository (context : Context): AuthRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return AuthRepository.getInstance(pref, apiService)
    }

    fun provideProfileRepository (context: Context) : ProfileRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return  ProfileRepository.getInstance(pref, apiService)
    }

    fun provideHomeRepository (context: Context) : HomeRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return HomeRepository.getInstance(pref, apiService)
    }

    fun provideDetailLaundryRepository (context: Context) : DetailLaundryRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return DetailLaundryRepository.getInstance(pref, apiService)
    }
}