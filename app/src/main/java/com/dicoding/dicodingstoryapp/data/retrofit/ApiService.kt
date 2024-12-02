package com.dicoding.dicodingstoryapp.data.retrofit

import com.dicoding.dicodingstoryapp.data.response.DetailStoryResponse
import com.dicoding.dicodingstoryapp.data.response.LoginResponse
import com.dicoding.dicodingstoryapp.data.response.RegisterResponse
import com.dicoding.dicodingstoryapp.data.response.StoryResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("/v1/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("/v1/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("/v1/stories")
    suspend fun getStories(): StoryResponse

    @GET("/v1/stories/{id}")
    suspend fun getDetailStory(
        @Path("id") id: String
    ): DetailStoryResponse
}