package com.example.noodoeassignment.view

import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.noodoeassignment.R
import com.example.noodoeassignment.base.BaseActivity
import com.example.noodoeassignment.viewModel.UpdateUserViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_update_user.*


class UpdateUserActivity : BaseActivity() {
    private val updateUserViewModel: UpdateUserViewModel by viewModels()

    override fun layoutId(): Int {
        return R.layout.activity_update_user
    }

    override fun initViews() {
        updateUserViewModel.apply {
            email.observe(this@UpdateUserActivity, Observer { email ->
                emailText.text = email
            })

            timezonesList.observe(this@UpdateUserActivity, Observer { timezones ->
                val adapter = ArrayAdapter(this@UpdateUserActivity, R.layout.item_timezone, timezones)
                timezoneInput.apply{
                    setAdapter(adapter)
                    setText(timezones[0].toString(), false)

                    addTextChangedListener {
                        updateUserViewModel.updateUserTimezone(it.toString())
                    }
                }
            })

            timezoneSelection.observe(this@UpdateUserActivity, Observer { timezone ->
                timezoneInput.setText(timezone.toString())
            })

            isLoading.observe(this@UpdateUserActivity, Observer { visibility ->
                updateUserProgressBar.visibility = visibility
            })

            isInputEnabled.observe(this@UpdateUserActivity, Observer { isEnabled ->
                timezoneInput.isEnabled = isEnabled
            })


            updateHint.observe(this@UpdateUserActivity, Observer { updateErrorHint ->
                if(updateErrorHint.isNotEmpty()) {
                    Snackbar.make(
                        containerUpdateUser,
                        updateErrorHint,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    override fun initActions() {
        updateUserViewModel.setLoginUserEmail()
        updateUserViewModel.setLoginTimeZone()
    }
}