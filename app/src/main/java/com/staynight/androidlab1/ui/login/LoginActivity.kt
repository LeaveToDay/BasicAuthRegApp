package com.staynight.androidlab1.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.staynight.androidlab1.R
import com.staynight.androidlab1.ui.registration.RegistrationActivity
import com.staynight.androidlab1.databinding.ActivityLoginBinding
import com.staynight.androidlab1.ui.allusers.AllUsersActivity
import com.staynight.androidlab1.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        checkForAuth()
        setupListeners()
        setupObservers()
    }

    private fun checkForAuth() {
        if (viewModel.isAuth())
            navigateToHomeActivity()
    }

    private fun setupListeners() {
        binding?.apply {
            btnSignUp.setOnClickListener {
                navigateToRegistrationActivity()
            }

            btnLogin.setOnClickListener {
                if (!etEmail.text.isNullOrEmpty() && !etPassword.text.isNullOrEmpty())
                    viewModel.login(etEmail.text.toString(), etPassword.text.toString())
                else
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.fill_fields_error),
                        Toast.LENGTH_SHORT
                    ).show()
            }

            btnAllUsers.setOnClickListener {
                navigateToAllUsersActivity()
            }
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { hasUser ->
            if (hasUser)
                navigateToHomeActivity()
            else
                Toast.makeText(this, "Error Invalid Password or user", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        finish()
        startActivity(intent)
    }

    private fun navigateToRegistrationActivity() {
        val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAllUsersActivity() {
        val intent = Intent(this@LoginActivity, AllUsersActivity::class.java)
        startActivity(intent)
    }
}