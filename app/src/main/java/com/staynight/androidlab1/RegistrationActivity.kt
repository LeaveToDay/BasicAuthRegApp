package com.staynight.androidlab1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.staynight.androidlab1.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private var binding: ActivityRegistrationBinding? = null

    private val sharedPreferences by lazy {
        this.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setupListeners()
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
                    checkUserLogin(
                        etEmail.text.toString(),
                        etPassword.text.toString(),
                        etName.text.toString(),
                        etAge.text.toString()
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

    private fun checkUserLogin(email: String, password: String, name: String, age: String) {
        val userEmails =
            sharedPreferences.getStringSet(Constants.EMAILS_TAG, emptySet())?.toMutableSet()
                ?: mutableSetOf()
        val userPasswords =
            sharedPreferences.getStringSet(Constants.PASSWORDS_TAG, emptySet())?.toMutableSet()
                ?: mutableSetOf()
        val userNames =
            sharedPreferences.getStringSet(Constants.NAMES_TAG, emptySet())?.toMutableSet()
                ?: mutableSetOf()
        val userAges =
            sharedPreferences.getStringSet(Constants.AGES_TAG, emptySet())?.toMutableSet()
                ?: mutableSetOf()

        val hasUserEmail = !userEmails.find { it == email }.isNullOrEmpty()

        if (!hasUserEmail) {
            userEmails.add(email)
            userPasswords.add(
                String.format(
                    getString(R.string.store_user_info_format),
                    email,
                    password
                )
            )
            userNames.add(
                String.format(
                    getString(R.string.store_user_info_format),
                    email,
                    name
                )
            )
            userAges.add(
                String.format(
                    getString(R.string.store_user_info_format),
                    email,
                    age
                )
            )

            sharedPreferences.edit().putStringSet(Constants.EMAILS_TAG, userEmails).apply()
            sharedPreferences.edit().putStringSet(Constants.PASSWORDS_TAG, userPasswords).apply()
            sharedPreferences.edit().putStringSet(Constants.NAMES_TAG, userNames).apply()
            sharedPreferences.edit().putStringSet(Constants.AGES_TAG, userAges).apply()

            navigateToHomeActivity(email)
        } else
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHomeActivity(userEmail: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(Constants.USER_EMAIL, userEmail)
        finishAffinity()
        setCurrentUser(userEmail)

        startActivity(intent)
    }

    private fun setCurrentUser(email: String) {
        sharedPreferences.edit().putString(Constants.CURRENT_EMAIL, email).apply()
    }
}