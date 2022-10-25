package com.example.explodingkittensapp.ui.view.signin

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.ui.viewmodel.UserViewModel
import org.w3c.dom.Text


class ProfileFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels()

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 1000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val email : TextView = view.findViewById(R.id.profileEmail)
        val profileusername : TextView = view.findViewById(R.id.profileUserName)
        val profilematches : TextView = view.findViewById(R.id.profileMatches)
        val profilewinrate : TextView = view.findViewById(R.id.profileWinrate)
        val profilewins : TextView = view.findViewById(R.id.profileWins)
        val profileloses : TextView = view.findViewById(R.id.profileLoses)
        val profiledefuses : TextView = view.findViewById(R.id.profileDefuses)

        email.text = "Email: "+ viewModel.profile.email
        profileusername.text = viewModel.profile.username
        profilematches.text = "Total Matches: "+ viewModel.profile.total_matches
        profilewinrate.text = "WinRate: "+ viewModel.profile.winrate
        profilewins.text = "Wins: "+ viewModel.profile.wins
        profileloses.text = "Loses: "+ viewModel.profile.loses
        profiledefuses.text = "Defuses: "+ viewModel.profile.defuses

        return view
    }

    // para que corra cada 5 segundos
    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            //updatea los participantes
            val uname = viewModel.uname
            viewModel.getProfileAPI(uname)

            val email = view?.findViewById<TextView>(R.id.profileEmail)
            val profileusername = view?.findViewById<TextView>(R.id.profileUserName)
            val profilematches = view?.findViewById<TextView>(R.id.profileMatches)
            val profilewinrate = view?.findViewById<TextView>(R.id.profileWinrate)
            val profilewins = view?.findViewById<TextView>(R.id.profileWins)
            val profileloses = view?.findViewById<TextView>(R.id.profileLoses)
            val profiledefuses = view?.findViewById<TextView>(R.id.profileDefuses)
            val profileshuffles = view?.findViewById<TextView>(R.id.profileShuffle)
            val profileattacks = view?.findViewById<TextView>(R.id.profileAttacks)
            val profileskips = view?.findViewById<TextView>(R.id.profileSkip)

            email?.text = "Email: "+ viewModel.profile.email
            profileusername?.text = viewModel.profile.username
            profilematches?.text = "Total Matches: "+ viewModel.profile.total_matches
            profilewinrate?.text = "WinRate: "+ viewModel.profile.winrate
            profilewins?.text = "Wins: "+ viewModel.profile.wins
            profileloses?.text = "Loses: "+ viewModel.profile.loses
            profiledefuses?.text = "Defuses: "+ viewModel.profile.defuses
            profileshuffles?.text = "Shuffles: "+ viewModel.profile.shuffles
            profileattacks?.text = "Attacks: "+ viewModel.profile.attacks
            profileskips?.text = "Skips: "+ viewModel.profile.skips

        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
    }

}