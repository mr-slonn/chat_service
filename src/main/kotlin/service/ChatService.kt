package service

import data.Chat
import data.Message

object ChatService {

    private val chats = mutableMapOf<Int, Chat>()

    fun addMessage(userId: Int, message: Message) {
//        if (chats.containsKey(userId))
//        {
//            chats[userId]?.messages?.add(message)
//        }
//        else
//        {
//            val chat = Chat()
//            chat.messages+=message
//            chats[userId]=chat
//        }

        chats.getOrPut(userId) { Chat() }.messages += message
    }

    fun deleteMessage(userId: Int, messageId: Int): Boolean {
//        val message = chats[userId]?.messages?.find { it.id==messageId }
//            message?.isDeleted=true
//        return message!=null
        return chats[userId]?.messages?.find { it.id == messageId }?.also { it.isDeleted = true } != null
    }

//    fun getLastMessage():List<String>
//    {
//        chats.values.map { it.messages.lastOrNull{ message: Message ->  !message.isDeleted}?.text ?:"Нет сообщений"}
//    }

    fun getLastMessage() =
        chats.values.map { it.messages.lastOrNull{ message: Message ->  !message.isDeleted}?.text ?:"Нет сообщений"}


    fun printChats() {
        println(chats)
    }
}