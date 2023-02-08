package com.staynight.androidlab1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.staynight.androidlab1.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private var binding: ActivityHomeBinding? = null

    private var email: String? = null

    private val sharedPreferences by lazy {
        this.getSharedPreferences(Constants.SHARED_PREF_TAG, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        initUserInfo()
        setupListeners()
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

    private fun initUserInfo() {
        val userEmail = intent.getStringExtra(Constants.USER_EMAIL)
        email = userEmail

        if (!userEmail.isNullOrEmpty()) {
            val prefixUserEmail = String.format(getString(R.string.delete_email_suffix), userEmail)

            val userName =
                sharedPreferences.getStringSet(Constants.NAMES_TAG, emptySet())?.toList()

            val userAge =
                sharedPreferences.getStringSet(Constants.AGES_TAG, emptySet())?.toList()

            userName?.forEach {
                if (it.contains(prefixUserEmail)) {
                    binding?.tvName?.text = it.replace(prefixUserEmail, "")
                    return@forEach
                }
            }

            userAge?.forEach {
                if (it.contains(prefixUserEmail)) {
                    binding?.tvAge?.text = it.replace(prefixUserEmail, "")
                    return@forEach
                }
            }

            binding?.tvEmail?.text = userEmail
        }
    }

    private fun logout() {
        sharedPreferences.edit().putString(Constants.CURRENT_EMAIL, "").apply()

        navigateToLogin()
    }

    private fun deleteUser() {
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

        val prefixUserEmail = String.format(getString(R.string.delete_email_suffix), email)

        userEmails.remove(email)
        userPasswords.removeIf { it.contains(prefixUserEmail) }
        userNames.removeIf { it.contains(prefixUserEmail) }
        userAges.removeIf { it.contains(prefixUserEmail) }

        sharedPreferences.edit().putStringSet(Constants.EMAILS_TAG, userEmails).apply()
        sharedPreferences.edit().putStringSet(Constants.PASSWORDS_TAG, userPasswords).apply()
        sharedPreferences.edit().putStringSet(Constants.NAMES_TAG, userNames).apply()
        sharedPreferences.edit().putStringSet(Constants.AGES_TAG, userAges).apply()

        logout()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}