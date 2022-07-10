package com.clickchat.clickchat.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.clickchat.clickchat.ui.fragments.InboxFragment
import com.clickchat.clickchat.ui.fragments.PeopleFragment

class ScreenSliderAdapter(fa: FragmentActivity) :FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {

        when(position){
            0 -> return InboxFragment()
            else -> return PeopleFragment()
    }

    }

}
