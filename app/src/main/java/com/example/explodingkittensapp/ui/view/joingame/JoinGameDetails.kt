package com.example.explodingkittensapp.ui.view.joingame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.explodingkittensapp.APImodels.Bodies.APIAcceptInvite
import com.example.explodingkittensapp.APImodels.Bodies.APIAcceptMatchInvite
import com.example.explodingkittensapp.APImodels.Bodies.APIRejectInvite
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.model.FriendInviteModel
import com.example.explodingkittensapp.model.MatchInviteModel
import com.example.explodingkittensapp.ui.viewmodel.FriendViewModel
import com.example.explodingkittensapp.ui.viewmodel.FriendsRequestsViewModel
import com.example.explodingkittensapp.ui.viewmodel.JoinGameViewModel
import com.example.explodingkittensapp.ui.viewmodel.UserViewModel


class JoinGameDetails : Fragment() {

    private val viewModel: JoinGameViewModel by activityViewModels()
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
        val view = inflater.inflate(R.layout.join_game_details_fragment, container, false)
        val selected = viewModel.chosenJoinGame.value

        val mid = view.findViewById<TextView>(R.id.joinGameName)
        val username = view.findViewById<TextView>(R.id.joinGameMatchId)

        val acceptmatchbtn : Button = view.findViewById(R.id.joinGameAccept)
        val rejectmatchbtn : Button = view.findViewById(R.id.joinGameReject)

        if (selected != null) {
            username.text = "Invited by: " + selected.invitor
            mid.text = selected.matchid
        }


        acceptmatchbtn.setOnClickListener {
            val inviteid = selected?.id
            val invite = APIAcceptMatchInvite(inviteid.toString())

            viewModel.acceptMatchInviteAPI(invite,activity,view)

            viewModel.joinGame.remove(selected)
            viewModel.joinGameLiveData.value = viewModel.joinGame
            val username = selected?.invited
            if (username != null) {
                viewModel.getDealCardsAPI(username)
            }


        }

        rejectmatchbtn.setOnClickListener {
            val inviteid = selected?.id
            val invite = APIRejectInvite(inviteid.toString())

            viewModel.rejectMatchInviteAPI(invite,activity,view)

            viewModel.joinGame.remove(selected)
            viewModel.joinGameLiveData.value = viewModel.joinGame

        }

        return view
    }
}