package com.lucascabral.hub4all.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.lucascabral.hub4all.R
import com.lucascabral.hub4all.api.LoginService
import com.lucascabral.hub4all.api.RetrofitClient
import com.lucascabral.hub4all.api.response.LoginResponse
import com.lucascabral.hub4all.constants.HeaderConstants
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        setListeners()
    }

    override fun onClick(view: View) {

        val id = view.id
        if (id == R.id.loginButton) {
            handlerLogin()
        }
    }

    private fun handlerLogin() {
        val email = loginEmailEdit.text.toString()
        val password = loginPasswordEdit.text.toString()

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (!password.isEmpty()) {//Email e Senha válidos!

                doLogin(email, password)

            } else {
                loginPasswordEdit.error = getString(R.string.password_empty_error_message)
            }
        } else {
            loginEmailEdit.error = getString(R.string.email_empty_error_message)
        }
    }

    private fun doLogin(email: String, password: String) {
        val remote = RetrofitClient.createService(LoginService::class.java)

        val call: Call<LoginResponse> = remote.login(email, password)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    loginProgressBar.visibility = View.VISIBLE
                    sendMessage(getString(R.string.login_successful_message))

                    val headers: Headers = response.headers()
                    val token: String = response.headers()[HeaderConstants.ACCESS_TOKEN].toString()
                    val client: String = response.headers()[HeaderConstants.CLIENT].toString()
                    val uid: String = response.headers()[HeaderConstants.UID].toString()

                    val intent = Intent(applicationContext, EnterprisesActivity::class.java)
                    intent.putExtra(HeaderConstants.ACCESS_TOKEN, token)
                    intent.putExtra(HeaderConstants.CLIENT, client)
                    intent.putExtra(HeaderConstants.UID, uid)
                    startActivity(intent)
                    finish()

                } else { //Exibindo texto de credenciais inválidas
                    loginCredentialsErrorTextView.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                sendMessage(getString(R.string.internet_failure_message))
            }
        })
    }

    fun sendMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setListeners() {
        loginButton.setOnClickListener(this)
    }
}