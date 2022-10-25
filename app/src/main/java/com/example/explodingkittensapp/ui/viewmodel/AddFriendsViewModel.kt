package com.example.explodingkittensapp.ui.viewmodel

import android.app.Activity
import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.example.explodingkittensapp.APImodels.Bodies.APIFinvite
import com.example.explodingkittensapp.APImodels.Bodies.APIUser
import com.example.explodingkittensapp.APImodels.Responses.APIMessageResponse
import com.example.explodingkittensapp.APImodels.Responses.APISigninResponse
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.database.DatabaseRepository
import com.example.explodingkittensapp.database.UserDao
import com.example.explodingkittensapp.database.UserEntityMapper
import com.example.explodingkittensapp.model.UserModel
import com.example.explodingkittensapp.navigation.Navigator
import com.example.explodingkittensapp.networking.FriendInviteRemoteRepository
import com.example.explodingkittensapp.networking.UsersRemoteRepository
import com.example.explodingkittensapp.networking.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddFriendsViewModel(application: Application) : AndroidViewModel(application) {

    val app = application
    var addfriends: MutableList<UserModel> = mutableListOf()
    var addFriendsLiveData = MutableLiveData<MutableList<UserModel>>()
    val chosenAddFriends = MutableLiveData<UserModel>()
    lateinit var uname: String

    lateinit var navigator: Navigator

    var database: UserDao
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    init{
        database = DatabaseRepository(application).postUserDao()
        loadAddFriends()
    }

    fun setNavigator(activity: MainActivity?) {
        navigator = Navigator(activity)
    }

    fun loadAddFriends() {
        executor.execute {
            addfriends = database.getAllUsers().map {
                UserEntityMapper().mapFromCached(it)
            } as MutableList<UserModel>
            println(addfriends)
            if(addfriends.size != 0) {
                addFriendsLiveData.postValue(addfriends)
            }
        }
    }

    //API Methods
    fun createInviteAPI(newInvite: APIFinvite,activity: Activity?, view: View){
        val service = getRetrofit().create(FriendInviteRemoteRepository::class.java)
        val call =  service.createInvite(newInvite)
        call.enqueue(object : Callback<APIMessageResponse> {
            override fun onFailure(call: Call<APIMessageResponse>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<APIMessageResponse>, response: Response<APIMessageResponse>) {
                if(response.body() != null){
                    val userAPI = response.body()
                    if (userAPI != null) {
                        println(userAPI)
                        Toast.makeText(activity, "Friend request send to "+ newInvite.invited, Toast.LENGTH_LONG).show()
                        Navigation.findNavController(view).popBackStack()
                    }
                }
            }
        })
    }

    fun getFriendsAPI(username: String){
        val service = getRetrofit().create(UsersRemoteRepository::class.java)
        val call =  service.getNotFriends(username)
        call.enqueue(object : Callback<List<UserModel>> {
            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                if(response.body() != null){
                    val friendsAPI = response.body()
                    if (friendsAPI != null) {
                        addfriends.clear()
                        for (user in friendsAPI){
                            if (!addfriends.contains(user)){
                                addfriends.add(user)
                                //saveFriend(user)
                            }
                        }
                        if(addfriends.size != 0) {
                            addFriendsLiveData.value = (addfriends)
                        }
                    }
                }
            }
        })
    }

    fun selectAddFriends(item: UserModel){
        chosenAddFriends.value = item
        println(chosenAddFriends)
    }
}