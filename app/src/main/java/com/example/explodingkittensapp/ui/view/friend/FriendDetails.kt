package com.example.explodingkittensapp.ui.view.friend

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.ui.viewmodel.FriendViewModel

class FriendDetails : Fragment() {

    private val viewModel: FriendViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(activity as MainActivity)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.friend_details_fragment, container, false)
        val selected = viewModel.chosenFriend.value

        val username = view.findViewById<TextView>(R.id.userName)
        val winrate = view.findViewById<TextView>(R.id.userWinrate)
        val email = view.findViewById<TextView>(R.id.userEmail)
        val matches= view.findViewById<TextView>(R.id.userMatches)

        if (selected != null) {
            username.text = selected.username
            winrate.text = selected.winrate.toString()
            email.text = selected.email
            matches.text = selected.total_matches.toString()
        }

        return view
    }
}