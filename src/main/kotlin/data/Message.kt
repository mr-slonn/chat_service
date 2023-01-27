package data

data class Message(val id:Int, val text:String, var isDeleted:Boolean=false, var read:Boolean=false, val incoming:Boolean)
