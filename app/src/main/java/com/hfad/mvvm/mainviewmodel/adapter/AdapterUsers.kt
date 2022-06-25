package com.hfad.mvvm.mainviewmodel.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hfad.mvvm.databinding.ItemUsersBinding
import com.hfad.mvvm.model.Users
import com.squareup.picasso.Picasso

class AdapterUsers (private val listener: SetOnClickListener): RecyclerView.Adapter<AdapterUsers.ViewHolder>() {

    var users = mutableListOf<Users>()


    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(users: ArrayList<Users>) {
        this.users = users.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val users = users[position]
        holder.binding.userIdTextView.text = users.id.toString()
        holder.binding.userLoginTextView.text = users.login
        Picasso.get().load(users.avatar_url).into(holder.binding.usersImageView)

        holder.itemView.setOnClickListener {
            listener.onClickUser(users.login)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(var binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root)

    interface SetOnClickListener{
        fun onClickUser(login: String)
    }
}