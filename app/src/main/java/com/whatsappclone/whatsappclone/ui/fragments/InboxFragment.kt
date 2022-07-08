package com.whatsappclone.whatsappclone.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingConfig
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.whatsappclone.whatsappclone.R
import com.whatsappclone.whatsappclone.data.Inbox
import com.whatsappclone.whatsappclone.data.User
import com.whatsappclone.whatsappclone.databinding.ActivityInboxFragmentBinding
import com.whatsappclone.whatsappclone.databinding.ActivityPeopleFragmentBinding
import com.whatsappclone.whatsappclone.ui.actvities.ChatActivity
import com.whatsappclone.whatsappclone.ui.actvities.NAME
import com.whatsappclone.whatsappclone.ui.actvities.PHOTO
import com.whatsappclone.whatsappclone.ui.actvities.UID
import com.whatsappclone.whatsappclone.ui.actvities.ViewHolders.UserViewHolder
import com.whatsappclone.whatsappclone.ui.actvities.ViewHolders.chatViewHolder

class InboxFragment: Fragment() {
    private lateinit var binding: ActivityInboxFragmentBinding
    lateinit var mAdapter: FirebaseRecyclerAdapter<Inbox, chatViewHolder>


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
        setUpAdapter()
        binding = ActivityInboxFragmentBinding.inflate(layoutInflater)
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

                val view = layoutInflater.inflate(R.layout.list_item, parent, false)

                return chatViewHolder(view)

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
            layoutManager = LinearLayoutManager(requireContext())
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