package com.example.explodingkittensapp.ui.view.friendsrequests

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.activities.OnClickListener
import com.example.explodingkittensapp.model.FriendInviteModel
import com.example.explodingkittensapp.ui.viewmodel.FriendsRequestsViewModel
import com.example.explodingkittensapp.ui.viewmodel.UserViewModel


class FriendsRequestsFragment : Fragment(), OnClickListener {

    lateinit var adapter: FriendsRequestsRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    private val friendsRequestsViewModel: FriendsRequestsViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        friendsRequestsViewModel.setNavigator(activity as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val uname = userViewModel.uname
        val view = inflater.inflate(R.layout.fragment_friends_requests, container, false)
        friendsRequestsViewModel.friendsRequestsAPI(uname)

        recyclerView = view.findViewById(R.id.friendsRequestsRecyclerView)
        adapter = FriendsRequestsRecyclerViewAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity,1, LinearLayoutManager.VERTICAL,false)

        friendsRequestsViewModel.friendsRequestsLiveData.observe(viewLifecycleOwner, Observer {
            adapter.set(it)
        })


        return view
    }

    override fun onClickItem(item: Any) {
        if (item is FriendInviteModel){
            friendsRequestsViewModel.selectFriendsRequests(item)
            friendsRequestsViewModel.navigator.navigateToFriendsRequestsDetail()
        }
    }

    // para que corra cada 5 segundos
    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            //updatea los participantes
            val uname = userViewModel.uname
            friendsRequestsViewModel.friendsRequestsAPI(uname)

        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
    }
}