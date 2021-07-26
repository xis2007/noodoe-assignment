package com.example.noodoeassignment.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

abstract class BaseActivity : AppCompatActivity() {
    var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initViews()
        initActions()
        rootView = window.decorView.rootView
    }

    abstract fun layoutId(): Int
    abstract fun initViews()
    abstract fun initActions()
}