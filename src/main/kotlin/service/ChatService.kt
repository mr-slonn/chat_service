package service

import data.Chat
import data.Message

object ChatService {

    private val chats = mutableMapOf<Int, Chat>()

    fun clear() {
        chats.clear()
    }

    //4. Создать новое сообщение.
    fun addMessage(userId: Int, message: Message) {
        chats.getOrPut(userId) { Chat() }.messages += message
    }

    // Удалить сообщение. При удалении последнего сообщения в чате весь чат удаляется.
    fun deleteMessage(userId: Int, messageId: Int): Boolean {

        return chats[userId]?.messages?.find { it.id == messageId }?.also {
            it.isDeleted = true
            if ((chats[userId]?.messages?.count { message: Message -> !message.isDeleted } ?: 0) == 0) {
                deleteChats(userId)
            }
        } != null

    }

    //Удалить чат, т. е. целиком удалить всю переписку.
    fun deleteChats(userId: Int): Boolean {
        return if (chats[userId] != null) {
            chats.remove(userId)
            true
        } else {
            false
        }
    }


    fun getLastMessage() =
        chats.values.map { it.messages.lastOrNull { message: Message -> !message.isDeleted }?.text ?: "Нет сообщений" }

    //Получить список чатов (например, service.getChats), где в каждом есть последнее сообщение. Если нет, то пишется «нет сообщений».
    fun getChats(userId: Int) =
        chats[userId]?.messages?.lastOrNull { !it.isDeleted } ?: "Нет сообщений"

    fun getChatsMessage(userId: Int, messageId: Int) =
        chats[userId]?.messages?.find { it.id == messageId }

    //Видеть, сколько чатов не прочитано (например, service.getUnreadChatsCount). В каждом из таких чатов есть хотя бы одно непрочитанное сообщение.
    fun getUnreadChatsCount()
            = chats.values.count { it.messages.any { message: Message -> !message.isDeleted && !message.read } }

    //сделал для себя Видеть, сколько сообщений не прочитано
    fun getUnreadMessagesCount(userId: Int? = null): Int {
        var count = 0
        if (userId == null) {
            chats.values.forEach { chat: Chat -> count += chat.messages.count { message: Message -> !message.isDeleted && !message.read && message.incoming } }
        } else {
            count = chats[userId]?.messages?.count { !it.isDeleted && !it.read && it.incoming } ?: 0
        }

        return count
    }

    //Получить список сообщений из чата, указав:
    //ID чата;
    //ID последнего сообщения, начиная с которого нужно подгрузить более новые;
    //количество сообщений. После того как вызвана эта функция, все отданные сообщения автоматически считаются прочитанными.
    fun getMessage(userId: Int, lastMessageId: Int, countMessage: Int) =
        chats[userId]?.messages?.asSequence()?.filter { it.id >= lastMessageId && !it.isDeleted }?.take(countMessage)
            ?.onEach { it.read = true }?.toList()

    fun printChats() {
        println(chats)
    }
}