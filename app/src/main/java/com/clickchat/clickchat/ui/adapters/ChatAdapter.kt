package com.clickchat.clickchat.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickchat.clickchat.R
import com.clickchat.clickchat.data.ChatEvent
import com.clickchat.clickchat.data.DateHeader
import com.clickchat.clickchat.databinding.ListItemChatRecvMessageBinding
import com.clickchat.clickchat.databinding.ListItemChatSentMessageBinding
import com.clickchat.clickchat.databinding.ListItemDateHeaderBinding
import com.clickchat.clickchat.utils.formatAsTime

class ChatAdapter(val list:MutableList<ChatEvent> , val mCurrentUid: String):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = { layout: Int ->
            LayoutInflater.from(parent.context)
                .inflate(layout, parent, false)
        }

        return when (viewType) {
            TEXT_MESSAGE_RECEIVED -> {
                MessageViewHolder(
                    inflate(R.layout.list_item_chat_recv_message)
                )
            }
            TEXT_MESSAGE_SENT -> {
                MessageViewHolder(
                    inflate(R.layout.list_item_chat_sent_message)
                )
            }
            DATE_HEADER -> {
                DateViewHolder(
                    inflate(R.layout.list_item_date_header)
                )
            }
            else -> {
                MessageViewHolder(
                    inflate(R.layout.list_item_chat_recv_message)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = list[position]) {
            is DateHeader -> {
                val v = ListItemDateHeaderBinding.bind(holder.itemView)

                v.textView.text = item.date

            }
            is com.clickchat.clickchat.data.Message -> {

                val t = getItemViewType(position)

                if(t == TEXT_MESSAGE_RECEIVED){
                    val v = ListItemChatRecvMessageBinding.bind(holder.itemView)

                    v.content.text = item.msg
                    v.time.text = item.sentAt.formatAsTime()
                }else{
                    val v = ListItemChatSentMessageBinding.bind(holder.itemView)
                    v.content.text = item.msg
                    v.time.text = item.sentAt.formatAsTime()
                }

            }
        }
    }

    override fun getItemCount() = list.size

    class DateViewHolder(view: View):RecyclerView.ViewHolder(view)

    class MessageViewHolder(view: View):RecyclerView.ViewHolder(view)



    override fun getItemViewType(position: Int): Int {

        return when(val event = list[position]) {
            is com.clickchat.clickchat.data.Message ->{
                if(event.senderId == mCurrentUid){
                    TEXT_MESSAGE_SENT
                }else{
                    TEXT_MESSAGE_RECEIVED
                }
            }

            is DateHeader ->{
                DATE_HEADER
            }

            else -> {
                UNSUPPORTED
            }
        }

    }

    companion object {

        private const val UNSUPPORTED = -1
        private const val TEXT_MESSAGE_RECEIVED = 0
        private const val TEXT_MESSAGE_SENT = 1
        private const val DATE_HEADER = 2

    }

}