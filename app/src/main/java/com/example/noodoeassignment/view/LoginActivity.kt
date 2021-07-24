package com.example.noodoeassignment.view

import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.noodoeassignment.R
import com.example.noodoeassignment.base.BaseActivity
import com.example.noodoeassignment.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViews() {
        emailEditText.addTextChangedListener {
            loginViewModel.verifyEmail(it.toString())
        }

        pwEditText.addTextChangedListener {
            loginViewModel.verifyPassword(it.toString())
        }

        loginButton.setOnClickListener {
            loginViewModel.login(
                emailEditText.text.toString(),
                pwEditText.text.toString()
            )
        }
    }

    override fun initActions() {
        loginViewModel.apply {
            emailErrorHint.observe(this@LoginActivity, Observer { emailErrorHint ->
                emailInputLayout.error = emailErrorHint
            })

            passwordErrorHint.observe(this@LoginActivity, Observer { pwErrorHint ->
                pwInputLayout.error = pwErrorHint
            })

            isLogInButtonEnabled.observe(this@LoginActivity, Observer { isEnabled ->
                loginButton.isEnabled = isEnabled
            })
        }
    }
}