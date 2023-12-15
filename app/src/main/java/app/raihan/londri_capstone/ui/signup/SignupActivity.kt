package app.raihan.londri_capstone.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import app.raihan.londri_capstone.databinding.ActivitySignupBinding
import app.raihan.londri_capstone.models.ViewModelFactory
import app.raihan.londri_capstone.ui.HomeActivity
import app.raihan.londri_capstone.ui.login.LoginActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerAction()

        binding.tvLogin.setOnClickListener{
            val moveIntent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(moveIntent)
        }
    }

    private fun registerAction() {
        binding.apply {
            btnDaftar.isEnabled = false

            edName.addTextChangedListener { validateInput() }
            edEmail.addTextChangedListener { validateInput() }
            edPassword.addTextChangedListener { validateInput() }
            edRepeatPassword.addTextChangedListener { validateInput() }

            btnDaftar.setOnClickListener {
                showLoading(true)
                if (edRepeatPassword.text.toString() == edPassword.text.toString()) {
                    viewModel.postRegister(
                        edName.toString(),
                        edEmail.toString(),
                        edPassword.toString(),
                    )
                    val intent = Intent(this@SignupActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignupActivity, "Password tidak sama", Toast.LENGTH_SHORT)
                        .show()
                    showLoading(false)
                }
            }

            viewModel.regResponse.observe(this@SignupActivity) { response ->
                response?.let {
                    if (it.error) {
                        showLoading(false)
                        Toast.makeText(this@SignupActivity, it.message, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        setupAction()
                    }
                }
            }
        }
    }

    private fun setupAction() {
        AlertDialog.Builder(this).apply {
            setTitle("Success")
            setMessage("Akun berhasil dibuat. Login untuk melanjutkan!")
            setPositiveButton("Lanjut") { _, _ ->
                finish()
            }
            create()
            show()
        }
    }

    private fun validateInput() {
        binding.apply {
            val isNameValid = edName.isValidInput()
            val isEmailValid = edEmail.isValidInput()
            val isPasswordValid = edPassword.isValidInput()
            val isRepeatPasswordValid = edRepeatPassword.isValidInput()

            btnDaftar.isEnabled =
                isNameValid && isEmailValid && isPasswordValid && isRepeatPasswordValid
        }
    }
    private fun EditText.isValidInput(): Boolean {
        return length() > 0 && error.isNullOrEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}