package com.alwihbsyi.githubuser.viewmodel

import androidx.lifecycle.ViewModel
import com.alwihbsyi.githubuser.data.UserFavRepository

class FavoriteViewModel(private val userRepository: UserFavRepository): ViewModel() {

    fun getUserFavorite() = userRepository.getFavoriteUser()

}