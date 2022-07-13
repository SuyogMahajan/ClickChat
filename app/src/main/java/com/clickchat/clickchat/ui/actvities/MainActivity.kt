package com.clickchat.clickchat.ui.actvities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.clickchat.clickchat.R
import com.google.android.material.tabs.TabLayoutMediator
import com.clickchat.clickchat.databinding.ActivityMainBinding
import com.clickchat.clickchat.ui.adapters.ScreenSliderAdapter
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);

        setContentView(binding.root)
        binding.viewPagerLay.adapter = ScreenSliderAdapter(this)

        TabLayoutMediator(binding.tabLay,binding.viewPagerLay,
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->

            when(position) {
                0-> tab.text = "Chat"
                1-> tab.text = "Status"
            }

        }
).attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

       menuInflater.inflate(R.menu.toolbar_menu,menu)

        val searchBtn = menu?.findItem(R.id.search)
        val searchView = searchBtn?.actionView as SearchView
        val profileBtn = menu?.findItem(R.id.Profile)
        val clearChat = menu?.findItem(R.id.clearChat)
        val logOut = menu?.findItem(R.id.logOut)
        val feedback = menu?.findItem(R.id.feedback)

        profileBtn?.setOnMenuItemClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
             true
        }

//        clearChat.setOnMenuItemClickListener {
//
//        }
//
        logOut!!.setOnMenuItemClickListener {

            auth!!.signOut()
            triggerRebirth(this)
            true

        }

//        feedback.setOnMenuItemClickListener {
//
//        }

        searchBtn.setOnActionExpandListener(object:MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                return true
            }

        })

        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    fun triggerRebirth(context: Context) {

        val packageManager = context.getPackageManager();

        val intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        val componentName = intent?.getComponent();

        val mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);

    }
}
