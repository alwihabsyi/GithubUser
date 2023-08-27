package com.alwihbsyi.githubuser.data.retrofit

import com.alwihbsyi.githubuser.data.response.ResponseItem
import com.alwihbsyi.githubuser.data.response.SearchResponse
import com.alwihbsyi.githubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getAllUser(): Call<List<ResponseItem>>

    @GET("search/users")
    fun getDetailUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ResponseItem>

    @GET("users/{username}/follower")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<ResponseItem>

}