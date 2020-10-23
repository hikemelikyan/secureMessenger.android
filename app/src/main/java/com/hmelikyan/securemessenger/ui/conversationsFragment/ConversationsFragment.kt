package com.hmelikyan.securemessenger.ui.conversationsFragment

import com.armboldmind.baggleapp.root.viewCommand.ViewCommand
import com.hmelikyan.securemessenger.R
import com.hmelikyan.securemessenger.databinding.FragmentConversationsBinding
import com.hmelikyan.securemessenger.root.BaseFragmentMVVM
import com.hmelikyan.securemessenger.ui.conversationsFragment.viewModel.ConversationsViewModel

class ConversationsFragment :BaseFragmentMVVM<FragmentConversationsBinding,ConversationsViewModel>(){
    override val layoutResId: Int
        get() = R.layout.fragment_conversations

    override val viewModelType: Class<ConversationsViewModel>
        get() = ConversationsViewModel::class.java

    override fun linkView() {

    }

    override fun processViewCommand(viewCommand: ViewCommand?) {

    }

}