package com.example.explodingkittensapp.ui.view.friend

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.MainActivity
import com.example.explodingkittensapp.activities.OnClickListener
import com.example.explodingkittensapp.model.UserModel
import com.example.explodingkittensapp.ui.viewmodel.FriendViewModel
import com.example.explodingkittensapp.ui.viewmodel.UserViewModel


class FriendFragment : Fragment(), OnClickListener {

    lateinit var adapter: FriendRecyclerViewAdapter
    lateinit var recyclerView: RecyclerView
    private val friendViewModel: FriendViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    var handler: Handler = Handler()
    var runnable: Runnable? = null
    var delay = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        friendViewModel.setNavigator(activity as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val uname = userViewModel.uname
        val view = inflater.inflate(R.layout.fragment_friends, container, false)
        friendViewModel.friendsAPI(uname)
        //println(friendViewModel.friends)
        recyclerView = view.findViewById(R.id.friendRecyclerView)
        adapter = FriendRecyclerViewAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity,1, LinearLayoutManager.VERTICAL,false)

        friendViewModel.friendsLiveData.observe(viewLifecycleOwner, Observer {
            adapter.set(it)
        })

        val addFriendsbtn : Button = view.findViewById(R.id.addFriendsbtn)
        val friendRequestsbtn : Button = view.findViewById(R.id.friendRequestsbtn)

        addFriendsbtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_friendsFragment_to_addFriendsFragment)
        }
        friendRequestsbtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_friendsFragment_to_friendsRequestsFragment)
        }

        return view
    }

    override fun onClickItem(item: Any) {
        if (item is UserModel){
            friendViewModel.selectFriend(item)
            friendViewModel.navigator.navigateToFriendDetail()
        }
    }

    // para que corra cada 5 segundos
    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            //updatea los participantes
            val uname = userViewModel.uname
            friendViewModel.friendsAPI(uname)

        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
    }
}