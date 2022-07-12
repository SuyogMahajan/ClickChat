package com.clickchat.clickchat.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.clickchat.clickchat.R
import com.clickchat.clickchat.data.User
import com.clickchat.clickchat.databinding.ActivityPeopleFragmentBinding
import com.clickchat.clickchat.ui.actvities.*
import com.clickchat.clickchat.ui.actvities.ViewHolders.EmptyViewHolder
import com.clickchat.clickchat.ui.actvities.ViewHolders.UserViewHolder

const val DELETED_VIEW_TYPE = 1
const val NORMAL_VIEW_TYPE = 2


class PeopleFragment:Fragment() {


    private lateinit var binding:ActivityPeopleFragmentBinding
    lateinit var mAdapter: FirestorePagingAdapter<User,RecyclerView.ViewHolder>


    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    val database by lazy {
        FirebaseFirestore.getInstance().collection("users").orderBy("name", Query.Direction.ASCENDING)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityPeopleFragmentBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        setUpAdapter()

        return binding.root
    }

    fun setUpAdapter() {

        val config = PagingConfig(10,2,false)

        val options = FirestorePagingOptions.Builder<User>()
            .setLifecycleOwner(viewLifecycleOwner)
            .setQuery(database,config,User::class.java)
            .build()

            mAdapter = object : FirestorePagingAdapter<User, RecyclerView.ViewHolder>(options) {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                    val view = layoutInflater.inflate(R.layout.list_item2, parent, false)

                    return when(viewType){
                        NORMAL_VIEW_TYPE -> UserViewHolder(view)
                        else -> EmptyViewHolder(layoutInflater.inflate(R.layout.empty_view,parent,false))
                    }
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: User) {

                    if (holder is UserViewHolder) {
                        holder.bind(model) { name: String, photo: String, id: String ->

                            val intent = Intent(requireContext(), ChatActivity::class.java)

                            intent.putExtra(UID, id)
                            intent.putExtra(NAME, name)
                            intent.putExtra(PHOTO, photo)

                            startActivity(intent)
                        }
                    }

                }

                override fun getItemViewType(position: Int): Int {
                   val item = getItem(position)?.toObject(User::class.java)

                    return if(item!!.uid == auth.uid){
                        DELETED_VIEW_TYPE
                    }else{
                        NORMAL_VIEW_TYPE
                    }

                }

            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPeople.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
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