package com.hfad.mvvm.model

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("id")
    var id: Int,
    @SerializedName("login")
    var login:String,
    @SerializedName("avatar_url")
    var avatar_url: String,
    @SerializedName("url")
    var url:String
)

data class User(
    @SerializedName("id")
    var id: Int,
    @SerializedName("login")
    var login:String,
    @SerializedName("avatar_url")
    var avatar_url: String,
    @SerializedName("url")
    var url:String
)