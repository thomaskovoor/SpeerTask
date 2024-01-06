package com.example.speerapplication.network

import com.example.speerapplication.dataclass.UserProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("{username}")
     fun getUserProfile(@Path("username") userName:String): Call<UserProfile>

    @GET("{username}/followers")
    fun getUserFollowers(@Path("username") userName:String,@Query("page") page: Int): Call<List<UserProfile>>

    @GET("{username}/following")
    fun getUserFollowing(@Path("username") userName:String,@Query("page") page: Int): Call<List<UserProfile>>
}