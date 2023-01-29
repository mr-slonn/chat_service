package service

import data.Message
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ChatServiceTest {


    @Before
    fun clear()
    {
        ChatService.clear()
    }


    @Test
    fun addMessage() {
        ChatService.addMessage(1, Message(1, "Привет", incoming = true))
        Assert.assertEquals(
            Message(id = 1, text = "Привет", isDeleted = false, read = false, incoming = true),
            ChatService.getChats(1)
        )
    }

    @Test
    fun deleteMessage() {
        ChatService.addMessage(1, Message(1, "Привет 1", incoming = true))
        ChatService.addMessage(1, Message(2, "Привет 2", incoming = true))
        ChatService.deleteMessage(1, 1)
        Assert.assertEquals(
            Message(id = 2, text = "Привет 2", isDeleted = false, read = false, incoming = true),
            ChatService.getChats(1)
        )
    }

    @Test
    fun deleteMessageLast() {
        ChatService.addMessage(1, Message(1, "Привет", incoming = true))
        ChatService.deleteMessage(1, 1)
        Assert.assertEquals(null, ChatService.getChatsMessage(1, 1))
    }

    @Test
    fun deleteChats() {
        ChatService.addMessage(1, Message(1, "Привет", incoming = true))
        Assert.assertTrue(ChatService.deleteChats(1))
    }

    @Test
    fun getLastMessage() {
        ChatService.addMessage(1, Message(1, "Привет", incoming = true))
        Assert.assertEquals(listOf("Привет"), ChatService.getLastMessage())
    }

    @Test
    fun getChats() {
        ChatService.addMessage(1, Message(1, "Привет", incoming = true))
        Assert.assertEquals(
            Message(id = 1, text = "Привет", isDeleted = false, read = false, incoming = true),
            ChatService.getChats(1)
        )
    }

    @Test
    fun getUnreadChatsCount() {
        ChatService.addMessage(1, Message(1, "Привет", incoming = true))
        Assert.assertEquals(1, ChatService.getUnreadChatsCount())
    }

    @Test
    fun getUnreadMessagesCount() {
        ChatService.addMessage(1, Message(1, "Привет", incoming = true))
        Assert.assertEquals(1, ChatService.getUnreadMessagesCount())
    }

    @Test
    fun getMessage() {

        ChatService.addMessage(1, Message(1, "Привет 1", incoming = true))
        ChatService.addMessage(1, Message(2, "Привет 2", incoming = true))
        ChatService.addMessage(1, Message(3, "Привет 3", incoming = true))
        ChatService.printChats()
        Assert.assertEquals(
            listOf(
                Message(id = 2, text = "Привет 2", isDeleted = false, read = true, incoming = true),
                Message(id = 3, text = "Привет 3", isDeleted = false, read = true, incoming = true)
            ), ChatService.getMessage(1, 2, 2)
        )
    }
}