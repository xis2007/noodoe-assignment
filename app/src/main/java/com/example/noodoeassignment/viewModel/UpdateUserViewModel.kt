package com.example.noodoeassignment.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noodoeassignment.api.ApiInterface
import com.example.noodoeassignment.api.Resource
import com.example.noodoeassignment.model.repository.LoginRepository
import com.example.noodoeassignment.model.repository.UpdateUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UpdateUserViewModel(
    application: Application
): AndroidViewModel(application){
    private val updateUserRepository: UpdateUserRepository  by lazy {
        UpdateUserRepository(ApiInterface.getInstance())
    }

    val email = MutableLiveData<String>("")
    val timezoneSelection = MutableLiveData<String>("")
    val isUpdatingServer = MutableLiveData<Boolean>(false)

    fun updateUserTimezone(userObjectId: String, timeZone: String, sessionId:String) {
        isUpdatingServer.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val remoteResource = updateUserRepository.updateUser(userObjectId, timeZone, sessionId)
            when (remoteResource.status) {
                Resource.Status.SUCCESS -> {
                    var loginResponse = remoteResource.data
                    if(loginResponse != null) {

                    } else {

                    }
                }

                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }
    }

    fun setUserEmail(newEmail: String) {
        val emailString = "Email: $newEmail"
        email.postValue(emailString)
    }
}