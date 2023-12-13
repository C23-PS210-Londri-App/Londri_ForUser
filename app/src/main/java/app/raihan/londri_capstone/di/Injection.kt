package app.raihan.londri_capstone.di

import android.content.Context
import app.raihan.londri_capstone.data.api.ApiConfig
import app.raihan.londri_capstone.pref.UserPreference
import app.raihan.londri_capstone.pref.dataStore
import app.raihan.londri_capstone.utils.UserRepository

object Injection {
    fun provideRepository (context : Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref, apiService)
    }
}