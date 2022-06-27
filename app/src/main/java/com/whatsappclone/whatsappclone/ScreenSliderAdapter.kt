package com.whatsappclone.whatsappclone

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSliderAdapter(fa: FragmentActivity) :FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {

        when(position){
            0 -> return InboxFragment()
            else -> return PeopleFragment()
    }

    }

}
