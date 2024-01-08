package com.example.speerapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.speerapplication.dataclass.UserProfile

class UserSharedViewModel:ViewModel() {
    private val _data = MutableLiveData<UserProfile?>()
    val data: MutableLiveData<UserProfile?> get() = _data

    private val _userName= MutableLiveData<String?>()
    val userName: MutableLiveData<String?> get() = _userName

    fun setData(newData: UserProfile?) {
        _data.value = newData
    }

    fun setUserName(newUserName: String?) {
        userName.value = newUserName
    }
}