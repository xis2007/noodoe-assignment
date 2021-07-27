package com.example.noodoeassignment.view

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.noodoeassignment.R
import com.example.noodoeassignment.base.BaseActivity
import com.example.noodoeassignment.viewModel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_update_user.*

class LoginActivity : BaseActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViews() {
        containerLogin.setOnClickListener {
            clearFocus()
        }

        emailEditText.setOnFocusChangeListener { v, hasFocus ->
            loginViewModel.verifyEmail((v as EditText).text.toString(), hasFocus)
        }

        pwEditText.addTextChangedListener {
            loginViewModel.verifyPasswordLength(it.toString())
        }

        loginButton.setOnClickListener {
            loginViewModel.checkValidityAndLogin(
                emailEditText.text.toString(),
                pwEditText.text.toString()
            )
        }

        loginViewModel.apply {
            emailErrorHint.observe(this@LoginActivity, { emailErrorHint ->
                emailInputLayout.error = emailErrorHint
            })

            passwordErrorHint.observe(this@LoginActivity, { pwErrorHint ->
                pwInputLayout.error = pwErrorHint
            })

            isLogInButtonEnabled.observe(this@LoginActivity, { isEnabled ->
                loginButton.isEnabled = isEnabled
            })

            isLoginSuccessful.observe(this@LoginActivity, { isSuccessful ->
                when(isSuccessful) {
                    true -> {
                        startActivity(
                            Intent(this@LoginActivity, UpdateUserActivity::class.java)
                        ).also {
                            finish()
                        }
                    }

                    false -> {
                        Snackbar.make(
                            containerLogin,
                            getString(R.string.login_incorrect_id_and_pw_hint),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            })

            isLoading.observe(this@LoginActivity, { visibility ->
                loginProgressBar.visibility = visibility
            })
        }
    }

    override fun initActions() {

    }

    private fun clearFocus() {
        emailEditText.clearFocus()
        pwEditText.clearFocus()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        rootView?.apply {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }
}