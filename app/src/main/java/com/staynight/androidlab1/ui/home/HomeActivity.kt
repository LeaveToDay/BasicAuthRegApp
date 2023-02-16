package com.staynight.androidlab1.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.staynight.androidlab1.databinding.ActivityHomeBinding
import com.staynight.androidlab1.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private var binding: ActivityHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupObservers()
        setupListeners()
        viewModel.getUserInfo()
    }

    private fun setupListeners() {
        binding?.apply {
            btnLogOut.setOnClickListener {
                logout()
            }

            btnDeleteAccount.setOnClickListener {
                deleteUser()
            }
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { userInfo ->
            binding?.apply {
                tvEmail.text = userInfo.email
                tvAge.text = userInfo.age
                tvName.text = userInfo.name
            }
        }
    }

    private fun logout() {
        viewModel.logout()
        navigateToLogin()
    }

    private fun deleteUser() {
        viewModel.deleteUser()
        logout()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        finish()
        startActivity(intent)
    }
}