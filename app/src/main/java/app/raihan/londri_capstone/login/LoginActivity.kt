package app.raihan.londri_capstone.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.raihan.londri_capstone.R
import app.raihan.londri_capstone.databinding.ActivityLoginBinding
import app.raihan.londri_capstone.signup.SignupActivity

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
    }
}