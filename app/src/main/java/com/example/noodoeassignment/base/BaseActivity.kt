package com.example.noodoeassignment.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initViews()
        initActions()
    }

    abstract fun layoutId(): Int
    abstract fun initViews()
    abstract fun initActions()
}