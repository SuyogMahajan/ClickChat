package com.whatsappclone.whatsappclone.ui.actvities

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.whatsappclone.whatsappclone.R
import com.whatsappclone.whatsappclone.data.User
import com.whatsappclone.whatsappclone.databinding.ListItemBinding

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ListItemBinding
    fun bind(user: User) {
        binding = ListItemBinding.bind(itemView)

        binding.userNameTextView.text = user.name
        binding.statusTv.text = user.status
        binding.countTV.visibility = View.GONE
        binding.timeTv.visibility = View.GONE

             Picasso.get()
            .load(user.thumbImage)
            .placeholder(R.drawable.profile_dummy)
            .error(R.drawable.profile_dummy)
            .into(binding.profilePic)

    }

}