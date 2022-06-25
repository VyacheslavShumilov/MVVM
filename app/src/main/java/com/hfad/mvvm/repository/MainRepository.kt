package com.hfad.mvvm.repository

import com.hfad.mvvm.services.Api

class MainRepository constructor(private val api: Api) {

    suspend fun getUserAll() = api.getUsers()
}