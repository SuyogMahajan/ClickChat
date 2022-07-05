package com.whatsappclone.whatsappclone.ui.adapters

import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.whatsappclone.whatsappclone.R
import com.whatsappclone.whatsappclone.data.ChatEvent
import com.whatsappclone.whatsappclone.data.DateHeader
import com.whatsappclone.whatsappclone.databinding.ListItemChatRecvMessageBinding
import com.whatsappclone.whatsappclone.databinding.ListItemChatSentMessageBinding
import com.whatsappclone.whatsappclone.databinding.ListItemDateHeaderBinding
import com.whatsappclone.whatsappclone.utils.formatAsTime

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
            is com.whatsappclone.whatsappclone.data.Message -> {

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
            is com.whatsappclone.whatsappclone.data.Message ->{
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