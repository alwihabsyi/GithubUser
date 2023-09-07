package com.alwihbsyi.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.alwihbsyi.githubuser.data.remote.response.SearchResponse
import com.alwihbsyi.githubuser.data.remote.response.UserResponse
import com.alwihbsyi.githubuser.data.remote.retrofit.ApiConfig
import com.alwihbsyi.githubuser.util.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences): ViewModel() {

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



    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

}