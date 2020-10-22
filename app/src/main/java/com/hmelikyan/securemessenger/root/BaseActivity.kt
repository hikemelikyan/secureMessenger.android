package com.hmelikyan.securemessenger.root

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.hmelikyan.securemessenger.shared.extensions.getStyledAttribute
import com.google.android.material.snackbar.Snackbar
import com.hmelikyan.securemessenger.R
import com.hmelikyan.securemessenger.shared.extensions.AppMode
import com.hmelikyan.securemessenger.shared.extensions.getAppMode


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), IBaseView {

    private var isLightStatusBar: Boolean? = null

    val notificationsLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (getAppMode() == AppMode.DARK) {
            clearLightStatusBar()
        } else {
            setLightStatusBar()
        }
        super.onCreate(savedInstanceState)
    }

    override fun setLightStatusBar() {
        isLightStatusBar = true
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else -> {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                )
            }
        }
    }

    override fun clearLightStatusBar() {
        isLightStatusBar = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    override fun isMainLoading(isLoading: Boolean) {}

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    open fun requestPermissionsSafely(permissions: Array<String>?, requestCode: Int?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode!!)
        }
    }

    override fun onError(@StringRes resId: Int) {
        onError(resources.getString(resId))
    }

    override fun onError(message: String) {
        showSnackBar(message)
    }

    override fun onNetworkError() {
        showSnackBar(resources.getString(R.string.network_error_message))
    }

    fun hideSoftInput() {
        val view: View? = currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(getStyledAttribute(R.attr.styledSnackBarColor))
            .setTextColor(getStyledAttribute(R.attr.styledBackgroundColor))
            .show()
    }

    open fun navigateToRoot(forceInvalidate:Boolean) {}
}
