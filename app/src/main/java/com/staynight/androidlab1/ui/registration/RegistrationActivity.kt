package com.staynight.androidlab1.ui.registration

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.staynight.androidlab1.R
import com.staynight.androidlab1.databinding.ActivityRegistrationBinding
import com.staynight.androidlab1.ui.home.HomeActivity
import com.staynight.domain.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {
    private var binding: ActivityRegistrationBinding? = null
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding?.apply {
            btnBack.setOnClickListener {
                finish()
            }

            btnSignUp.setOnClickListener {
                if (!etEmail.text.isNullOrEmpty() &&
                    !etName.text.isNullOrEmpty() &&
                    !etAge.text.isNullOrEmpty() &&
                    !etPassword.text.isNullOrEmpty()
                )
                    viewModel.register(
                        User(
                            etEmail.text.toString(),
                            etAge.text.toString(),
                            etName.text.toString()
                        ),
                        etPassword.text.toString()
                    )
                else
                    Toast.makeText(
                        this@RegistrationActivity,
                        getString(R.string.fill_fields_error),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { isRegistrationSuccess ->
            if (isRegistrationSuccess)
                navigateToHomeActivity()
            else
                Toast.makeText(this, "Error Invalid data", Toast.LENGTH_SHORT).show()
        }
    }
}