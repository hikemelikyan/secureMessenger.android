package com.hmelikyan.securemessenger.ui

import android.os.Bundle
import com.hmelikyan.securemessenger.databinding.ActivityMainBinding
import com.hmelikyan.securemessenger.root.BaseActivity
import com.hmelikyan.securemessenger.ui.senderProcessors.Processor
import com.hmelikyan.securemessenger.ui.senderProcessors.User1Processor
import com.hmelikyan.securemessenger.ui.senderProcessors.User2Processor

class MainActivity : BaseActivity(), Processor.ConnectionHelper {

    private val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var processors: Map<String, Processor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        clearLightStatusBar()
        val processor1 = User1Processor(mBinding.user1, this)
        val processor2 = User2Processor(mBinding.user2, this)
        processors = mapOf(
            processor1.uId to processor1,
            processor2.uId to processor2
        )
    }

    override fun onConnectionRequested(uId: String) {
        processors.forEach {
            if (it.key != uId) {
                it.value.onConnectionRequested(uId)
            }
        }
    }

    override fun onConnectionAccepted(uId: String, opponentUID: String) {
        val randomTuple = generateRandomTuple()

        val sender = processors[uId]
        val receiver = processors[opponentUID]

        sender?.onConnectionAccepted(opponentUID)
        receiver?.onConnectionAccepted(uId)

        val senderPublicKey: Int? = sender?.onPublicValuesReceived(randomTuple)
        val receiverPublicKey: Int? = receiver?.onPublicValuesReceived(randomTuple)

        receiverPublicKey?.let { sender?.setOpponentPublicKey(it) }
        senderPublicKey?.let { receiver?.setOpponentPublicKey(it) }
    }

    private fun generateRandomTuple(): Pair<Int, Int> {
        return Pair(
            System.currentTimeMillis().toInt(),
            System.currentTimeMillis().toInt() xor System.currentTimeMillis().toInt()
        )
    }

    override fun sendMessage(uId: String, message: String, key: String) {
        processors.forEach {
            if (it.key == uId) {
                it.value.processMessage(message, key)
            }
        }
    }


}