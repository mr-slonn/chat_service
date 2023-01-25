import data.Message
import service.ChatService

fun main(args: Array<String>) {

    var messageId = 0

    ChatService.addMessage(1, Message(++messageId,"Привет"))
    ChatService.addMessage(2, Message(++messageId,"Здароффф!"))

    ChatService.printChats()

    ChatService.deleteMessage(1,1)

    ChatService.printChats()


    ChatService.addMessage(1, Message(++messageId,"Как дела?"))

    println( ChatService.getLastMessage())



}