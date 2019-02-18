package com.whitedev.bkf

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

import com.whitedev.bkf.pojo.Auth

interface InterfaceApi {
    @GET("auth/{uniqueId}")
    fun getUniqueIdAuth(@Path("uniqueId", encoded = true) uniqueId: String?): Call<Auth>

    @GET("auth/{login}/{password}")
    fun getLoginAuth(
        @Path("login", encoded = true) login: String, @Path(
            "password",
            encoded = true
        ) password: String
    ): Call<Auth>

    @GET("getListColumnAtelier/{token}")
    fun getListColumnAtelier(@Path("token", encoded = true) token: String): Call<Auth>

}
