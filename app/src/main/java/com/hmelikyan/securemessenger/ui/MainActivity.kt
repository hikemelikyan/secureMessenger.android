package com.hmelikyan.securemessenger.ui

import android.os.Bundle
import com.hmelikyan.securemessenger.R
import com.hmelikyan.securemessenger.root.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}