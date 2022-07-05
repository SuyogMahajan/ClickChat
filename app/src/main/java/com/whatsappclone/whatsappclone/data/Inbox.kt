package com.whatsappclone.whatsappclone.data

import java.util.*

class Inbox(
    var msg:String,
    var from:String,
    var name:String,
    var image:String,
    var time:Date = Date(),
    var count:Int

) {
   constructor():this("","","","", Date(),0)

}