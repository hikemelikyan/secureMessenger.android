package com.hmelikyan.securemessenger.ui

object MessageEncryptor {

    fun encryptMessage(message: String, seanceKey: String, opponentPublicKey: Int) : Result {

    }

    private fun encryptSeanceKey(seanceKey: String, opponentPublicKey: String){

    }

    data class Result(
        val encryptedMessage:String,
        val encryptedSeanceKey: String
    )

}