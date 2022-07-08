package com.whatsappclone.whatsappclone.ui.actvities.ViewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.whatsappclone.whatsappclone.R
import com.whatsappclone.whatsappclone.data.Inbox
import com.whatsappclone.whatsappclone.data.User
import com.whatsappclone.whatsappclone.databinding.ListItem2Binding
import com.whatsappclone.whatsappclone.databinding.ListItemBinding
import com.whatsappclone.whatsappclone.utils.formatAsListItem
import com.whatsappclone.whatsappclone.utils.formatAsTime

class chatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: ListItemBinding

    fun bind(inbox: Inbox, onClick : (name:String, photo:String, id:String) -> Unit) = with(itemView) {

        binding = ListItemBinding.bind(itemView)

        binding.userNameTextView.text = inbox.name
        binding.statusTv.text = inbox.msg

        binding.countTV.visibility = if(inbox.count > 0) View.VISIBLE else View.GONE
        binding.countTV.text = inbox.count.toString()

        binding.timeTv.text = inbox.time.formatAsTime()

        Picasso.get()
            .load(inbox.image)
            .placeholder(R.drawable.profile_dummy)
            .error(R.drawable.profile_dummy)
            .into(binding.profilePic)

        setOnClickListener {
            onClick.invoke(inbox.name,inbox.image,inbox.from)
        }

    }

}