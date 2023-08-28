package com.alwihbsyi.githubuser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.alwihbsyi.githubuser.R
import com.alwihbsyi.githubuser.adapter.ViewPagerAdapter
import com.alwihbsyi.githubuser.data.response.UserResponse
import com.alwihbsyi.githubuser.databinding.FragmentDetailBinding
import com.alwihbsyi.githubuser.util.hide
import com.alwihbsyi.githubuser.util.show
import com.alwihbsyi.githubuser.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

open class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding
    val viewModel by viewModels<DetailViewModel>()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = DetailFragmentArgs.fromBundle(arguments as Bundle).userResponse

        setupViewPager(user)
        setupDetailPage(user)

        binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupDetailPage(user: UserResponse?) {
        viewModel.viewModelScope.launch { viewModel. getUserDetail(user?.login.toString())}
        observer()
    }

    @SuppressLint("SetTextI18n")
    fun observer() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
                binding.pageContent.show()
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
            Glide.with(requireContext()).load(it.avatarUrl).into(binding.ivDetailProfile)
            binding.tvName.text = it.name
            binding.tvUsername.text = it.login
            binding.tvFollower.text = "${it.followers} Followers"
            binding.tvFollowing.text = "${it.following} Following"
        }
    }

    private fun setupViewPager(user: UserResponse?) {
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.model = user
        binding.viewPager.adapter = viewPagerAdapter
        val tabs = binding.tabLayout
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
}
