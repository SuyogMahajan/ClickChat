package com.clickchat.clickchat.ui.actvities.ViewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.clickchat.clickchat.R
import com.clickchat.clickchat.data.User
import com.clickchat.clickchat.databinding.ListItem2Binding

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: ListItem2Binding

    fun bind(user: User, onClick : (name:String,photo:String,id:String) -> Unit) = with(itemView) {
        binding = ListItem2Binding.bind(itemView)

        binding.userNameTextView.text = user.name
        binding.statusTv.text = user.status

             Picasso.get()
            .load(user.thumbImage)
            .placeholder(R.drawable.profile_dummy)
            .error(R.drawable.profile_dummy)
            .into(binding.profilePic)

        setOnClickListener {
            onClick.invoke(user.name,user.thumbImage,user.uid)
        }

    }

}
