package com.example.explodingkittensapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.explodingkittensapp.R


class HomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)

        val logoutbtn : Button = view.findViewById(R.id.logoutbtn)

        logoutbtn.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_homeScreenFragment_to_loginFragment)}

        val newgamebtn : Button = view.findViewById(R.id.newgamebtn)

        newgamebtn.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_homeScreenFragment_to_newGameFragment)}

        val joingamebtn : Button = view.findViewById(R.id.joingamebtn)

        joingamebtn.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_homeScreenFragment_to_joinGameFragment)}

        val mygamesbtn : Button = view.findViewById(R.id.mygamesbtn)

        mygamesbtn.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_homeScreenFragment_to_myGamesFragment)}

        val profilebtn : Button = view.findViewById(R.id.profilebtn)

        profilebtn.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_homeScreenFragment_to_profileFragment)}

        val friendsbtn : Button = view.findViewById(R.id.friendsbtn)

        friendsbtn.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_homeScreenFragment_to_friendsFragment)}

        return view

    }

}