package com.alwihbsyi.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.alwihbsyi.githubuser.data.local.entity.UserFavEntity
import com.alwihbsyi.githubuser.data.local.room.UserFavDao
import com.alwihbsyi.githubuser.data.remote.retrofit.ApiService
import com.alwihbsyi.githubuser.util.Executor

class UserFavRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserFavDao,
    private val appExecutors: Executor
){
    private val result = MediatorLiveData<Result<List<UserFavEntity>>>()
    private val resultS = MediatorLiveData<Result<String>>()

    fun getFavoriteUser(): LiveData<Result<List<UserFavEntity>>> {
        result.value = Result.Loading
        val localData = userDao.getAllFavorite()
        result.addSource(localData) {
            result.value = Result.Success(it)
        }
        return result
    }

    fun getUserInfo(username: String): LiveData<List<UserFavEntity>> {
        return userDao.getUserInfo(username)
    }

    fun insertFavorite(user: UserFavEntity): LiveData<Result<String>> {
        resultS.value = Result.Loading
        appExecutors.diskIO.execute {
            userDao.insert(user)
        }
        resultS.value = Result.Success("${user.login} telah ditambahkan ke data User Favorite")
        return resultS
    }

    fun deleteFavorite(user: UserFavEntity): LiveData<Result<String>> {
        resultS.value = Result.Loading
        appExecutors.diskIO.execute {
            userDao.delete(user)
        }
        resultS.value = Result.Success("${user.login} telah dihapus dari data User Favorite")
        return resultS
    }

    companion object {
        @Volatile
        private var instance: UserFavRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserFavDao,
            appExecutors: Executor
        ): UserFavRepository =
            instance ?: synchronized(this) {
                instance ?: UserFavRepository(apiService, userDao, appExecutors)
            }.also { instance = it }
    }
}