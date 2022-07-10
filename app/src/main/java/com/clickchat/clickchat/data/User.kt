package com.clickchat.clickchat.data

data class User(
    val name: String,
    val imageUrl: String,
    val thumbImage: String,
    val deviceToken: String,
    val status:String,
    val onlineStatus: String,
    val uid: String
) {
    constructor():this("","","","","","","")

    constructor(name: String,imageUrl: String,thumbImage: String,uid: String) :this(
        name,
        imageUrl,
        thumbImage,
        "",
        "Hey there I am using WhatsApp !!!",
        "",
        uid
        )
}
