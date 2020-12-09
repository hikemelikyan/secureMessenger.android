package com.hmelikyan.securemessenger.ui

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hmelikyan.securemessenger.databinding.ActivitySplashScreenSecondBinding
import com.hmelikyan.securemessenger.root.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenSecondActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        clearLightStatusBar()
        val binding = ActivitySplashScreenSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashScreenSecondActivity, MainActivity::class.java))
            finish()
        }
    }
}