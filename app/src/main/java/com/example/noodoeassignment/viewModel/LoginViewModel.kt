package com.example.noodoeassignment.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noodoeassignment.App
import com.example.noodoeassignment.AppConstant
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
        LoginRepository(App.retrofitService)
    }

    private var isEmailValid = false
    private var isPwValid = false
    private var isPwLengthValid = false

    val emailErrorHint = MutableLiveData<String?>("")
    val passwordErrorHint = MutableLiveData<String?>("")
    val isLogInButtonEnabled = MutableLiveData<Boolean>(false)
    val isLoginSuccessful = MutableLiveData<Boolean?>(null)

    val isLoading = MutableLiveData<Int>(View.INVISIBLE)

    fun verifyEmail(inputEmail: String, hasFocus: Boolean) {
        if(!hasFocus) {
            isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()
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
        passwordErrorHint.postValue(null)
        verifyLoginButton()
    }

    private fun verifyPassword(inputPassword: String) {
        val containsNumber = Pattern.compile(".*\\d.*").matcher(inputPassword).matches()
        val containsAlphabet = Pattern.compile(".*[a-z].*").matcher(inputPassword).matches()
        isPwValid = containsNumber && containsAlphabet
        val pwErrorHint: String? = when(isPwValid) {
            true -> null
            false -> getApplication<Application>().getString(R.string.pw_error_hint)
        }
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
        isLoading.postValue(View.VISIBLE)
        isLogInButtonEnabled.postValue(false)

        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = loginRepository.login(email, pw)

            when (loginResponse.status) {
                Resource.Status.SUCCESS -> {
                    val data = loginResponse.data
                    if(data != null) {
                        AppConstant.loginResponse = data
                        isLoginSuccessful.postValue(true)
                    } else {
                        isLoginSuccessful.postValue(false)
                    }
                    isLoading.postValue(View.INVISIBLE)
                    isLogInButtonEnabled.postValue(true)
                }

                Resource.Status.ERROR -> {
                    isLoginSuccessful.postValue(false)
                    isLoading.postValue(View.INVISIBLE)
                    isLogInButtonEnabled.postValue(true)
                }

                Resource.Status.LOADING -> {

                }
            }
        }
    }
}