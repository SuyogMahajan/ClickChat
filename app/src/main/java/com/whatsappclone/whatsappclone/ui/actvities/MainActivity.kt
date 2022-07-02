package com.whatsappclone.whatsappclone.ui.actvities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.whatsappclone.whatsappclone.databinding.ActivityMainBinding
import com.whatsappclone.whatsappclone.ui.adapters.ScreenSliderAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

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
}