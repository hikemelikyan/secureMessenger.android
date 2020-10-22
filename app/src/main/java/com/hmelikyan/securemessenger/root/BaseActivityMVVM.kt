package com.hmelikyan.securemessenger.root

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.armboldmind.baggleapp.root.viewCommand.*
import com.hmelikyan.securemessenger.root.view_model.BaseViewModel

abstract class BaseActivityMVVM<VB : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    private lateinit var _binding: VB
    protected val mBinding: VB
        get() = _binding

    private lateinit var _viewModel: VM
    val mViewModel: VM
        get() = _viewModel

    protected abstract val layoutResId: Int

    protected abstract val viewModelType: Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)
        _viewModel = ViewModelProviders.of(this).get(viewModelType)
        _viewModel.viewCommands.observe(this, {
            processViewCommandGlobal(it)
        })
        linkView()
    }

    private fun processViewCommandGlobal(viewCommand: ViewCommand?) {
        when (viewCommand) {
            is NetworkErrorViewCommand -> onNetworkError()
            is ShowMessageViewCommand -> onError(viewCommand.resId)
            is ShowMessageTextViewCommand -> onError(viewCommand.errorMessage)
            is ShowLoadingViewCommand -> isMainLoading(true)
            is HideLoadingViewCommand -> isMainLoading(false)
            else -> processViewCommand(viewCommand)
        }
    }

    protected abstract fun processViewCommand(viewCommand: ViewCommand?)

    protected abstract fun linkView()

}