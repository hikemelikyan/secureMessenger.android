package com.hmelikyan.securemessenger.root.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.armboldmind.baggleapp.root.viewCommand.ViewCommand
import com.hmelikyan.securemessenger.root.handlers.ResponseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(application: Application) : AndroidViewModel(application), ResponseHandler, CoroutineScope {

    private val jobRegister = ArrayList<Job>()

    protected val _viewCommandsLiveData: MutableLiveData<ViewCommand> = MutableLiveData()
    val viewCommands: MutableLiveData<ViewCommand>
        get() = _viewCommandsLiveData

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO

    override val target: BaseViewModel
        get() = this

    override fun registerScope(job: Job) {
        jobRegister.add(job)
    }

    override fun onCleared() {
        super.onCleared()
        cancelAllRequests()
    }

    private fun cancelAllRequests() {
        jobRegister.forEach {
            it.cancel()
        }
    }

}