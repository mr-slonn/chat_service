import data.Message
import service.ChatService

fun main() {

    var messageId = 0

    ChatService.addMessage(1, Message(++messageId,"Привет", incoming = true))
    ChatService.addMessage(2, Message(++messageId,"Здравствуйте", incoming = true))
    ChatService.addMessage(1, Message(++messageId,"Как дела?", incoming = true))
    ChatService.addMessage(3, Message(++messageId,"Бяк Бяк", incoming = false))
    ChatService.addMessage(1, Message(++messageId,"Бяк Бяк", incoming = true))

    ChatService.printChats()

    ChatService.deleteMessage(2,2)
    ChatService.deleteMessage(1,3)
    println("after")
   ChatService.printChats()



    println("getLastMessage")
    println( ChatService.getLastMessage())
    println("getChats")
    println(  ChatService.getChats(1))
    println("getUnreadChatsCount")
    println(  ChatService.getUnreadChatsCount())
    println("getUnreadMessagesCount")
    println(  ChatService.getUnreadMessagesCount(3))

    println("getMessage")
    println(  ChatService.getMessage(1,1,3))
    ChatService.printChats()

    println("deleteChats")
    println(  ChatService.deleteChats(14))
    ChatService.printChats()




}