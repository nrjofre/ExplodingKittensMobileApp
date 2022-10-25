package com.example.explodingkittensapp.navigation

import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.ui.view.friend.FriendFragment

class Navigator(val activity: MainActivity?) {

    fun navigateToFriendDetail(){
        activity?.findNavController(R.id.fragment)?.navigate(R.id.action_friendsFragment_to_friendDetails)
    }
    fun navigateToAddFriendsDetail(){
        activity?.findNavController(R.id.fragment)?.navigate(R.id.action_addFriendsFragment_to_addFriendsDetails)
    }
    fun navigateToFriendsRequestsDetail(){
        activity?.findNavController(R.id.fragment)?.navigate(R.id.action_friendsRequestsFragment_to_friendsRequestsDetails)
    }
    fun navigateToJoinGameDetail(){
        activity?.findNavController(R.id.fragment)?.navigate(R.id.action_joinGameFragment_to_joinGameDetails)
    }
    fun navigateToNewGameDetail(){
        activity?.findNavController(R.id.fragment)?.navigate(R.id.action_newGameFragment_to_newGameDetails)
    }
    fun navigateToGame(){
        activity?.findNavController(R.id.fragment)?.navigate(R.id.action_myGamesFragment_to_gameFragment)
    }
}