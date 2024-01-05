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