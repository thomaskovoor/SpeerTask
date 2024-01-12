package com.example.speerapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.speerapplication.dataclass.UserProfile

/**
 * `UserSharedViewModel` is a class that extends `ViewModel`. It is responsible for storing and managing UI-related data in a lifecycle conscious way. This ViewModel is shared between multiple fragments.
 *
 * @property _data This is a private mutable live data of `UserProfile?`. It represents the user profile data that is shared between fragments.
 * @property data This is a public getter for `_data`.
 *
 * @property _userName This is a private mutable live data of `String?`. It represents the username that is shared between fragments.
 * @property userName This is a public getter for `_userName`.
 *
 * @function setData This function is used to set the user profile data.
 *
 * @param newData The new user profile data to be set.
 *
 * Inside this function:
 * - The `_data`'s value is set to `newData`.
 *
 * @function setUserName This function is used to set the username.
 *
 * @param newUserName The new username to be set.
 *
 * Inside this function:
 * - The `userName`'s value is set to `newUserName`.
 */

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