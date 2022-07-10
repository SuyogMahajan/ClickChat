package com.clickchat.clickchat.ui.actvities.ViewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.clickchat.clickchat.R
import com.clickchat.clickchat.data.Inbox
import com.clickchat.clickchat.databinding.ListItemBinding
import com.clickchat.clickchat.utils.formatAsTime

class chatViewHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(inbox: Inbox, onClick: (name: String, photo: String, id: String) -> Unit) = with(binding.root) {

            binding.userNameTextView.text = inbox.name
            binding.statusTv.text = inbox.msg

            binding.countTV.visibility = if (inbox.count > 0) View.VISIBLE else View.GONE
            binding.countTV.text = inbox.count.toString()

            binding.timeTv.text = inbox.time.formatAsTime()

            Picasso.get()
                .load(inbox.image)
                .placeholder(R.drawable.profile_dummy)
                .error(R.drawable.profile_dummy)
                .into(binding.profilePic)

            setOnClickListener {
                onClick.invoke(inbox.name, inbox.image, inbox.from)
            }

        }

}