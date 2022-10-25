package com.example.explodingkittensapp.ui.view.friendsrequests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.explodingkittensapp.APImodels.Bodies.APIAcceptInvite
import com.example.explodingkittensapp.APImodels.Bodies.APIFinvite
import com.example.explodingkittensapp.APImodels.Bodies.APIRejectInvite
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.model.FriendInviteModel
import com.example.explodingkittensapp.ui.viewmodel.FriendsRequestsViewModel
import com.example.explodingkittensapp.ui.viewmodel.UserViewModel

class FriendsRequestsDetails : Fragment() {

    private val viewModel: FriendsRequestsViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(activity as MainActivity)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.friends_requests_details_fragment, container, false)
        val selected = viewModel.chosenFriendsRequests.value

        val username = view.findViewById<TextView>(R.id.friendsRequestsUserName)

        val acceptfriendbtn : Button = view.findViewById(R.id.acceptfriendbtn)
        val rejectfriendbtn : Button = view.findViewById(R.id.rejectfriendbtn)

        if (selected != null) {
            username.text = selected.invitor
        }

        acceptfriendbtn.setOnClickListener {
            val inviteid = selected?.id
            val invitedusr = userViewModel.uname
            val invitorusr = selected?.invitor

            val invite = APIAcceptInvite(inviteid.toString(), invitedusr, invitorusr.toString())

            viewModel.acceptInviteAPI(invite,activity,view)

            viewModel.friendsRequests.remove(selected)
            viewModel.friendsRequestsLiveData.value = viewModel.friendsRequests

        }

        rejectfriendbtn.setOnClickListener {
            val inviteid = selected?.id

            val invite = APIRejectInvite(inviteid.toString())

            viewModel.rejectInviteAPI(invite,activity,view)

            for (item: FriendInviteModel in viewModel.friendsRequests) {
                if (item.id == selected?.id){
                    viewModel.friendsRequests.remove(item)
                    viewModel.friendsRequestsLiveData.value = viewModel.friendsRequests
                }
            }
        }

        return view
    }
}