package com.example.noodoeassignment.view

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
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
                    addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(newTimezone: CharSequence, start: Int, before: Int, count: Int) {}
                        override fun afterTextChanged(editable: Editable) {
                            updateUserViewModel.updateUserTimezone(editable.toString())
                            Log.d("updateUserTimezone", "updateUserTimezone timezone = $editable.toString()")
                        }
                    })
                }
            })

            timezoneSelection.observe(this@UpdateUserActivity, Observer { timezone ->
                timezoneInput.setText(timezone.toString())
            })

            isUpdatingServer.observe(this@UpdateUserActivity, Observer { isUpdating ->
                timezoneInput.isEnabled = !isUpdating
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