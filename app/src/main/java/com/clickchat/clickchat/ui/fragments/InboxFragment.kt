package com.clickchat.clickchat.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.clickchat.clickchat.data.Inbox
import com.clickchat.clickchat.databinding.ActivityInboxFragmentBinding
import com.clickchat.clickchat.databinding.ListItemBinding
import com.clickchat.clickchat.ui.actvities.ChatActivity
import com.clickchat.clickchat.ui.actvities.NAME
import com.clickchat.clickchat.ui.actvities.PHOTO
import com.clickchat.clickchat.ui.actvities.UID
import com.clickchat.clickchat.ui.actvities.ViewHolders.chatViewHolder

class InboxFragment: Fragment() {

    private lateinit var binding: ActivityInboxFragmentBinding
    lateinit var mAdapter: FirebaseRecyclerAdapter<Inbox, chatViewHolder>
    private lateinit var viewManager: RecyclerView.LayoutManager

    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    val realTimeDb by lazy {
        FirebaseDatabase.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityInboxFragmentBinding.inflate(layoutInflater)
        viewManager = LinearLayoutManager(requireContext())
        setUpAdapter()

        // Inflate the layout for this fragment
        return binding.root

    }

    fun setUpAdapter() {

        val config = realTimeDb.reference.child("chats").child(auth.uid!!)

        val options = FirebaseRecyclerOptions.Builder<Inbox>()
            .setLifecycleOwner(viewLifecycleOwner)
            .setQuery(config,Inbox::class.java)
            .build()

        mAdapter = object : FirebaseRecyclerAdapter<Inbox, chatViewHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatViewHolder {
              return chatViewHolder(ListItemBinding.inflate(LayoutInflater.from(context),parent,false))
            }

            override fun onBindViewHolder(holder: chatViewHolder, position: Int, model: Inbox) {

                    Log.e("HELLO?",position.toString())

                    holder.bind(model) { name: String, photo: String, id: String ->

                        val intent = Intent(requireContext(), ChatActivity::class.java)
                        intent.putExtra(UID, id)
                        intent.putExtra(NAME, name)
                        intent.putExtra(PHOTO, photo)
                        startActivity(intent)
                    }

            }

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvInbox.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = mAdapter
        }

    }



    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

}