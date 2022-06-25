package com.hfad.mvvm.repository

import com.hfad.mvvm.services.Api

class UserRepository(private val api: Api) {
    suspend fun getUser(login: String) = api.getUser(login)
}