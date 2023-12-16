package app.raihan.londri_capstone.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.raihan.londri_capstone.data.repository.AuthRepository
import app.raihan.londri_capstone.models.UserModel

class SplashScreenViewModel (private val repository: AuthRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

}