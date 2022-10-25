package com.example.explodingkittensapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.explodingkittensapp.APImodels.Bodies.APIAcceptMatchInvite
import com.example.explodingkittensapp.APImodels.Bodies.APIMinvite
import com.example.explodingkittensapp.APImodels.Bodies.APIUser
import com.example.explodingkittensapp.APImodels.Responses.APIMessageResponse
import com.example.explodingkittensapp.APImodels.Responses.APISigninResponse
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.database.DatabaseRepository
import com.example.explodingkittensapp.database.UserDao
import com.example.explodingkittensapp.database.UserEntityMapper
import com.example.explodingkittensapp.model.UserModel
import com.example.explodingkittensapp.navigation.Navigator
import com.example.explodingkittensapp.networking.MatchInviteRemoteRepository
import com.example.explodingkittensapp.networking.UsersRemoteRepository
import com.example.explodingkittensapp.networking.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NewGameViewModel(application: Application) : AndroidViewModel(application) {

    val app = application
    var newGame: MutableList<UserModel> = mutableListOf()
    var newGameLiveData = MutableLiveData<MutableList<UserModel>>()
    val chosenNewGame = MutableLiveData<UserModel>()

    var invites: MutableList<String> = mutableListOf()

    lateinit var navigator: Navigator

    //lateinit var navigator: Navigator

    var database: UserDao
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    init{
        database = DatabaseRepository(application).postUserDao()
        loadNewGame()
        val service = getRetrofit().create(UsersRemoteRepository::class.java)
    }

    fun setNavigator(activity: MainActivity?) {
        navigator = Navigator(activity)
    }

    //DB Methods

    fun loadNewGame() {
        executor.execute {
            newGame = database.getAllUsers().map {
                UserEntityMapper().mapFromCached(it)
            } as MutableList<UserModel>
            println(newGame)
            if(newGame.size != 0) {
                newGameLiveData.postValue(newGame)
            }
        }
    }

    //API Methods
    fun newGameAPI(username: String){
        val service = getRetrofit().create(UsersRemoteRepository::class.java)
        val call =  service.getFriends(username)
        call.enqueue(object : Callback<List<UserModel>> {
            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                if(response.body() != null){
                    val friendsAPI = response.body()
                    if (friendsAPI != null) {
                        newGame.clear()
                        for (user in friendsAPI){
                            if (!newGame.contains(user)){
                                newGame.add(user)
                                //saveFriend(user)
                            }
                        }
                        if(newGame.size != 0) {
                            newGameLiveData.value =(newGame)
                        }
                        //friends2 = friendsAPI
                    }
                }
            }
        })
    }

    fun selectNewGame(item: UserModel){
        chosenNewGame.value = item
        println(chosenNewGame)
    }

    fun createInviteMatchAPI(newMatchInvite: APIMinvite) {
        val service = getRetrofit().create(MatchInviteRemoteRepository::class.java)
        val call =  service.createMatchInvite(newMatchInvite)
        call.enqueue(object : Callback<APIMessageResponse> {
            override fun onFailure(call: Call<APIMessageResponse>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<APIMessageResponse>, response: Response<APIMessageResponse>) {
                if(response.body() != null){
                    val userAPI = response.body()
                    if (userAPI != null) {
                        println(userAPI)
                        //val usr = UserModel(userAPI.id,userAPI.email,userAPI.username,userAPI.password,userAPI.total_matches,userAPI.winrate,userAPI.friends)
                        //saveFriend(usr)
                    }
                }
            }
        })
    }
}