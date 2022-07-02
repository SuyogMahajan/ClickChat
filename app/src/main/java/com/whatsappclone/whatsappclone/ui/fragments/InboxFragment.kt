package com.whatsappclone.whatsappclone.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.whatsappclone.whatsappclone.databinding.ActivityInboxFragmentBinding

class InboxFragment: Fragment() {
    private lateinit var binding:ActivityInboxFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HELLO","Chat")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityInboxFragmentBinding.inflate(inflater)
        // Inflate the layout for this fragmen
        Log.d("HELLO","Chat")

        return binding.root
    }
}