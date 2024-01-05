package com.example.speerapplication.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserProfile {

    @SerializedName("login")
    @Expose
    private val userName: String? = null
    fun getUserName(): String? {
        return userName
    }

    @SerializedName("avatar_url")
    @Expose
    private val userAvatarUrl: String? = null
    fun getUserAvatarUrl(): String? {
        return userAvatarUrl
    }

    @SerializedName("name")
    @Expose
    private val name: String? = null
    fun getName(): String? {
        return name
    }

    @SerializedName("bio")
    @Expose
    private val bio: String? = null
    fun getBio(): String? {
        return bio
    }

    @SerializedName("followers")
    @Expose
    private val followers: Int? = null
    fun getFollowers(): Int? {
        return followers
    }

    @SerializedName("following")
    @Expose
    private val following: Int? = null
    fun getFollowing(): Int? {
        return following
    }

    @SerializedName("followers_url")
    @Expose
    private val followersUrl: String? = null
    fun getFollowersUrl(): String? {
        return followersUrl
    }

    @SerializedName("following_url")
    @Expose
    private val followingUrl: String? = null
    fun getFollowingUrl(): String? {
        return followingUrl
    }

    @SerializedName("message")
    @Expose
    private val message: String? = null
    fun getMessage(): String? {
        return message
    }
}