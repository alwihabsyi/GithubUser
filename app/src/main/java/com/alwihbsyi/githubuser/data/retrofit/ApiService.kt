package com.alwihbsyi.githubuser.data.retrofit

import com.alwihbsyi.githubuser.BuildConfig
import com.alwihbsyi.githubuser.BuildConfig.*
import com.alwihbsyi.githubuser.data.response.SearchResponse
import com.alwihbsyi.githubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val API_TOKEN = BuildConfig.API_TOKEN
    }

    @GET("users")
    @Headers(("Authorization: token $API_TOKEN"))
    suspend fun getAllUser(): ArrayList<UserResponse>

    @GET("search/users")
    @Headers(("Authorization: token $API_TOKEN"))
    fun searchUser(
        @Query("q") username: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    @Headers(("Authorization: token $API_TOKEN"))
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserResponse>

    @GET("users/{username}/following")
    @Headers(("Authorization: token $API_TOKEN"))
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<UserResponse>>

    @GET("users/{username}/followers")
    @Headers(("Authorization: token $API_TOKEN"))
    fun getUserFollower(
        @Path("username") username: String
    ): Call<List<UserResponse>>

}