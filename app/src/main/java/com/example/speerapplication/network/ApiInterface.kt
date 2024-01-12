package com.example.speerapplication.network

import com.example.speerapplication.dataclass.UserProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The `ApiInterface` interface defines the endpoints for the API that the application is using.
 * It uses Retrofit annotations to encode details about the parameters and request method of each endpoint.
 *
 * @function getUserProfile This function fetches the user profile from the API. It takes a username as a parameter and returns a `Call` object which can be used to send the request.
 * The `@GET` annotation specifies that this endpoint uses a GET request. The `{username}` in the annotation is a placeholder for the username parameter of the request.
 * The `@Path` annotation is used to replace the `{username}` placeholder in the `@GET` annotation with the `userName` parameter of the function.
 *
 * @function getUserFollowers This function fetches the followers of a user from the API. It takes a username and a page number as parameters and returns a `Call` object which can be used to send the request.
 * The `@GET` annotation specifies that this endpoint uses a GET request. The `{username}/followers` in the annotation is a placeholder for the username parameter of the request and the followers endpoint.
 * The `@Path` annotation is used to replace the `{username}` placeholder in the `@GET` annotation with the `userName` parameter of the function.
 * The `@Query` annotation is used to add the `page` parameter to the request as a query parameter.
 *
 * @function getUserFollowing This function fetches the users that a user is following from the API. It takes a username and a page number as parameters and returns a `Call` object which can be used to send the request.
 * The `@GET` annotation specifies that this endpoint uses a GET request. The `{username}/following` in the annotation is a placeholder for the username parameter of the request and the following endpoint.
 * The `@Path` annotation is used to replace the `{username}` placeholder in the `@GET` annotation with the `userName` parameter of the function.
 * The `@Query` annotation is used to add the `page` parameter to the request as a query parameter.
 */


interface ApiInterface {

    @GET("{username}")
     fun getUserProfile(@Path("username") userName:String): Call<UserProfile>

    @GET("{username}/followers")
    fun getUserFollowers(@Path("username") userName:String,@Query("page") page: Int): Call<List<UserProfile>>

    @GET("{username}/following")
    fun getUserFollowing(@Path("username") userName:String,@Query("page") page: Int): Call<List<UserProfile>>
}