package com.example.explodingkittensapp.ui.view.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.explodingkittensapp.R
import com.example.explodingkittensapp.activities.AdapterView
import com.example.explodingkittensapp.activities.OnClickListener
import com.example.explodingkittensapp.model.CardModel

class PlayerDeckRecyclerViewAdapter(override val onClickListener: OnClickListener): RecyclerView.Adapter<PlayerDeckRecyclerViewAdapter.UserViewHolder>(),
    AdapterView {
    var data = listOf<CardModel>()
    var isClickable = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_deck_item,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {

        val item = data[position]
        holder.bindView(item)

        holder.itemView.setOnClickListener{
            if(isClickable) {
                onClickListener.onClickItem(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun addItem(item: Any) {
        if (item is CardModel){
            notifyDataSetChanged()
        }
        this.notifyDataSetChanged()
    }

    fun set(users: List<CardModel>){
        this.data = users
        this.notifyDataSetChanged()
    }


    inner class UserViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        fun bindView(item: CardModel){
            val userNameTextView: TextView = view.findViewById(R.id.userName)
            userNameTextView.text = item.type
        }

    }
}