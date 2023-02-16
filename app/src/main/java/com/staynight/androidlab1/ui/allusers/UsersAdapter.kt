package com.staynight.androidlab1.ui.allusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.staynight.androidlab1.R
import com.staynight.androidlab1.databinding.ItemUserBinding
import com.staynight.domain.model.User

class UsersAdapter(
    private val users: List<User>
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder =
        UsersViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    inner class UsersViewHolder(private val item: ItemUserBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(user: User) {
            item.apply {
                tvUserId.text = String.format(
                    tvUserId.context.getString(R.string.id_holder),
                    (adapterPosition + 1).toString()
                )
                tvUserName.text = String.format(
                    tvUserId.context.getString(R.string.name_holder),
                    user.name
                )
                tvUserAge.text = String.format(
                    tvUserId.context.getString(R.string.age_holder),
                    user.age
                )
                tvUserEmail.text = String.format(
                    tvUserId.context.getString(R.string.email_holder),
                    user.email
                )
            }
        }
    }
}