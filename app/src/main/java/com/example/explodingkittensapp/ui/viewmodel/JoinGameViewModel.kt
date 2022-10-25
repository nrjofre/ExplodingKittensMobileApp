package com.example.explodingkittensapp.ui.viewmodel

import android.app.Activity
import android.app.Application
import android.service.autofill.FieldClassification.Match
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.example.explodingkittensapp.APImodels.Bodies.*
import com.example.explodingkittensapp.APImodels.Responses.APIMessageResponse
import com.example.explodingkittensapp.APImodels.Responses.APISigninResponse
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.database.*
import com.example.explodingkittensapp.model.MatchInviteModel
import com.example.explodingkittensapp.model.UserModel
import com.example.explodingkittensapp.navigation.Navigator
import com.example.explodingkittensapp.networking.FriendInviteRemoteRepository
import com.example.explodingkittensapp.networking.MatchInviteRemoteRepository
import com.example.explodingkittensapp.networking.UsersRemoteRepository
import com.example.explodingkittensapp.networking.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class JoinGameViewModel(application: Application) : AndroidViewModel(application) {

    val app = application
    var joinGame: MutableList<MatchInviteModel> = mutableListOf()
    var joinGameLiveData = MutableLiveData<MutableList<MatchInviteModel>>()
    val chosenJoinGame = MutableLiveData<MatchInviteModel>()

    lateinit var gameName: String

    lateinit var navigator: Navigator


    //var database: UserDao
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    init{
        //database = DatabaseRepository(application).postUserDao()
        loadJoinGame()
    }

    fun setNavigator(activity: MainActivity?) {
        navigator = Navigator(activity)
    }

    //DB Methods
    fun loadJoinGame() {
        executor.execute {
            if(joinGame.size != 0) {
                joinGameLiveData.postValue(joinGame)
            }
        }
    }

    //API Methods
    fun joinGameAPI(username: String){
        val service = getRetrofit().create(MatchInviteRemoteRepository::class.java)
        val call =  service.getMatchInvites(username)
        call.enqueue(object : Callback<List<MatchInviteModel>> {
            override fun onFailure(call: Call<List<MatchInviteModel>>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<List<MatchInviteModel>>, response: Response<List<MatchInviteModel>>) {
                if(response.body() != null){
                    val friendsAPI = response.body()
                    if (friendsAPI != null) {
                        for (user in friendsAPI){
                            joinGame.clear()
                            if (!joinGame.contains(user)){
                                joinGame.add(user)
                                //saveFriend(user)
                            }
                        }
                        if(joinGame.size != 0) {
                            joinGameLiveData.value = (joinGame)
                        }
                        //friends2 = friendsAPI
                    }
                }
            }
        })
    }

    fun selectJoinGame(item: MatchInviteModel){
        chosenJoinGame.value = item
        println(chosenJoinGame)
    }

    fun createMatchAPI(newMInvite: APIMatch,activity: Activity?,view: View) {
        val service = getRetrofit().create(MatchInviteRemoteRepository::class.java)
        val call =  service.createMatch(newMInvite)
        call.enqueue(object : Callback<APIMessageResponse> {
            override fun onFailure(call: Call<APIMessageResponse>, t: Throwable) {
                println(t.message)

            }
            override fun onResponse(call: Call<APIMessageResponse>, response: Response<APIMessageResponse>) {

                if(response.body() != null){
                    val friendsAPI = response.body()
                    if (friendsAPI != null) {
                        Toast.makeText(activity, "Match Created", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(view).popBackStack()
                    }
                }
            }
        })
    }

    fun rejectMatchInviteAPI(invite: APIRejectInvite, activity: Activity?, view: View) {
        val service = getRetrofit().create(MatchInviteRemoteRepository::class.java)
        val call =  service.rejectMatchInvite(invite)
        call.enqueue(object : Callback<APIMessageResponse> {
            override fun onFailure(call: Call<APIMessageResponse>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<APIMessageResponse>, response: Response<APIMessageResponse>) {
                if(response.body() != null){
                    val userAPI = response.body()
                    if (userAPI != null) {
                        Toast.makeText(activity, "Match Rejected", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(view).popBackStack()
                        Navigation.findNavController(view).popBackStack()
                    }
                }
            }
        })
    }

    fun acceptMatchInviteAPI(invite: APIAcceptMatchInvite, activity: FragmentActivity?, view: View) {
        val service = getRetrofit().create(MatchInviteRemoteRepository::class.java)
        val call =  service.acceptMatchInvite(invite)
        call.enqueue(object : Callback<APIMessageResponse> {
            override fun onFailure(call: Call<APIMessageResponse>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<APIMessageResponse>, response: Response<APIMessageResponse>) {
                if(response.body() != null){
                    val userAPI = response.body()
                    if (userAPI != null) {
                        Toast.makeText(activity, "Match Accepted", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(view).popBackStack()
                        Navigation.findNavController(view).popBackStack()
                    }
                }
            }
        })
    }

    fun getDealCardsAPI(username: String) {
        val service = getRetrofit().create(UsersRemoteRepository::class.java)
        val call =  service.getdealcards(username)
        call.enqueue(object : Callback<APIMessageResponse> {
            override fun onFailure(call: Call<APIMessageResponse>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<APIMessageResponse>, response: Response<APIMessageResponse>) {
                if(response.body() != null){
                    val userAPI = response.body()
                    if (userAPI != null) {

                    }
                }
            }
        })
    }
}