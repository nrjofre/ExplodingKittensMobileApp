package com.example.explodingkittensapp.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (isDebug)
        HttpLoggingInterceptor.Level.BODY
    else
        HttpLoggingInterceptor.Level.NONE
    return logging
}

private fun getOkClient(): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(makeLoggingInterceptor(isDebug = true))
        .build()
}

fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl("https://exploding-kittens-api.herokuapp.com/")
        .client(getOkClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit
}

fun UsersApi(retrofit: Retrofit): UsersRemoteRepository = retrofit.create(UsersRemoteRepository::class.java)
fun FriendsApi(retrofit: Retrofit): FriendInviteRemoteRepository = retrofit.create(FriendInviteRemoteRepository::class.java)
fun MatchApi(retrofit: Retrofit): MatchInviteRemoteRepository = retrofit.create(MatchInviteRemoteRepository::class.java)