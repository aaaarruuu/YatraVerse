package com.yatraverse.data.api

import com.yatraverse.data.models.ChatRequest
import com.yatraverse.data.models.ChatResponse
import com.yatraverse.data.models.SignupRequest
import com.yatraverse.data.models.Destination
import com.yatraverse.data.models.LoginRequest
import com.yatraverse.data.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/destinations")
    suspend fun getDestinations(): Response<List<Destination>>

    @GET("api/destinations/{id}")
    suspend fun getDestination(@Path("id") id: Long): Response<Destination>

    @POST("api/auth/signup")
    suspend fun signup(@Body request: SignupRequest): Response<LoginResponse>

    @POST("api/chat")
    suspend fun chat(
        @Header("Authorization") bearer: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>
}