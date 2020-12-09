package com.hmelikyan.securemessenger.ui.senderProcessors

import com.hmelikyan.securemessenger.databinding.LayoutSenderBinding
import com.hmelikyan.securemessenger.shared.extensions.hide
import com.hmelikyan.securemessenger.shared.extensions.show
import com.hmelikyan.securemessenger.ui.MessageEncryptor
import java.util.*
import kotlin.math.pow

class User1Processor(private val binding: LayoutSenderBinding, helper: ConnectionHelper) :
    Processor(helper) {
    override val uId: String = UUID.randomUUID().toString()

    private lateinit var opponentId: String
    private lateinit var globalPair: Pair<Int, Int>
    private var opponentPublicKey: Int? = null
    private val myPrivateKey: Int = 784654

    init {
        binding.apply {
            startConnection.setOnClickListener {
                hideContent()
                helper.onConnectionRequested(uId)
                startConnection.text = "Waiting"
                startConnection.setOnClickListener(null)
            }
            send.setOnClickListener {
                if (messageArea.text.isNotEmpty()) {
                    val seanceKey: String = generateSeanceKey()
                    val encryptedMessageKeyPair =
                        opponentPublicKey?.let {
                            MessageEncryptor.encryptMessage(
                                messageArea.text.toString(),
                                seanceKey,
                                it
                            )
                        }
                    encryptedMessageKeyPair?.let {
                        helper.sendMessage(
                            opponentId,
                            encryptedMessageKeyPair.encryptedMessage,
                            encryptedMessageKeyPair.encryptedSeanceKey
                        )
                    }
                }
            }
        }
    }

    override fun processMessage(key: String, message: String) {

    }

    override fun onConnectionRequested(uId: String) {
        binding.startConnection.text = "Accept connection"
        binding.opponentInfo.text = "User with id $uId wants to connect with you"
        binding.opponentInfo.show()
        binding.startConnection.setOnClickListener {
            helper.onConnectionAccepted(this.uId, uId)
        }
    }

    override fun onConnectionAccepted(uId: String) {
        showContent()
        opponentId = uId
    }

    private fun showContent() {
        binding.apply {
            encryptedValue.show()
            messageArea.show()
            decryptedMessage.show()
            send.show()
            opponentInfo.hide()
            startConnection.hide()
        }
    }

    private fun hideContent() {
        binding.apply {
            encryptedValue.hide()
            messageArea.hide()
            decryptedMessage.hide()
            send.hide()
            opponentInfo.hide()
        }
    }

    private fun generateSeanceKey(): String {
        return UUID.randomUUID().toString()
    }

    override fun onPublicValuesReceived(pair: Pair<Int, Int>): Int {
        globalPair = pair
        return generatePrivateAndPublicKeys()
    }

    private fun generatePrivateAndPublicKeys(): Int {
        return (globalPair.first.toDouble()
            .pow(myPrivateKey.toDouble()) % globalPair.second).toInt()
    }

    override fun setOpponentPublicKey(key: Int) {
        opponentPublicKey = key
    }
}