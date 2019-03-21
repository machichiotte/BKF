package com.whitedev.bkf.data.network

import com.whitedev.bkf.model.ServiceResponse
import com.whitedev.bkf.model.atelier.ServiceResponseAtelier
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestApi {
    @GET("auth/{uniqueId}")
    fun getUniqueIdAuth(@Path("uniqueId", encoded = true) uniqueId: String?): Call<ServiceResponse>

    @GET("auth/{login}/{password}")
    fun getLoginAuth(
        @Path("login", encoded = true) login: String, @Path(
            "password",
            encoded = true
        ) password: String
    ): Call<ServiceResponse>

    @GET("getListDataAtelier/{token}/{timestamp}")
    fun getListDataAtelier(@Path("token", encoded = true) token: String, @Path("timestamp", encoded = true) timestamp: Long): Call<ServiceResponse>

    @GET("getListAtelier/{token}/{timestamp}")
    fun getListAtelier(@Path("token", encoded = true) token: String, @Path("timestamp", encoded = true) timestamp: Long): Call<ServiceResponseAtelier>
}
