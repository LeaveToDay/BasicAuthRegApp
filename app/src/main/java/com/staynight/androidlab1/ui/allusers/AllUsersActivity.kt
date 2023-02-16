package com.staynight.androidlab1.ui.allusers

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.staynight.androidlab1.databinding.ActivityAllUsersBinding
import com.staynight.domain.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllUsersActivity : AppCompatActivity() {
    private var binding: ActivityAllUsersBinding? = null
    private val viewModel: AllUsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllUsersBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupObservers()
        viewModel.getAllUsers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { users ->
            setupRecyclerView(users)
        }
    }

    private fun setupRecyclerView(users: List<User>) {
        binding?.rvAllUser?.adapter = UsersAdapter(users)
    }
}