package app.raihan.londri_capstone.models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.raihan.londri_capstone.di.Injection
import app.raihan.londri_capstone.ui.detail.DetailViewModel
import app.raihan.londri_capstone.ui.home.HomeViewModel
import app.raihan.londri_capstone.ui.login.LoginViewModel
import app.raihan.londri_capstone.ui.order.OrderViewModel
import app.raihan.londri_capstone.ui.profile.ProfileViewModel
import app.raihan.londri_capstone.ui.signup.SignUpViewModel
import app.raihan.londri_capstone.utils.UserRepository

class ViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(repository) as T
            }modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }modelClass.isAssignableFrom(OrderViewModel::class.java) -> {
                OrderViewModel(repository) as T
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
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}