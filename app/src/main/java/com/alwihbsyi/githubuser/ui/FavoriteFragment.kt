package com.alwihbsyi.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwihbsyi.githubuser.adapter.RvFavoriteAdapter
import com.alwihbsyi.githubuser.adapter.RvUserAdapter
import com.alwihbsyi.githubuser.data.Result
import com.alwihbsyi.githubuser.data.local.entity.UserFavEntity
import com.alwihbsyi.githubuser.databinding.FragmentFavoriteBinding
import com.alwihbsyi.githubuser.util.hide
import com.alwihbsyi.githubuser.util.show
import com.alwihbsyi.githubuser.viewmodel.FavoriteViewModel
import com.alwihbsyi.githubuser.viewmodel.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FavoriteViewModel> { ViewModelFactory.getInstance(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        observer()
    }

    private fun observer() {
        viewModel.getUserFavorite().observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    setupRvData(it.data)
                }
                is Result.Error -> {
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }



    private fun setupRvData(responseItems: List<UserFavEntity>) {
        val adapter = RvFavoriteAdapter()
        adapter.differ.submitList(responseItems)
        binding.rvFavorite.adapter = adapter
        adapter.onClick = {
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it.login)
            findNavController().navigate(action)
        }
    }

    private fun setupRv() {
        binding.rvFavorite.apply {
            val rvLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = rvLayoutManager
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}