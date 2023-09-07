package com.alwihbsyi.githubuser.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.alwihbsyi.githubuser.R
import com.alwihbsyi.githubuser.adapter.ViewPagerAdapter
import com.alwihbsyi.githubuser.data.Result
import com.alwihbsyi.githubuser.data.local.entity.UserFavEntity
import com.alwihbsyi.githubuser.data.remote.response.UserResponse
import com.alwihbsyi.githubuser.databinding.FragmentDetailBinding
import com.alwihbsyi.githubuser.util.hide
import com.alwihbsyi.githubuser.util.show
import com.alwihbsyi.githubuser.viewmodel.DetailViewModel
import com.alwihbsyi.githubuser.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

open class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding
    val viewModel by viewModels<DetailViewModel> { ViewModelFactory.getInstance(requireActivity()) }

    private var isFavorite = false

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

    private fun setupDetailPage(user: String?) {
        viewModel.viewModelScope.launch { viewModel. getUserDetail(user!!)}
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

        viewModel.user.observe(viewLifecycleOwner) { user ->
            Glide.with(requireContext()).load(user.avatarUrl).into(binding.ivDetailProfile)
            binding.tvName.text = user.name
            binding.tvUsername.text = user.login
            binding.tvFollower.text = "${user.followers} Followers"
            binding.tvFollowing.text = "${user.following} Following"

            setFavorite(user)
        }
    }

    private fun setFavorite(user: UserResponse) {
        val userEntity = UserFavEntity(user.name, user.login!!, user.avatarUrl)
        viewModel.getUserInfo(user.login).observe(viewLifecycleOwner) {
            isFavorite = it.isNotEmpty()

            binding.btnAddFav.imageTintList = if (it.isEmpty()) {
                ColorStateList.valueOf(Color.rgb(255, 255, 255))
            } else {
                ColorStateList.valueOf(Color.rgb(247, 106, 123))
            }
        }

        binding.btnAddFav.setOnClickListener {
            if (isFavorite){
                viewModel.deleteFromFavorite(userEntity).observe(viewLifecycleOwner) {
                    when (it) {
                        is Result.Loading -> {
                            binding.btnAddFav.hide()
                            binding.progressBarFav.show()
                        }
                        is Result.Success -> {
                            binding.progressBarFav.show()
                            binding.btnAddFav.show()
                            Toast.makeText(requireContext(), it.data, Toast.LENGTH_SHORT).show()
                        }
                        is Result.Error -> {
                            binding.progressBarFav.show()
                            binding.btnAddFav.show()
                            Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else {
                viewModel.addToFavorite(userEntity).observe(viewLifecycleOwner) {
                    when (it) {
                        is Result.Loading -> {
                            binding.btnAddFav.hide()
                            binding.progressBarFav.show()
                        }
                        is Result.Success -> {
                            binding.progressBarFav.show()
                            binding.btnAddFav.show()
                            Toast.makeText(requireContext(), it.data, Toast.LENGTH_SHORT).show()
                        }
                        is Result.Error -> {
                            binding.progressBarFav.show()
                            binding.btnAddFav.show()
                            Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupViewPager(user: String?) {
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.model = user
        binding.viewPager.adapter = viewPagerAdapter
        val tabs = binding.tabLayout
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
}
