package com.example.speerapplication.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * The `UserProfile` class is a data class that represents a user's profile.
 * This class is used to parse JSON data from a web API using the Gson library.
 * Each variable in the class represents a field in the JSON data.
 * @property userAvatarUrl The URL of the user's avatar image. It's annotated with @SerializedName("avatar_url") to map the "avatar_url" field in the JSON to this property.
 * @property name The user's real name. It's annotated with @SerializedName("name") to map the "name" field in the JSON to this property.
 * @property bio The user's bio. It's annotated with @SerializedName("bio") to map the "bio" field in the JSON to this property.
 * @property followers The number of followers the user has. It's annotated with @SerializedName("followers") to map the "followers" field in the JSON to this property.
 * @property following The number of users the user is following. It's annotated with @SerializedName("following") to map the "following" field in the JSON to this property.
 * @property followersUrl The URL of the user's followers. It's annotated with @SerializedName("followers_url") to map the "followers_url" field in the JSON to this property.
 * @property followingUrl The URL of the users the user is following. It's annotated with @SerializedName("following_url") to map the "following_url" field in the JSON to this property.
 * @property message A message associated with the user's profile. It's annotated with @SerializedName("message") to map the "message" field in the JSON to this property.
 *
 * Each property has a corresponding getter method to access its value.
 */

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