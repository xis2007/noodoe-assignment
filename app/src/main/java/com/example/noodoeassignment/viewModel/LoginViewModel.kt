package com.example.noodoeassignment.viewModel

import android.app.Application
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.noodoeassignment.R
import java.util.regex.Pattern


class LoginViewModel(application: Application): AndroidViewModel(application){
    private val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    private var isEmailValid = false
    private var isPwValid = false

    val emailErrorHint = MutableLiveData<String?>("")
    val passwordErrorHint = MutableLiveData<String?>("")
    val isLogInButtonEnabled = MutableLiveData<Boolean>(false)

    fun verifyEmail(inputEmail: String) {
        isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
        val emailErrorHInt: String? = when (isEmailValid){
            true -> null
            false -> getApplication<Application>().getString(R.string.email_error_hint)
        }
        emailErrorHint.postValue(emailErrorHInt)
        verifyLoginButton()
    }

    fun verifyPassword(inputPassword: String) {
        isPwValid = Pattern.compile(PASSWORD_PATTERN).matcher(inputPassword).matches()
        val pwErrorHint: String? = when(isPwValid) {
            true -> null
            false -> getApplication<Application>().getString(R.string.pw_error_hint)
        }
        passwordErrorHint.postValue(pwErrorHint)
        verifyLoginButton()
    }

    private fun verifyLoginButton() {
        isLogInButtonEnabled.postValue(isEmailValid && isPwValid)
    }

    fun login(email: String, pw: String) {
        // TODO fire API
    }
}