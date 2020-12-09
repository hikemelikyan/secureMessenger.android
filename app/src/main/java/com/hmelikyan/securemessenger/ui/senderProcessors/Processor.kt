package com.hmelikyan.securemessenger.ui.senderProcessors

abstract class Processor(protected val helper: ConnectionHelper) {

    abstract val uId: String

    abstract fun processMessage(key: String, message: String)

    abstract fun onConnectionRequested(uId: String)

    abstract fun onConnectionAccepted(uId: String)

    abstract fun onPublicValuesReceived(pair:Pair<Int,Int>):Int

    abstract fun setOpponentPublicKey(key:Int)

    interface ConnectionHelper {

        fun onConnectionRequested(uId: String)

        fun onConnectionAccepted(uId: String, opponentUID: String)

        fun sendMessage(uId: String, message: String, key: String)
    }

}