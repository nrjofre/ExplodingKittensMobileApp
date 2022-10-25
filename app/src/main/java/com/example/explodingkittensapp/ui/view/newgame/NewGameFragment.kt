package com.example.explodingkittensapp.ui.view.newgame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explodingkittensapp.APImodels.Bodies.APIMatch
import com.example.explodingkittensapp.APImodels.Bodies.APIMinvite
import com.example.explodingkittensapp.APImodels.Bodies.APIRejectInvite
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.activities.OnClickListener
import com.example.explodingkittensapp.model.UserModel
import com.example.explodingkittensapp.ui.viewmodel.JoinGameViewModel
import com.example.explodingkittensapp.ui.viewmodel.NewGameViewModel
import com.example.explodingkittensapp.ui.viewmodel.UserViewModel
import kotlin.concurrent.thread


class NewGameFragment : Fragment(), OnClickListener {

    lateinit var adapter: NewGameRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    private val newGameViewModel: NewGameViewModel by activityViewModels()
    private val viewModel: JoinGameViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newGameViewModel.setNavigator(activity as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val uname = userViewModel.uname
        val view = inflater.inflate(R.layout.fragment_new_game, container, false)
        newGameViewModel.newGameAPI(uname)
        //println(friendViewModel.friends)
        recyclerView = view.findViewById(R.id.newGameRecyclerView)
        adapter = NewGameRecyclerViewAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity,1, LinearLayoutManager.VERTICAL,false)

        newGameViewModel.newGameLiveData.observe(viewLifecycleOwner, Observer {
            adapter.set(it)
        })
        val createbtn : Button = view.findViewById(R.id.createGamebtn)

        val nameedittext: EditText = view.findViewById<EditText>(R.id.GameNameEditText)

        createbtn.setOnClickListener {
            val settings = mutableListOf<String>()
            val participants = mutableListOf<String>()
            participants.add(userViewModel.uname)

            val invites = newGameViewModel.invites

            if(nameedittext.text.isEmpty()){
                nameedittext.error = "Game Name required required"
                nameedittext.requestFocus()
                return@setOnClickListener
            }
            else{
                val newMatch = APIMatch(nameedittext.text.toString(),userViewModel.uname,settings, participants)
                viewModel.createMatchAPI(newMatch, activity, view)

                Thread.sleep(2000)

                for (username in invites){
                    var newInvite = APIMinvite(nameedittext.text.toString(), username, userViewModel.uname)
                    newGameViewModel.createInviteMatchAPI(newInvite)
                }

                viewModel.getDealCardsAPI(userViewModel.uname)
            }


        }

        return view
    }
    override fun onClickItem(item: Any) {
        if (item is UserModel){
            if(newGameViewModel.invites.size < 4){
                newGameViewModel.invites.add(item.username)
                newGameViewModel.newGame.remove(item)
                newGameViewModel.newGameLiveData.value = newGameViewModel.newGame
            }
            else{
                Toast.makeText(activity, "Can not invite more than 4 players", Toast.LENGTH_LONG).show()
            }

        }
    }


}