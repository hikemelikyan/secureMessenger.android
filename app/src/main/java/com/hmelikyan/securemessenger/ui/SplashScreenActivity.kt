package com.hmelikyan.securemessenger.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.hmelikyan.securemessenger.root.BaseActivity

class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        clearLightStatusBar()
        val intent = Intent(this, SplashScreenSecondActivity::class.java)
        startActivity(intent)
        finish()
    }
}