package com.whatsappclone.whatsappclone.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.whatsappclone.whatsappclone.R
import com.whatsappclone.whatsappclone.data.User
import com.whatsappclone.whatsappclone.databinding.ActivityPeopleFragmentBinding
import com.whatsappclone.whatsappclone.ui.actvities.UserViewHolder

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

        val s = if(::mAdapter.isInitialized ) "initialized" else "no"

        Log.d("HELLO", " ${mAdapter}")

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setUpAdapter()

        Log.d("HELLO","People")
    }

    fun setUpAdapter() {

        val config = PagingConfig(10,2,false)

        val options = FirestorePagingOptions.Builder<User>()
            .setLifecycleOwner(viewLifecycleOwner)
            .setQuery(database,config,User::class.java)
            .build()



        try {
            mAdapter = object : FirestorePagingAdapter<User, RecyclerView.ViewHolder>(options) {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                    val view = layoutInflater.inflate(R.layout.list_item, parent, false)
                    Log.d("HELLO","hitting")
                    return when(viewType){
                        NORMAL_VIEW_TYPE -> UserViewHolder(view)
                        else -> EmptyViewHolder(layoutInflater.inflate(R.layout.empty_view,parent,false))
                    }
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: User) {

                    if(holder is UserViewHolder) {
                       holder.bind(model)
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
        }catch(e:Exception){
            Log.d("HELLO?",e.message.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPeople.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }

}