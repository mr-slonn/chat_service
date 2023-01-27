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

    fun deleteChats(userId: Int):Boolean {
        return if (chats[userId] != null) {
            chats.remove(userId)
            true
        } else {
            false
        }


    }

//    fun getLastMessage():List<String>
//    {
//        chats.values.map { it.messages.lastOrNull{ message: Message ->  !message.isDeleted}?.text ?:"Нет сообщений"}
//    }

    fun isDeleted(message: Message) = message.isDeleted
    fun isNotDeleted(message: Message) = !message.isDeleted

    fun getLastMessage() =
        chats.values.map { it.messages.lastOrNull { message: Message -> !message.isDeleted }?.text ?: "Нет сообщений" }

    fun getChats(userId: Int)   =
        chats[userId]?.messages?.lastOrNull { !it.isDeleted } ?:"Нет сообщений"


    //Видеть, сколько чатов не прочитано (например, service.getUnreadChatsCount). В каждом из таких чатов есть хотя бы одно непрочитанное сообщение.
    fun getUnreadChatsCount()
    //= chats.values.count { it -> it.messages.any { !it.read && it.isDeleted } }
    = chats.values.count { it.messages.any { message: Message -> !message.isDeleted && !message.read } }

    //сделал для себя Видеть, сколько сообщений не прочитано
    fun getUnreadMessagesCount(userId: Int?=null):Int {
      var count = 0
        if (userId==null) {
            chats.values.forEach { chat: Chat -> count += chat.messages.count { message: Message -> !message.isDeleted && !message.read && message.incoming } }
        }
        else
        {
            count = chats[userId]?.messages?.count { !it.isDeleted && !it.read && it.incoming }?:0
        }

      return count
    }

    //Получить список сообщений из чата, указав:
    //ID чата;
    //ID последнего сообщения, начиная с которого нужно подгрузить более новые;
    //количество сообщений. После того как вызвана эта функция, все отданные сообщения автоматически считаются прочитанными.

//    fun getMessage(userId:Int, lastMessageId:Int, countMessage: Int)
//   =  chats[userId]?.messages?.filter { it.id>=lastMessageId && !it.isDeleted}?.take(countMessage)?.forEach { message: Message -> message.read=true }

    fun getMessage(userId:Int, lastMessageId:Int, countMessage: Int):MutableList<Message>
    {
        val messages:MutableList<Message> = chats[userId]?.messages?.filter { it.id>=lastMessageId && !it.isDeleted}?.take(countMessage) as MutableList<Message>
        messages.forEach { message: Message -> message.read=true }
        return messages
    }

    fun printChats() {
        println(chats)
    }
}