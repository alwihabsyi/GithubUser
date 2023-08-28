package com.alwihbsyi.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwihbsyi.githubuser.adapter.RvUserAdapter
import com.alwihbsyi.githubuser.data.response.UserResponse
import com.alwihbsyi.githubuser.databinding.FragmentHomeBinding
import com.alwihbsyi.githubuser.util.hide
import com.alwihbsyi.githubuser.util.show
import com.alwihbsyi.githubuser.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUserRv()
        observer()
        setupSearchBar()

        binding.btnSettings.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.getAllUser()
            }
        }
    }

    private fun setupSearchBar() {

        binding.apply {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { text, actionId, event ->
                searchView.hide()
                viewModel.searchUser(searchView.text.toString())
                false
            }
        }
    }

    private fun observer() {
        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            if (isLoading) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }

        viewModel.user.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.tvGithubUser.show()
                return@observe
            }

            setupRvData(it)
        }

        viewModel.searchUser.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.tvGithubUser.show()
                binding.tvGithubUser.text = "No account with that username"
                return@observe
            }

            setupRvData(it)
        }
    }

    private fun setupRvData(responseItems: List<UserResponse>) {
        binding.tvGithubUser.hide()
        val adapter = RvUserAdapter()
        adapter.differ.submitList(responseItems)
        binding.rvUser.adapter = adapter
        adapter.onClick = {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            action.userResponse = it
            findNavController().navigate(action)
        }
    }

    private fun setupUserRv() {
        binding.rvUser.apply {
            val rvLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = rvLayoutManager
        }
    }

}