package app.raihan.londri_capstone.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.databinding.ActivityLoginBinding
import app.raihan.londri_capstone.models.UserModel
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.MainActivity
import app.raihan.londri_capstone.ui.signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    companion object {
        const val KEY = "Bearer "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginAction()
        observe()
    }

    private fun observe(){
        viewModel.loginResponse.observe(this@LoginActivity) { response ->
            when(response){
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Error -> {
                    showLoading(false)
                    showDialogFail()
                }

                is Result.Success -> {
                    showLoading(true)
                    viewModel.isLoggedIn()
                    saveSession(
                        UserModel(
                            KEY + response.data.response?.token.toString(),
                            true
                        )
                    )
                    showDialogSuccess()

                }
            }
        }
    }


    private fun loginAction() {
        binding.apply {
            btnLogin.isEnabled = false

            tvRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(intent)
            }
            emailEditText.addTextChangedListener {
                validateInput()
            }

            passwordEditText.addTextChangedListener {
                validateInput()
            }

            btnLogin.setOnClickListener {
                loginAccount()
            }
        }
    }

    private fun validateInput() {
        binding.apply {
            val isEmailValid = emailEditText.length() > 0 && emailEditText.error.isNullOrEmpty()
            val isPasswordValid =
                passwordEditText.length() > 0 && passwordEditText.error.isNullOrEmpty()

            btnLogin.isEnabled = isEmailValid && isPasswordValid
        }
    }

    private fun loginAccount() {
        binding.apply {
            viewModel.postLogin(
                emailEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
    }

    private fun showDialogSuccess(){
        AlertDialog.Builder(this).apply {
            setTitle("Berhasil")
            setMessage("Anda berhasil login!")
            setPositiveButton("Okay") { _, _ ->
                val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(moveIntent)
                finish()
            }
            create()
            show()
        }
    }

    private fun showDialogFail(){
        AlertDialog.Builder(this).apply {
            setTitle("Maaf")
            setMessage("Login gagal! Masukkan email dan password anda yang benar")
            create()
            show()
        }
        showLoading(false)
}

    private fun saveSession(session: UserModel) {
        viewModel.saveSession(session)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}