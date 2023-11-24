package app.raihan.londri_capstone.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.raihan.londri_capstone.databinding.ActivitySignupBinding
import app.raihan.londri_capstone.ui.login.LoginActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signIn.setOnClickListener{
            val moveIntent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(moveIntent)
        }

        binding.signupButton.setOnClickListener{
            val moveIntent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(moveIntent)
        }
    }
}