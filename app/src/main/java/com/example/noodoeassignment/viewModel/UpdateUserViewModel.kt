package com.example.noodoeassignment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.noodoeassignment.R
import java.util.regex.Pattern


class UpdateUserViewModel(application: Application): AndroidViewModel(application){
    val emailText = MutableLiveData<String>("")
    val timezoneSelection = MutableLiveData<String>("")

    private fun updateUser(timeZone: String) {

    }

    fun setUserEmailText(email: String) {
        val emailString = "Email: $email"
        emailText.postValue(emailString)
    }
}