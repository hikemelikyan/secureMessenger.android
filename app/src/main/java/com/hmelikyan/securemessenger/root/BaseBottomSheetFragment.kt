package com.hmelikyan.securemessenger.root

import android.content.Context
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.hmelikyan.securemessenger.shared.extensions.getColor
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.hmelikyan.securemessenger.R

open class BaseBottomSheetFragment : BottomSheetDialogFragment(),
    IBaseView {
    private var mBaseActivity: BaseActivity? = null
    private var rootView: CoordinatorLayout? = null

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme_Root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) mBaseActivity = context
    }

    protected fun onCreateView(dialog: View): View? {
        rootView = CoordinatorLayout(requireContext())
        rootView?.layoutParams =
            CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.MATCH_PARENT
            )
        rootView?.addView(dialog)
        return rootView
    }

    override fun onNetworkError() {
        showSnackBar(resources.getString(R.string.network_error_message))
    }

    override fun onError(resId: Int) {
        onError(getString(resId))
    }

    override fun onError(message: String) {
        showSnackBar(message)
    }

    protected open fun showSnackBar(message: String) {
        Snackbar.make(rootView!!, message, Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(android.R.color.white)).show()
    }

    override fun setLightStatusBar() {
        mBaseActivity?.setLightStatusBar()
    }

    override fun clearLightStatusBar() {
        mBaseActivity?.clearLightStatusBar()
    }

    override fun isMainLoading(isLoading: Boolean) {

    }

    protected fun hideSoftInput() {
        mBaseActivity?.hideSoftInput()
    }

}