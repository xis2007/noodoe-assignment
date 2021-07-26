package com.example.noodoeassignment.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noodoeassignment.AppConstant
import com.example.noodoeassignment.R
import com.example.noodoeassignment.api.ApiInterface
import com.example.noodoeassignment.api.Resource
import com.example.noodoeassignment.model.entity.LoginResponse
import com.example.noodoeassignment.model.repository.UpdateUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UpdateUserViewModel(
    application: Application
): AndroidViewModel(application){
    private val updateUserRepository: UpdateUserRepository by lazy {
        UpdateUserRepository(ApiInterface.getInstance())
    }

    val email = MutableLiveData<String>("")
    val timezoneSelection = MutableLiveData<Int>(1)
    var timezonesList = MutableLiveData<ArrayList<Int>>()
    val isUpdatingServer = MutableLiveData<Boolean>(false)
    val updateHint = MutableLiveData<String>("")

    fun updateUserTimezone(timeZone: String) {
        Log.d("updateUserTimezone", "updateUserTimezone timezone = $timeZone")
        val loginResponse = AppConstant.loginResponse
        if(loginResponse != null) {
            updateToServer(timeZone, loginResponse)
        } else {
            updateHint.postValue(getApplication<Application>().getString(R.string.update_error_hint))
        }
    }

    private fun updateToServer(timeZone: String, loginResponse: LoginResponse) {
        isUpdatingServer.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val remoteResource = updateUserRepository.updateUser(
                loginResponse.objectId,
                timeZone,
                loginResponse.sessionToken
            )

            when (remoteResource.status) {
                Resource.Status.SUCCESS -> {
                    var response = remoteResource.data
                    Log.d("updateUserTimezone", "updateUserTimezone timezone SUCCESS = $response")
                    if(response != null) {
                        Log.d("updateUserTimezone", "updateUserTimezone timezone SUCCESS = ${response.updatedAt}")
                        updateHint.postValue(getApplication<Application>().getString(R.string.update_success_hint))

                    } else {
                        Log.d("updateUserTimezone", "updateUserTimezone timezone null")
                        updateHint.postValue(getApplication<Application>().getString(R.string.update_error_hint))
                    }
                    isUpdatingServer.postValue(false)
                }

                Resource.Status.ERROR -> {
                    Log.d("updateUserTimezone", "updateUserTimezone timezone ERROR ")
                    isUpdatingServer.postValue(false)
                }

                Resource.Status.LOADING -> {

                }
            }
        }
    }

    fun setLoginUserEmail() {
        val emailFromLogin = AppConstant.loginResponse?.reportEmail
        val emailString = "Email: $emailFromLogin"
        email.postValue(emailString)
    }

    fun setLoginTimeZone() {
        val timesZones = arrayListOf<Int>(1, 2, 3, 4, 5)
        val selectedTimeZone = AppConstant.loginResponse?.timezone
        val mapping = timesZones.find { it == selectedTimeZone }
        if(mapping == null){
            timesZones.add(0, selectedTimeZone!!)
        }
        timezonesList.postValue(timesZones)
    }
}