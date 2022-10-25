package com.example.explodingkittensapp.networking

import com.example.explodingkittensapp.APImodels.Bodies.*
import com.example.explodingkittensapp.APImodels.Responses.APIMessageResponse
import com.example.explodingkittensapp.model.FriendInviteModel
import retrofit2.Call
import retrofit2.http.*

interface FriendInviteRemoteRepository {
    @POST("finvite")
    fun createInvite(@Body invite: APIFinvite): Call<APIMessageResponse>

    @POST("acceptfinvite")
    fun acceptUserInvite(@Body login: APIAcceptInvite): Call<APIMessageResponse>

    @POST("deletefinvite")
    fun rejectInvite(@Body login: APIRejectInvite): Call<APIMessageResponse>

    @GET("finvite/{username}")
    fun getFriendInvites(@Path("username") username: String): Call<List<FriendInviteModel>>

}