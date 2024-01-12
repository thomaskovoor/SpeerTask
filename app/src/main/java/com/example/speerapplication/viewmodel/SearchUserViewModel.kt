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
 * `SearchUserViewModel` is a class that extends `ViewModel`. It is responsible for preparing and managing the data for the `SearchUserFragment`.
 *
 * @property _userLiveData This is a private mutable live data of `Resource<UserProfile>`. It represents the state of the network request for a user's profile.
 * @property userLiveData This is a public getter for `_userLiveData`.
 *
 * @function searchUser This function is used to search for a user's profile.
 *
 * @param query The username of the user to be searched.
 *
 * Inside this function:
 * - The `_userLiveData` is posted with a `Loading` state.
 * - The `ApiInterface` is created using the `RetrofitInstance`.
 * - A network request is made to get the user's profile.
 * - If the network request is successful:
 *   - If the response body is not null and the message is not "Not Found", the `_userLiveData` is posted with a `Success` state and the response body.
 *   - Otherwise, the `_userLiveData` is posted with a `Failure` state and an error message of "Not Found".
 * - If the network request is not successful, the `_userLiveData` is posted with a `Failure` state and the error message from the response.
 * - If the network request fails, the `_userLiveData` is posted with a `Failure` state and an error message of "Server Error".
 */

class SearchUserViewModel :ViewModel(){

    var _userLiveData = MutableLiveData<Resource<UserProfile>>()
    val userLiveData : MutableLiveData<Resource<UserProfile>> get() = _userLiveData

    fun searchUser(query: String){


        viewModelScope.launch {
        _userLiveData.postValue(Resource.Loading)

            val apiInterface = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            val call = apiInterface.getUserProfile(query)
            call.enqueue(
                object: Callback<UserProfile>{
                    override fun onResponse(
                        call: Call<UserProfile>,
                        response: Response<UserProfile>
                    ) {
                        if(response.isSuccessful){
                            if(response.body() != null && response.body()!!.getMessage()!="Not Found"){
                                _userLiveData.postValue(Resource.Success(response.body()!!))
                            }
                            else{
                                _userLiveData.postValue(Resource.Failure(false,404,"Not Found"))
                            }
                        }
                        else
                        {
                            _userLiveData.postValue(Resource.Failure(true,response.code(),response.errorBody().toString()))
                        }
                    }

                    override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                        _userLiveData.postValue(Resource.Failure(true,500,"Server Error"))
                    }

                }
            )


        }

    }



}