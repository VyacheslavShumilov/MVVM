package com.hfad.mvvm.mainviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hfad.mvvm.model.Users
import com.hfad.mvvm.repository.MainRepository
import kotlinx.coroutines.*

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val userList = MutableLiveData<ArrayList<Users>>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("error ${throwable.localizedMessage}")
    }

    fun getAllUsers() {
        job = CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getUserAll()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    userList.postValue(response.body())
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