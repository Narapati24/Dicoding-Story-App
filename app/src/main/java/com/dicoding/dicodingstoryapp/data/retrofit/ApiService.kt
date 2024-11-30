package com.dicoding.dicodingstoryapp.data.retrofit

import com.dicoding.dicodingstoryapp.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiService {
    @POST("/v1/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse
}