package com.example.explodingkittensapp.ui.viewmodel

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.example.explodingkittensapp.*
import com.example.explodingkittensapp.APImodels.Bodies.APILogin
import com.example.explodingkittensapp.APImodels.Bodies.APIUser
import com.example.explodingkittensapp.APImodels.Responses.APILoginResponse
import com.example.explodingkittensapp.APImodels.Responses.APISigninResponse
import com.example.explodingkittensapp.database.DatabaseRepository
import com.example.explodingkittensapp.database.UserDao
import com.example.explodingkittensapp.database.UserEntityMapper
import com.example.explodingkittensapp.model.*
import com.example.explodingkittensapp.networking.UsersRemoteRepository
import com.example.explodingkittensapp.networking.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class UserViewModel(application: Application) : AndroidViewModel(application) {

    val app = application
    var users: MutableList<UserModel> = mutableListOf()
    var usersLiveData = MutableLiveData<MutableList<UserModel>>()
    lateinit var uname: String
    lateinit var id: String
    var profile = UserModel("", "", "", "", 0, 0.0, listOf(), listOf(), "", 0, 0, 0,0,0,0)

    var database: UserDao
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    init{
        database = DatabaseRepository(application).postUserDao()
        loadUsers()
        val service = getRetrofit().create(UsersRemoteRepository::class.java)
    }

    fun loadUsers() {
        executor.execute {
            users = database.getAllUsers().map {
                UserEntityMapper().mapFromCached(it)
            } as MutableList<UserModel>
            if(users.size != 0) {
                usersLiveData.postValue(users)
            }
        }
    }

    //API Methods
    fun addUserAPI(newUser: APIUser){
        val service = getRetrofit().create(UsersRemoteRepository::class.java)
        val call =  service.createUser(newUser)
        call.enqueue(object :  Callback<APISigninResponse> {
            override fun onFailure(call: Call<APISigninResponse>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<APISigninResponse>, response: Response<APISigninResponse>) {
                if(response.body() != null){
                    val userAPI = response.body()
                    if (userAPI != null) {
                        println(userAPI)
                        //val usr = UserModel(userAPI.id,userAPI.email,userAPI.username,userAPI.password,userAPI.total_matches,userAPI.winrate,userAPI.friends,userAPI.cards,"")
                        //saveUser(usr)
                    }
                }
            }
        })
    }

    fun loginUserAPI(loginUser: APILogin, view: View, activity: FragmentActivity?){
        val service = getRetrofit().create(UsersRemoteRepository::class.java)
        val call =  service.loginUser(loginUser)
        call.enqueue(object :  Callback<APILoginResponse> {
            override fun onFailure(call: Call<APILoginResponse>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<APILoginResponse>, response: Response<APILoginResponse>) {
                if(response.body() != null){
                    val userAPI = response.body()
                    if (userAPI != null) {
                        println(userAPI)
                        uname = userAPI.username
                        id = userAPI.id
                        getProfileAPI(userAPI.username)
                        Toast.makeText(activity, "Welcome $uname", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeScreenFragment)
                    }
                }
            }
        })
    }

    fun getProfileAPI(username: String){
        val service = getRetrofit().create(UsersRemoteRepository::class.java)
        val call =  service.getProfile(username)
        call.enqueue(object :  Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if(response.body() != null){
                    val userAPI = response.body()
                    if (userAPI != null) {
                        println(userAPI)
                        profile.id = userAPI.id
                        profile.email = userAPI.email
                        profile.username = userAPI.username
                        profile.winrate = userAPI.winrate
                        profile.total_matches = userAPI.total_matches
                        profile.wins = userAPI.wins
                        profile.loses = userAPI.loses
                        profile.defuses = userAPI.defuses
                        profile.shuffles = userAPI.shuffles
                        profile.attacks = userAPI.attacks
                        profile.skips = userAPI.skips
                    }
                }
            }
        })
    }
}