package com.example.explodingkittensapp.ui.view.mygames

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.AdapterView
import com.example.explodingkittensapp.activities.OnClickListener
import com.example.explodingkittensapp.model.MatchModel
import com.example.explodingkittensapp.model.UserModel

class MyGamesRecyclerViewAdapter(override val onClickListener: OnClickListener): RecyclerView.Adapter<MyGamesRecyclerViewAdapter.UserViewHolder>(),
    AdapterView {
    var data = listOf<MatchModel>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_games_item,parent,false)
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
        if (item is MatchModel){
            notifyDataSetChanged()
        }
        this.notifyDataSetChanged()
    }

    fun set(users: List<MatchModel>){
        this.data = users
        this.notifyDataSetChanged()
    }


    inner class UserViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bindView(item: MatchModel){
            val userNameTextView: TextView = view.findViewById(R.id.myGamesUserName)
            userNameTextView.text = item.gamename
        }

    }
}

