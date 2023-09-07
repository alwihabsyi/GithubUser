package com.alwihbsyi.githubuser.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alwihbsyi.githubuser.ui.FollowFragment

class ViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    var model: String? = null

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(position, model)
    }
    override fun getItemCount(): Int = 2
}