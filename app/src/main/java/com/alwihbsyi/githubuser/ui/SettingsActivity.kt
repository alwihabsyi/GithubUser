package com.alwihbsyi.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alwihbsyi.githubuser.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private var _binding: ActivitySettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val pref = SettingPreferences.getInstance(application.dataStore)
//        val viewModel = ViewModelProvider(this, SettingsViewModel(pref))[SettingsViewModel::class.java]
    }
}