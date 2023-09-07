package com.alwihbsyi.githubuser.di

import android.content.Context
import com.alwihbsyi.githubuser.data.UserFavRepository
import com.alwihbsyi.githubuser.data.local.room.UserFavDatabase
import com.alwihbsyi.githubuser.data.remote.retrofit.ApiConfig
import com.alwihbsyi.githubuser.util.Executor

object Injection {

    fun provideRepository(context: Context): UserFavRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserFavDatabase.getInstance(context)
        val dao = database.userDao()
        val appExecutors = Executor()

        return UserFavRepository.getInstance(apiService, dao, appExecutors)
    }

}