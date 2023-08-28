package com.alwihbsyi.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alwihbsyi.githubuser.data.response.SearchResponse
import com.alwihbsyi.githubuser.data.response.UserResponse
import com.alwihbsyi.githubuser.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _user = MutableLiveData<ArrayList<UserResponse>>()
    val user: LiveData<ArrayList<UserResponse>> = _user

    private val _searchUser = MutableLiveData<List<UserResponse>>()
    val searchUser: LiveData<List<UserResponse>> = _searchUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainActivity"
    }

    init {
        viewModelScope.launch { getAllUser() }
    }

    suspend fun getAllUser() {
        _isLoading.value = true
        val getUserDeferred = ApiConfig.getApiService().getAllUser()
        try {
            _isLoading.value = false
            _user.postValue(getUserDeferred)
        } catch (e: Exception) {
            _isLoading.value = false
            Log.e(TAG, "onFailure: ${e.message.toString()}")
        }
    }

    fun searchUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object : Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (responseBody != null) {
                    _searchUser.value = response.body()!!.items
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

}