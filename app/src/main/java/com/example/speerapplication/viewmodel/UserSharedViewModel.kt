package com.example.speerapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.speerapplication.dataclass.UserProfile

class UserSharedViewModel:ViewModel() {
    private val _data = MutableLiveData<UserProfile?>()
    val data: MutableLiveData<UserProfile?> get() = _data

    var userName: String? = null

    fun setData(newData: UserProfile?) {
        _data.value = newData
    }
}