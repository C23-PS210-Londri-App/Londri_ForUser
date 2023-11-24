package app.raihan.londri_capstone.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.raihan.londri_capstone.databinding.ActivityLoginBinding
import app.raihan.londri_capstone.ui.main.MainActivity
import app.raihan.londri_capstone.ui.signup.SignupActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signUp.setOnClickListener{
            val moveIntent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(moveIntent)
        }

        binding.loginButton.setOnClickListener{
            val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(moveIntent)
        }
    }
}