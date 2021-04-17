package com.lucascabral.hub4all.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.lucascabral.hub4all.R
import com.lucascabral.hub4all.api.LoginService
import com.lucascabral.hub4all.api.RetrofitClient
import com.lucascabral.hub4all.constants.HeaderConstants
import com.lucascabral.hub4all.databinding.ActivityLoginBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        supportActionBar?.hide()

        setButtonListener()
    }

    fun sendMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setButtonListener() {
        mainBinding.loginButton.setOnClickListener {
            handlerLogin()
        }
    }

    private fun handlerLogin() {
        val email = mainBinding.loginEmailEditText.text.toString()
        val password = mainBinding.loginPasswordEditText.text.toString()

        val validEmail = validateEmail(email)
        val validPassword = validatePassword(password)

        if (validEmail && validPassword) doLogin(email, password)
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.isNotEmpty()) {
            mainBinding.loginPasswordTextInputLayout.error = null
            true
        } else {
            mainBinding.loginPasswordTextInputLayout.error = getString(R.string.password_empty_error_message)
            false
        }
    }

    private fun validateEmail(email: String): Boolean {
        return when {
            email.isEmpty() -> {
                mainBinding.loginEmailTextInputLayout.error = getString(R.string.email_empty_error_message)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                mainBinding.loginEmailTextInputLayout.error = getString(R.string.email_invalid_error_message)
                false
            }
            else -> {
                mainBinding.loginEmailTextInputLayout.error = null
                true
            }
        }
    }

    private fun doLogin(email: String, password: String) {
        mainBinding.loginProgressBar.visibility = View.VISIBLE

        val remote = RetrofitClient.createService(LoginService::class.java)

        val call: Call<ResponseBody> = remote.login(email, password)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>, responseBody: Response<ResponseBody>
            ) {
                if (responseBody.isSuccessful) {
                    sendMessage(getString(R.string.login_successful_message))
                    //Recupera os headers
                    val (token: String, client: String, uid: String) = getHeaders(responseBody)

                    val intent = Intent(applicationContext, EnterprisesActivity::class.java)
                    intent.putExtra(HeaderConstants.ACCESS_TOKEN, token)
                    intent.putExtra(HeaderConstants.CLIENT, client)
                    intent.putExtra(HeaderConstants.UID, uid)
                    startActivity(intent)
                    finish()

                } else { //Exibindo texto de credenciais inválidas
                    mainBinding.loginProgressBar.visibility = View.GONE
                    mainBinding.loginCredentialsErrorTextView.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mainBinding.loginProgressBar.visibility = View.GONE
                sendMessage(getString(R.string.internet_failure_message))
            }
        })
    }

    private fun getHeaders(responseBody: Response<ResponseBody>): Triple<String, String, String> {
        val token: String = responseBody.headers()[HeaderConstants.ACCESS_TOKEN].toString()
        val client: String = responseBody.headers()[HeaderConstants.CLIENT].toString()
        val uid: String = responseBody.headers()[HeaderConstants.UID].toString()
        return Triple(token, client, uid)
    }
}