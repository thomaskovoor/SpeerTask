
package com.example.speerapplication.dataclass

/**
 * `Resource` is a sealed class with a generic type `T`. It represents a resource that can have one of three states: `Success`, `Failure`, or `Loading`.
 *
 * @class Success This class represents the successful state of the resource. It contains a `value` of type `T`.
 * @param value The value of the successful resource.
 *
 * @class Failure This class represents the failure state of the resource. It contains an `isNetworkError` boolean, an `errorCode` integer, and an `errorBody` string.
 * @param isNetworkError A boolean indicating whether the failure was due to a network error.
 * @param errorCode The error code of the failure.
 * @param errorBody The error message of the failure.
 *
 * @object Loading This object represents the loading state of the resource.
 */

sealed class Resource<out T>{

    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: String
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}
