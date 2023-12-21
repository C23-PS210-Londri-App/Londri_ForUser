package app.raihan.londri_capstone.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import app.raihan.londri_capstone.databinding.ActivitySplashScreenBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.MainActivity
import app.raihan.londri_capstone.ui.home.HomeViewModel
import app.raihan.londri_capstone.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getSession().observe(this) { user ->
                if (!user.isLogin) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    val token = user.token
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("extra token", token)
                    startActivity(intent)
                    finish()
                }
            }

        }, 3000)
    }
}