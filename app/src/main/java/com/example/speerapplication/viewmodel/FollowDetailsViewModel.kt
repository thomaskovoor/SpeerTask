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