package com.hmelikyan.securemessenger.root

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.armboldmind.baggleapp.root.viewCommand.*
import com.hmelikyan.securemessenger.root.view_model.BaseViewModel
import com.google.gson.Gson

abstract class BaseFragmentMVVM<VB : ViewDataBinding, VM : BaseViewModel> : BaseFragment() {

    private var mView: View? = null

    private lateinit var _binding: VB
    protected val mBinding: VB
        get() = _binding

    private lateinit var _viewModel: VM
    val mViewModel: VM
        get() = _viewModel

    protected abstract val layoutResId: Int

    protected abstract val viewModelType: Class<VM>

    private val logger: Gson by lazy { Gson() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProviders.of(this).get(viewModelType)
        _viewModel.viewCommands.observe(this, Observer {
            processViewCommandGlobal(it)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
            mView = _binding.root
            linkView()
        }
        return mView
    }

    protected abstract fun linkView()

    private fun processViewCommandGlobal(viewCommand: ViewCommand?) {
        viewCommand?.let {
            Log.d("Response", logger.toJson(viewCommand::class.java.name))
        }

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
}