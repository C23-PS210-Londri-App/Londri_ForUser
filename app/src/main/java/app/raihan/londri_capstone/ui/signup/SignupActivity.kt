package app.raihan.londri_capstone.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import app.raihan.londri_capstone.data.Result
import app.raihan.londri_capstone.databinding.ActivitySignupBinding
import app.raihan.londri_capstone.models.ViewModelFactory
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
        binding.apply {
            tvLogin.setOnClickListener{
                val moveIntent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(moveIntent)
            }

            btnDaftar.setOnClickListener{
                validateInput()
            }
        }

        observe()
    }

    private fun observe(){
        viewModel.registerResponse.observe(this) { response ->
            when(response){
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Error -> {
                    showLoading(false)
                    registerFailedToast("Register gagal, silahkan coba lagi!")
                }

                is Result.Success -> {
                    showLoading(true)
                    showDialogSuccess()
                }
            }
        }
    }

    private fun EditText.isValidInput(): Boolean {
        return length() > 0 && error.isNullOrEmpty()
    }

    private fun validateInput() {
        binding.apply {
            val isNameValid = edName.isValidInput()
            val isEmailValid = edEmail.isValidInput()
            val isTelephoneValid = edPhone.isValidInput()
            val isPasswordValid = edPassword.isValidInput()
            val isConfirmPasswordValid = edRepeatPassword.isValidInput()
            if (isNameValid && isEmailValid && isTelephoneValid && isPasswordValid && isConfirmPasswordValid) {
                if (edPassword.text.toString() == edRepeatPassword.text.toString()){
                    registerAccount(edName.text.toString(), edEmail.text.toString(), edPhone.text.toString(), edPassword.text.toString())
                }else{
                    Toast.makeText(
                        this@SignupActivity,
                        "Password harus sama! ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this@SignupActivity,
                    "Mohon lengkapi semua data! ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun registerAccount(name: String, email: String, telephone: String, password: String) {
        binding.apply {
            viewModel.postRegister(
                name,
                email,
                telephone,
                password
            )
        }
    }

    private fun showDialogSuccess() {
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun registerFailedToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}