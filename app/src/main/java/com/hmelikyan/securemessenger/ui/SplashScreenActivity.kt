package com.hmelikyan.securemessenger.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hmelikyan.securemessenger.root.BaseActivity

class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = resources.getColor(android.R.color.transparent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(android.R.color.transparent)
        }
        val intent = Intent(this, SplashScreenSecondActivity::class.java)
        startActivity(intent)
        finish()
    }
}