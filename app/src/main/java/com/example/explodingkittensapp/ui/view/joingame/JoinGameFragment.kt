package com.example.explodingkittensapp.ui.view.joingame

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.activities.OnClickListener
import com.example.explodingkittensapp.model.FriendInviteModel
import com.example.explodingkittensapp.model.MatchInviteModel
import com.example.explodingkittensapp.model.UserModel
import com.example.explodingkittensapp.ui.view.friend.FriendRecyclerViewAdapter
import com.example.explodingkittensapp.ui.view.friendsrequests.FriendsRequestsRecyclerViewAdapter
import com.example.explodingkittensapp.ui.viewmodel.FriendViewModel
import com.example.explodingkittensapp.ui.viewmodel.FriendsRequestsViewModel
import com.example.explodingkittensapp.ui.viewmodel.JoinGameViewModel
import com.example.explodingkittensapp.ui.viewmodel.UserViewModel


class JoinGameFragment : Fragment(), OnClickListener {

    lateinit var adapter: JoinGameRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    private val joinGameViewModel: JoinGameViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        joinGameViewModel.setNavigator(activity as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val uname = userViewModel.uname
        val view = inflater.inflate(R.layout.fragment_join_game, container, false)
        joinGameViewModel.joinGameAPI(uname)
        //println(friendViewModel.friends)
        recyclerView = view.findViewById(R.id.joinGameRecyclerView)
        adapter = JoinGameRecyclerViewAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity,1, LinearLayoutManager.VERTICAL,false)

        joinGameViewModel.joinGameLiveData.observe(viewLifecycleOwner, Observer {
            adapter.set(it)
        })

        return view
    }

    override fun onClickItem(item: Any) {
        if (item is MatchInviteModel){
            joinGameViewModel.selectJoinGame(item)
            joinGameViewModel.navigator.navigateToJoinGameDetail()
        }
    }

    // para que corra cada 5 segundos
    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            //updatea los participantes
            val uname = userViewModel.uname
            joinGameViewModel.joinGameAPI(uname)

        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
    }
}