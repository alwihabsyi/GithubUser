package com.alwihbsyi.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alwihbsyi.githubuser.adapter.RvUserAdapter
import com.alwihbsyi.githubuser.data.remote.response.UserResponse
import com.alwihbsyi.githubuser.databinding.FragmentFollowsBinding
import com.alwihbsyi.githubuser.util.hide
import com.alwihbsyi.githubuser.util.show
import com.alwihbsyi.githubuser.viewmodel.FollowViewModel

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowsBinding
    val viewModel by viewModels<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_INDEX, 0)
        val user = arguments?.getString(ARG_BUNDLE)

        setupRecyclerView()

        if(index == 0){
            user?.let { viewModel.getUserFollower(it) }
        }else{
            user?.let { viewModel.getUserFollowing(it) }
        }

        observer()

    }

    private fun observer() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }

        viewModel.userFollowing.observe(viewLifecycleOwner){
            setupRvData(it)
        }

        viewModel.userFollower.observe(viewLifecycleOwner){
            setupRvData(it)
        }
    }

    private fun setupRvData(responseItems: List<UserResponse>) {
        val adapter = RvUserAdapter()
        adapter.differ.submitList(responseItems)
        binding.rvUserFollows.adapter = adapter
        adapter.onClick = {
            val action = DetailFragmentDirections.actionDetailFragmentSelf(it.login)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        binding.rvUserFollows.apply {
            val rvLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = rvLayoutManager
        }
    }

    companion object {

        private const val ARG_INDEX = "index"
        private const val ARG_BUNDLE = "data"

        @JvmStatic
        fun newInstance(index: Int, user: String?) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_INDEX, index)
                    putString(ARG_BUNDLE, user)
                }
            }

    }

}