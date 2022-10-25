package com.example.explodingkittensapp.ui.view.friendsrequests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.AdapterView
import com.example.explodingkittensapp.activities.OnClickListener
import com.example.explodingkittensapp.model.FriendInviteModel
import com.example.explodingkittensapp.model.UserModel

class FriendsRequestsRecyclerViewAdapter(override val onClickListener: OnClickListener): RecyclerView.Adapter<FriendsRequestsRecyclerViewAdapter.UserViewHolder>(),
    AdapterView {
    var data = listOf<FriendInviteModel>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_requests_item,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {
        val item = data[position]
        holder.bindView(item)
        holder.itemView.setOnClickListener{
            onClickListener.onClickItem(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun addItem(item: Any) {
        if (item is UserModel){
            notifyDataSetChanged()
        }
        this.notifyDataSetChanged()
    }

    fun set(users: MutableList<FriendInviteModel>){
        this.data = users
        this.notifyDataSetChanged()
    }


    inner class UserViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bindView(item: FriendInviteModel){
            val userNameTextView: TextView = view.findViewById(R.id.friendsRequestsUserName)
            userNameTextView.text = item.invitor
        }
    }
}