package com.example.noodoeassignment.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.noodoeassignment.R
import com.example.noodoeassignment.base.BaseActivity
import com.example.noodoeassignment.viewModel.UpdateUserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_update_user.*


class UpdateUserActivity : BaseActivity() {
    private val updateUserViewModel: UpdateUserViewModel by viewModels()

    override fun layoutId(): Int {
        return R.layout.activity_update_user
    }

    override fun initViews() {
        val items = listOf("Timezone1", "Timezone12", "Timezone3", "Timezone4")
        val adapter = ArrayAdapter(this@UpdateUserActivity, R.layout.item_timezone, items)
        timezoneInput.apply{
            setAdapter(adapter)
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(newTimezone: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    updateUserViewModel.updateUserTimezone("", editable.toString(), "session ID") // TODO use real data
                }
            })
        }

        updateUserViewModel.apply {
            email.observe(this@UpdateUserActivity, Observer { email ->
                emailText.text = email
            })

            timezoneSelection.observe(this@UpdateUserActivity, Observer { timezone ->

            })

            isUpdatingServer.observe(this@UpdateUserActivity, Observer { isUpdating ->
                timezoneInput.isEnabled = !isUpdating
            })
        }
    }

    override fun initActions() {
        // TODO get user data from Login and set email
        updateUserViewModel.setUserEmail("test2@qq.com")

    }
}