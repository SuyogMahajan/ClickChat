package com.whatsappclone.whatsappclone.data

import java.util.*

class Inbox(
    val msg:String,
    val from:String,
    val name:String,
    val image:String,
    val time:Date,
    val count:Int

) {
   constructor():this("","","","", Date(),0)

}