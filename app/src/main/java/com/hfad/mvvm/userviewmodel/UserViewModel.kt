package com.hfad.mvvm.userviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hfad.mvvm.model.User
import com.hfad.mvvm.repository.UserRepository
import kotlinx.coroutines.*
import kotlin.math.log

class UserViewModel constructor(private val userRepository: UserRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val user = MutableLiveData<User>()
    val list = MutableLiveData<String>()
    var job: Job? = null
    var loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("error ${throwable.localizedMessage}")
    }

    fun getUser(login: String) {
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            loading.postValue(true)
            val response = userRepository.getUser(login)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                    loading.value = false
                } else {
                    onError("ERROR: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}