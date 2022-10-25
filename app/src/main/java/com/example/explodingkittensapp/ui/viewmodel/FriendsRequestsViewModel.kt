package com.example.explodingkittensapp.ui.viewmodel

import android.app.Activity
import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.example.explodingkittensapp.APImodels.Bodies.APIAcceptInvite
import com.example.explodingkittensapp.APImodels.Bodies.APIRejectInvite
import com.example.explodingkittensapp.APImodels.Bodies.APIUser
import com.example.explodingkittensapp.APImodels.Responses.APIMessageResponse
import com.example.explodingkittensapp.APImodels.Responses.APISigninResponse
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.database.*
import com.example.explodingkittensapp.model.FriendInviteModel
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

class FriendsRequestsViewModel(application: Application) : AndroidViewModel(application) {

    val app = application
    var friendsRequests: MutableList<FriendInviteModel> = mutableListOf()
    var friendsRequestsLiveData = MutableLiveData<MutableList<FriendInviteModel>>()
    val chosenFriendsRequests = MutableLiveData<FriendInviteModel>()

    lateinit var navigator: Navigator

    //lateinit var navigator: Navigator

    var database: FriendInviteDao
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    init{
        database = DatabaseRepository(application).postFriendInviteDao()
        loadFriendsRequests()
    }

    fun setNavigator(activity: MainActivity?) {
        navigator = Navigator(activity)
    }
    //DB Methods
    fun saveFriendsRequests(invite: FriendInviteModel) {
        executor.execute {
            database.insertFriendInvite(FriendInviteEntityMapper().mapToCached(invite))
            friendsRequests.add(invite)
            friendsRequestsLiveData.postValue(friendsRequests)
        }
    }

    fun loadFriendsRequests() {
        executor.execute {
            friendsRequests = database.getAllFriendInvites().map {
                FriendInviteEntityMapper().mapFromCached(it)
            } as MutableList<FriendInviteModel>
            println(friendsRequests)
            if(friendsRequests.size != 0) {
                friendsRequestsLiveData.postValue(friendsRequests)
            }
        }
    }

    //API Methods
    fun acceptInviteAPI(newAccepted: APIAcceptInvite, activity: Activity?, view: View){
        val service = getRetrofit().create(FriendInviteRemoteRepository::class.java)
        val call =  service.acceptUserInvite(newAccepted)
        call.enqueue(object : Callback<APIMessageResponse> {
            override fun onFailure(call: Call<APIMessageResponse>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<APIMessageResponse>, response: Response<APIMessageResponse>) {
                if(response.body() != null){
                    val userAPI = response.body()
                    if (userAPI != null) {
                        Toast.makeText(activity, "User "+ newAccepted.invitor + " acepted", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(view).popBackStack()
                        Navigation.findNavController(view).popBackStack()
                    }
                }
            }
        })
    }

    fun rejectInviteAPI(newRejected: APIRejectInvite, activity: Activity?, view: View){
        val service = getRetrofit().create(FriendInviteRemoteRepository::class.java)
        val call =  service.rejectInvite(newRejected)
        call.enqueue(object : Callback<APIMessageResponse> {
            override fun onFailure(call: Call<APIMessageResponse>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<APIMessageResponse>, response: Response<APIMessageResponse>) {
                if(response.body() != null){
                    val userAPI = response.body()
                    if (userAPI != null) {
                        Toast.makeText(activity, "User Rejected", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(view).popBackStack()
                        Navigation.findNavController(view).popBackStack()
                    }
                }
            }
        })
    }

    fun friendsRequestsAPI(username: String){
        val service = getRetrofit().create(FriendInviteRemoteRepository::class.java)
        val call =  service.getFriendInvites(username)
        call.enqueue(object : Callback<List<FriendInviteModel>> {
            override fun onFailure(call: Call<List<FriendInviteModel>>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<List<FriendInviteModel>>, response: Response<List<FriendInviteModel>>) {
                if(response.body() != null){
                    val friendsAPI = response.body()
                    if (friendsAPI != null) {
                        friendsRequests.clear()
                        for (invite in friendsAPI){
                            if (!friendsRequests.contains(invite)){
                                friendsRequests.add(invite)
                                //saveFriend(user)
                            }
                        }
                        if(friendsRequests.size != 0) {
                            friendsRequestsLiveData.value =(friendsRequests)
                        }
                        //friends2 = friendsAPI
                    }
                }
            }
        })
    }

    fun selectFriendsRequests(item: FriendInviteModel){
        chosenFriendsRequests.value = item
        println(chosenFriendsRequests)
    }
}