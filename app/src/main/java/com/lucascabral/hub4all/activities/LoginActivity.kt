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
import kotlinx.android.synthetic.main.activity_login.*
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

    fun handlerLogin() {

        val email = loginEmailEdit.text.toString()
        val password = loginPasswordEdit.text.toString()

        if (emailValidation(email)) return

        if (passwordValidation(password)) return

        val remote = RetrofitClient.createService(LoginService::class.java)

        val call: Call<LoginResponse> = remote.login(email, password)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {

                if (response.isSuccessful) {

                    loginProgressBar.visibility = View.VISIBLE

                    val intent = Intent(applicationContext, EnterprisesActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(
                        applicationContext,
                        "Sucesso ao fazer Login",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginCredentialsErrorTextView.visibility = View.VISIBLE
            }
        })
    }

    fun passwordValidation(password: String): Boolean{

        if (password.isEmpty()){
            //loginPasswordEdit.error[R.string.password_empty_error_message]
            //loginPasswordEdit.requestFocus()
                Toast.makeText(applicationContext, "senha", Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }

    fun emailValidation(email: String): Boolean {

        if (email.isEmpty()){
            //loginEmailEdit.error[R.string.email_empty_error_message]
            //loginEmailEdit.requestFocus()
            Toast.makeText(applicationContext, "empty Email", Toast.LENGTH_LONG).show()
            return true
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //loginEmailEdit.error[R.string.email_invalid_error_message]
            //loginEmailEdit.requestFocus()
            Toast.makeText(applicationContext, "invalid email", Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }

    private fun setListeners() {
        loginButton.setOnClickListener(this)
    }
}