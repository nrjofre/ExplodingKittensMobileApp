package com.example.explodingkittensapp.ui.view.addfriends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.explodingkittensapp.APImodels.Bodies.APIFinvite
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.model.FriendInviteModel
import com.example.explodingkittensapp.model.UserModel
import com.example.explodingkittensapp.ui.viewmodel.AddFriendsViewModel
import com.example.explodingkittensapp.ui.viewmodel.UserViewModel

class AddFriendsDetails : Fragment() {

    private val addFriendsViewModel: AddFriendsViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFriendsViewModel.setNavigator(activity as MainActivity)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_friends_details_fragment, container, false)
        val selected = addFriendsViewModel.chosenAddFriends.value

        val username = view.findViewById<TextView>(R.id.addFriendsUserName)
        val winrate = view.findViewById<TextView>(R.id.addFriendsUserWinrate)
        val email = view.findViewById<TextView>(R.id.addFriendsUserEmail)
        val matches= view.findViewById<TextView>(R.id.addFriendsUserMatches)

        val addfriendbtn : Button = view.findViewById(R.id.addfriendbtn)

        if (selected != null) {
            username.text = selected.username
            winrate.text = selected.winrate.toString()
            email.text = selected.email
            matches.text = selected.total_matches.toString()
        }

        addfriendbtn.setOnClickListener {
            val invitedusr = selected?.username
            val invitorusr = userViewModel.uname

            val newInvite = APIFinvite(invitedusr.toString(),invitorusr)

            addFriendsViewModel.createInviteAPI(newInvite,activity,view)

            addFriendsViewModel.addfriends.remove(selected)

            addFriendsViewModel.addFriendsLiveData.value = addFriendsViewModel.addfriends

        }
        return view
    }


}