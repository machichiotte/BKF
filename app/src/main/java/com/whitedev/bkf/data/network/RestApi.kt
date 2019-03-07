package com.whitedev.bkf.data.network

import com.whitedev.bkf.model.ServiceResponse
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

    @GET("getListColumnAtelier/{token}")
    fun getListColumnAtelier(@Path("token", encoded = true) token: String): Call<ServiceResponse>

    @GET("getListColumnAtelier/{token}")
    fun getListColumnAtelierObs(@Path("token", encoded = true) token: String): Observable<ServiceResponse>

}
