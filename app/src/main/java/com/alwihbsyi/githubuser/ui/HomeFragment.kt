package com.alwihbsyi.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwihbsyi.githubuser.adapter.RvUserAdapter
import com.alwihbsyi.githubuser.adapter.RvUserSearchAdapter
import com.alwihbsyi.githubuser.data.response.ItemsItem
import com.alwihbsyi.githubuser.data.response.ResponseItem
import com.alwihbsyi.githubuser.databinding.FragmentHomeBinding
import com.alwihbsyi.githubuser.util.hide
import com.alwihbsyi.githubuser.util.show
import com.alwihbsyi.githubuser.viewmodel.MainViewModel

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

        binding.apply {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { text, actionId, event ->
                searchView.hide()
//                Toast.makeText(requireContext(), searchView.text, Toast.LENGTH_SHORT).show()
                viewModel.searchUsername(searchView.text.toString())
                true
            }
        }
    }

    private fun observer() {
        viewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            if (isLoading) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
                binding.tvGithubUser.hide()
            }
        }

        viewModel.user.observe(viewLifecycleOwner){
            setupRvData(it)
        }

        viewModel.searchUser.observe(viewLifecycleOwner){
            updateRvData(it)
        }
    }

    private fun setupRvData(responseItems: List<ResponseItem>) {
        val adapter = RvUserAdapter()
        adapter.submitList(responseItems)
        binding.rvUser.adapter = adapter
    }

    private fun updateRvData(responseItems: List<ItemsItem>) {
        val adapter = RvUserSearchAdapter()
        adapter.submitList(responseItems)
        binding.rvUser.adapter = adapter
    }

    private fun setupUserRv() {
        binding.rvUser.apply {
            val rvLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = rvLayoutManager
            addItemDecoration(DividerItemDecoration(context, rvLayoutManager.orientation))
        }
    }

}