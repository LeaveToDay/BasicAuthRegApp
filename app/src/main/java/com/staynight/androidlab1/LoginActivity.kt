package com.staynight.androidlab1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.staynight.androidlab1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private val sharedPreferences by lazy {
        this.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        checkForAuth()
        setupListeners()
        Log.e(
            "TAG",
            "onCreate: ${sharedPreferences.getStringSet(Constants.EMAILS_TAG, emptySet())}",
        )
        Log.e(
            "TAG",
            "onCreate: ${sharedPreferences.getStringSet(Constants.PASSWORDS_TAG, emptySet())}",
        )
        Log.e(
            "TAG",
            "onCreate: ${sharedPreferences.getStringSet(Constants.NAMES_TAG, emptySet())}",
        )
        Log.e("TAG", "onCreate: ${sharedPreferences.getStringSet(Constants.AGES_TAG, emptySet())}")
    }

    private fun checkForAuth() {
        val currentEmail = sharedPreferences.getString(Constants.CURRENT_EMAIL, "")
        if (!currentEmail.isNullOrEmpty())
            navigateToHomeActivity(currentEmail)
    }

    private fun setupListeners() {
        binding?.apply {
            btnSignUp.setOnClickListener {
                navigateToRegistrationActivity()
            }

            btnLogin.setOnClickListener {
                if (!etEmail.text.isNullOrEmpty() && !etPassword.text.isNullOrEmpty())
                    checkUserLogin(etEmail.text.toString(), etPassword.text.toString())
                else
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.fill_fields_error),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }

    private fun checkUserLogin(email: String, password: String) {
        val userEmails = sharedPreferences.getStringSet(Constants.EMAILS_TAG, emptySet())
        val userPasswords = sharedPreferences.getStringSet(Constants.PASSWORDS_TAG, emptySet())

        val userPassword =
            String.format(getString(R.string.store_user_info_format), email, password)

        val hasUserEmail = !userEmails?.find { it == email }.isNullOrEmpty()

        if (hasUserEmail) {
            val hasUserPassword =
                !userPasswords?.find { it == userPassword }.isNullOrEmpty()

            if (hasUserPassword) {
                val userLoginIndex = userEmails?.indexOf(email)

                if (userLoginIndex != null)
                    navigateToHomeActivity(email)
            } else
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHomeActivity(userEmail: String) {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        intent.putExtra(Constants.USER_EMAIL, userEmail)
        finish()
        setCurrentUser(userEmail)

        startActivity(intent)
    }

    private fun navigateToRegistrationActivity() {
        val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun setCurrentUser(email: String) {
        sharedPreferences.edit().putString(Constants.CURRENT_EMAIL, email).apply()
    }
}