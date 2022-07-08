package com.whatsappclone.whatsappclone.ui.actvities.ViewHolders

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.whatsappclone.whatsappclone.R
import com.whatsappclone.whatsappclone.data.User
import com.whatsappclone.whatsappclone.databinding.ListItem2Binding
import com.whatsappclone.whatsappclone.databinding.ListItemBinding
import kotlinx.coroutines.withContext

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
