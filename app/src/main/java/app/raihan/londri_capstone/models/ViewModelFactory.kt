package app.raihan.londri_capstone.models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.raihan.londri_capstone.data.repository.AuthRepository
import app.raihan.londri_capstone.data.repository.HomeRepository
import app.raihan.londri_capstone.data.repository.ProfileRepository
import app.raihan.londri_capstone.di.Injection
import app.raihan.londri_capstone.ui.home.HomeViewModel
import app.raihan.londri_capstone.ui.login.LoginViewModel
import app.raihan.londri_capstone.ui.profile.ProfileViewModel
import app.raihan.londri_capstone.ui.signup.SignUpViewModel
import app.raihan.londri_capstone.ui.splashscreen.SplashScreenViewModel

class ViewModelFactory (private val authRepository: AuthRepository, private val profileRepository: ProfileRepository, private val homeRepository: HomeRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(profileRepository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(profileRepository, homeRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown viewModel class : " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideAuthRepository(context),
                        Injection.provideProfileRepository(context),
                        Injection.provideHomeRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}