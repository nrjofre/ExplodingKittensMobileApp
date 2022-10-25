package com.example.explodingkittensapp.networking

import com.example.explodingkittensapp.APImodels.Bodies.*
import com.example.explodingkittensapp.APImodels.Responses.APILoginResponse
import com.example.explodingkittensapp.APImodels.Responses.APIMessageResponse
import com.example.explodingkittensapp.APImodels.Responses.APISigninResponse
import com.example.explodingkittensapp.model.CardModel
import com.example.explodingkittensapp.model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface UsersRemoteRepository {
    @POST("register")
    fun createUser(@Body user: APIUser): Call<APISigninResponse>

    @POST("login")
    fun loginUser(@Body login: APILogin): Call<APILoginResponse>

    @POST("participants/")
    fun getGameParticipants(@Body participants: APIGameParticipants): Call<List<UserModel>>

    @GET("friends/{username}")
    fun getFriends(@Path("username") username: String): Call<List<UserModel>>

    @GET("users/{username}")
    fun getProfile(@Path("username") username: String): Call<UserModel>

    @GET("notfriends/{username}")
    fun getNotFriends(@Path("username") username: String): Call<List<UserModel>>

    @GET("cards/{username}")
    fun getUserCards(@Path("username") username: String): Call<List<CardModel>>

    @GET("draw5/{username}")
    fun getdealcards(@Path("username") username: String): Call<APIMessageResponse>

    @POST("myturn/")
    fun getMyTurn(@Body turn: APIMyturn): Call<APIMessageResponse>

    @POST("playcard")
    fun playCard(@Body play: APIPlay): Call<APIMessageResponse>

    @GET("drawgame/{gamename}")
    fun nextTurn(@Path("gamename") gamename: String): Call<APIMessageResponse>

    @GET("draw/{username}")
    fun draw(@Path("username") username: String): Call<APIMessageResponse>

    @GET("win/{username}")
    fun win(@Path("username") username: String): Call<APIMessageResponse>
}