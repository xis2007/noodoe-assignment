package com.example.noodoeassignment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.noodoeassignment.R
import java.util.regex.Pattern


class UpdateUserViewModel(application: Application): AndroidViewModel(application){
    val email = MutableLiveData<String>("")
    val timezoneSelection = MutableLiveData<String>("")
    val isUpdatingServer = MutableLiveData<Boolean>(false)

    fun updateUserTimezone(timeZone: String) {
        isUpdatingServer.postValue(true)
        // TODO update timezone on server
    }

    fun setUserEmail(newEmail: String) {
        val emailString = "Email: $newEmail"
        email.postValue(emailString)
    }
}