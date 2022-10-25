package com.example.explodingkittensapp.ui.view.newgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.ui.viewmodel.FriendViewModel
import com.example.explodingkittensapp.ui.viewmodel.NewGameViewModel

class NewGameDetails : Fragment() {

    private val viewModel: NewGameViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(activity as MainActivity)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.new_game_details_fragment, container, false)
        val selected = viewModel.chosenNewGame.value

        val username = view.findViewById<TextView>(R.id.newGameUserName)
        val winrate = view.findViewById<TextView>(R.id.NewGameUserWinrate)
        val email = view.findViewById<TextView>(R.id.NewGameUserEmail)
        val matches= view.findViewById<TextView>(R.id.NewGameUserMatches)

        if (selected != null) {
            username.text = selected.username
            winrate.text = selected.winrate.toString()
            email.text = selected.email
            matches.text = selected.total_matches.toString()
        }

        return view
    }
}