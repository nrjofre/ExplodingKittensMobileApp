package com.example.explodingkittensapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.explodingkittensapp.APImodels.Bodies.APIUser
import com.example.explodingkittensapp.APImodels.Responses.APISigninResponse
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.database.DatabaseRepository
import com.example.explodingkittensapp.database.UserDao
import com.example.explodingkittensapp.database.UserEntityMapper
import com.example.explodingkittensapp.model.UserModel
import com.example.explodingkittensapp.navigation.Navigator
import com.example.explodingkittensapp.networking.UsersRemoteRepository
import com.example.explodingkittensapp.networking.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FriendViewModel(application: Application) : AndroidViewModel(application) {

    val app = application
    var friends: MutableList<UserModel> = mutableListOf()
    var friendsLiveData = MutableLiveData<MutableList<UserModel>>()
    val chosenFriend = MutableLiveData<UserModel>()

    lateinit var navigator: Navigator

    var database: UserDao
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    init{
        database = DatabaseRepository(application).postUserDao()
        loadFriends()
    }

    fun setNavigator(activity: MainActivity?) {
        navigator = Navigator(activity)
    }

    fun loadFriends() {
        executor.execute {
            friends = database.getAllUsers().map {
                UserEntityMapper().mapFromCached(it)
            } as MutableList<UserModel>
            println(friends)
            if(friends.size != 0) {
                friendsLiveData.postValue(friends)
            }
        }
    }


    fun friendsAPI(username: String){
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
                        friends.clear()
                        for (user in friendsAPI){
                            if (!friends.contains(user)){
                                friends.add(user)
                            }
                        }
                        if(friends.size != 0) {
                            friendsLiveData.value =(friends)
                        }
                    }
                }
            }
        })
    }

    fun selectFriend(item: UserModel){
        chosenFriend.value = item
        println(chosenFriend)
    }
}