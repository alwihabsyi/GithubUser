package com.alwihbsyi.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alwihbsyi.githubuser.data.response.ItemsItem
import com.alwihbsyi.githubuser.data.response.ResponseItem
import com.alwihbsyi.githubuser.data.response.SearchResponse
import com.alwihbsyi.githubuser.data.response.UserResponse
import com.alwihbsyi.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _user = MutableLiveData<List<ResponseItem>>()
    val user: LiveData<List<ResponseItem>> = _user

    private val _searchUser = MutableLiveData<List<ItemsItem>>()
    val searchUser: LiveData<List<ItemsItem>> = _searchUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainActivity"
    }

    init {
        getAllUser()
    }

    private fun getAllUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllUser()
        client.enqueue(object : Callback<List<ResponseItem>> {
            override fun onResponse(call: Call<List<ResponseItem>>, response: Response<List<ResponseItem>>) {
                _isLoading.value = false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _user.value = response.body()
                    }else{
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun searchUsername(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        _searchUser.value = response.body()?.items
                    }else{
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

}