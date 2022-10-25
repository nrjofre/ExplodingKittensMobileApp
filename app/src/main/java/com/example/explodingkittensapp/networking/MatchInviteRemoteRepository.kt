package com.example.explodingkittensapp.networking

import com.example.explodingkittensapp.APImodels.Bodies.*
import com.example.explodingkittensapp.APImodels.Responses.APIMessageResponse
import com.example.explodingkittensapp.model.FriendInviteModel
import com.example.explodingkittensapp.model.MatchInviteModel
import com.example.explodingkittensapp.model.MatchModel
import retrofit2.Call
import retrofit2.http.*

interface MatchInviteRemoteRepository {
    @POST("minvite")
    fun createMatchInvite(@Body invite: APIMinvite): Call<APIMessageResponse>

    @POST("creatematch")
    fun createMatch(@Body invite: APIMatch): Call<APIMessageResponse>

    @POST("acceptminvite")
    fun acceptMatchInvite(@Body login: APIAcceptMatchInvite): Call<APIMessageResponse>

    @POST("deleteminvite")
    fun rejectMatchInvite(@Body login: APIRejectInvite): Call<APIMessageResponse>

    @GET("minvite/{username}")
    fun getMatchInvites(@Path("username") username: String): Call<List<MatchInviteModel>>

    @GET("matches/{username}")
    fun getMatches(@Path("username") username: String): Call<List<MatchModel>>

}