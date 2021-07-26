package com.example.noodoeassignment.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noodoeassignment.R
import com.example.noodoeassignment.api.ApiInterface
import com.example.noodoeassignment.api.Resource
import com.example.noodoeassignment.model.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class LoginViewModel(
    application: Application
): AndroidViewModel(application){
    private val loginRepository: LoginRepository by lazy {
        LoginRepository(ApiInterface.getInstance())
    }

    private val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    private var isEmailValid = false
    private var isPwValid = false
    private var isPwLengthValid = false

    val emailErrorHint = MutableLiveData<String?>("")
    val passwordErrorHint = MutableLiveData<String?>("")
    val isLogInButtonEnabled = MutableLiveData<Boolean>(false)
    val isLoginSuccessful = MutableLiveData<Boolean?>(null)

    fun verifyEmail(inputEmail: String, hasFocus: Boolean) {
        if(!hasFocus) {
            isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
            val emailErrorHint: String? = when (isEmailValid){
                true -> null
                false -> getApplication<Application>().getString(R.string.email_error_hint)
            }

            this.emailErrorHint.postValue(emailErrorHint)
            verifyLoginButton()
        }
    }

    fun verifyPasswordLength(inputPassword: String) {
        isPwLengthValid = inputPassword.length >= 8
        verifyLoginButton()
    }

    fun verifyPassword(inputPassword: String) {

        isPwValid = Pattern.compile("[a-zA-Z0-9]*").matcher(inputPassword).matches()

        val pwErrorHint: String? = when(isPwValid) {
            true -> null
            false -> getApplication<Application>().getString(R.string.pw_error_hint)
        }
        Log.d("inputPassword", "inputPassword = $inputPassword is $isPwValid")
        passwordErrorHint.postValue(pwErrorHint)
    }

    private fun verifyLoginButton() {
        isLogInButtonEnabled.postValue(isEmailValid && isPwLengthValid)
    }

    fun checkValidityAndLogin(email: String, pw: String) {
        verifyPassword(pw)
        if(isPwValid) {
            login(email, pw)
        }
    }

    private fun login(email: String, pw: String) {
        Log.d("login", "loginnnnnnn = $email & $pw")
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = loginRepository.login(email, pw)

            when (loginResponse.status) {
                Resource.Status.SUCCESS -> {
                    var loginResponse = loginResponse.data
                    Log.d("login", "loginnnnnnn = SUCCESS $loginResponse")

                    if(loginResponse != null) {
                        isLoginSuccessful.postValue(true)
                    } else {
                        isLoginSuccessful.postValue(false)
                    }
                }

                Resource.Status.ERROR -> {
                    Log.d("login", "loginnnnnnn = ERROR ${loginResponse.apiError?.errorCode} with message = ${loginResponse.apiError?.errorMessage}")
                    isLoginSuccessful.postValue(false)
                }

                Resource.Status.LOADING -> {
                    Log.d("login", "loginnnnnnn = LOADING ${loginResponse.apiError?.errorCode}")
                }
            }
        }
    }
}