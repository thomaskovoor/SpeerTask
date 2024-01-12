package com.example.speerapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speerapplication.dataclass.Resource
import com.example.speerapplication.dataclass.UserProfile
import com.example.speerapplication.network.ApiInterface
import com.example.speerapplication.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * `FollowDetailsViewModel` is a class that extends `ViewModel`. It is responsible for preparing and managing the data for the `FollowDetailsFragment`.
 *
 * @property _followersLiveData This is a private mutable live data of `Resource<List<UserProfile>>`. It represents the state of the network request for a list of user profiles.
 * @property followersLiveData This is a public getter for `_followersLiveData`.
 *
 * @property currentPage This is the current page of the followers or following list.
 *
 * @function getFollowersOrFollowing This function is used to get a list of followers or following for a user.
 *
 * @param login The username of the user.
 * @param button The button that was clicked. It can be "followers" or "following".
 *
 * Inside this function:
 * - The `_followersLiveData` is posted with a `Loading` state.
 * - The `ApiInterface` is created using the `RetrofitInstance`.
 * - A network request is made to get the list of followers or following based on the `button` parameter.
 * - If the network request is successful:
 *   - If the response body is not null, the `_followersLiveData` is posted with a `Success` state and the response body.
 *   - Otherwise, the `_followersLiveData` is posted with a `Failure` state and an error message of "Not Found".
 * - If the network request is not successful, the `_followersLiveData` is posted with a `Failure` state and the error message from the response.
 * - If the network request fails, the `_followersLiveData` is posted with a `Failure` state and an error message of "Server Error".
 *
 * @function loadNextPage This function is used to load the next page of the followers or following list.
 *
 * @param login The username of the user.
 * @param button The button that was clicked. It can be "followers" or "following".
 *
 * Inside this function:
 * - The `currentPage` is incremented.
 * - The `getFollowersOrFollowing` function is called with the `login` and `button` parameters.
 */

class FollowDetailsViewModel:ViewModel() {

    var _followersLiveData = MutableLiveData<Resource<List<UserProfile>>>()
    val followersLiveData : MutableLiveData<Resource<List<UserProfile>>> get() = _followersLiveData

    var currentPage = 1



    fun getFollowersOrFollowing(login:String,button:String){

        viewModelScope.launch {
            _followersLiveData.postValue(Resource.Loading)
            val call: Call<List<UserProfile>>
            val apiInterface = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            call = if(button=="followers")
                apiInterface.getUserFollowers(login,currentPage)
            else
                apiInterface.getUserFollowing(login,currentPage)

            call.enqueue(object :Callback<List<UserProfile>>{
                override fun onResponse(
                    call: Call<List<UserProfile>>,
                    response: Response<List<UserProfile>>
                ) {
                    if(response.isSuccessful){
                        if(response.body() != null){
                            _followersLiveData.postValue(Resource.Success(response.body()!!))
                        }
                        else{
                            _followersLiveData.postValue(Resource.Failure(false,404,"Not Found"))
                        }
                    }
                    else
                    {
                        _followersLiveData.postValue(Resource.Failure(true,response.code(),response.errorBody().toString()))
                    }
                }

                override fun onFailure(call: Call<List<UserProfile>>, t: Throwable) {
                    _followersLiveData.postValue(Resource.Failure(true,500,"Server Error"))
                }

            })
        }

    }

    fun loadNextPage(login: String, button: String) {
        currentPage++
        getFollowersOrFollowing(login, button)
    }
}