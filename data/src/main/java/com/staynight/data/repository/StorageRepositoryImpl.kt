package com.staynight.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.staynight.domain.model.User
import com.staynight.domain.repository.StorageRepository
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : StorageRepository {

    init {
        Log.e("TAG", "${getUserEmails()}")
        Log.e("TAG", "${getUserPasswords()}")
        Log.e("TAG", "${getUserNames()}")
        Log.e("TAG", "${getUserAges()}")
    }

    companion object {
        const val EMAILS_TAG = "LoginsTag"
        const val PASSWORDS_TAG = "PasswordsTag"
        const val NAMES_TAG = "NamesTag"
        const val AGES_TAG = "AgesTag"

        const val CURRENT_EMAIL = "CurrentEmail"
    }

    override fun getUserEmails(): Set<String>? =
        sharedPreferences.getStringSet(EMAILS_TAG, emptySet())


    override fun getUserPasswords(): Set<String>? =
        sharedPreferences.getStringSet(PASSWORDS_TAG, emptySet())

    override fun getUserNames(): Set<String>? =
        sharedPreferences.getStringSet(NAMES_TAG, emptySet())

    override fun getUserAges(): Set<String>? =
        sharedPreferences.getStringSet(AGES_TAG, emptySet())

    override fun setCurrentUser(email: String) {
        sharedPreferences.edit().putString(CURRENT_EMAIL, email).apply()
    }

    override fun getCurrentUser(): String? =
        sharedPreferences.getString(CURRENT_EMAIL, "")

    override fun getUserInfo(): User {
        val userEmail = getCurrentUser()
        val prefixUserEmail = String.format("%s_", userEmail)

        val userNames =
            sharedPreferences.getStringSet(NAMES_TAG, emptySet())?.toList()

        val userAges =
            sharedPreferences.getStringSet(AGES_TAG, emptySet())?.toList()

        return User(
            userEmail ?: "",
            userAges?.find { it.contains(prefixUserEmail) }?.replace(prefixUserEmail, "") ?: "",
            userNames?.find { it.contains(prefixUserEmail) }?.replace(prefixUserEmail, "") ?: ""
        )
    }

    override fun deleteUser() {
        val email = getCurrentUser()

        val userEmails =
            sharedPreferences.getStringSet(EMAILS_TAG, emptySet())?.toMutableSet()
                ?: mutableSetOf()
        val userPasswords =
            sharedPreferences.getStringSet(PASSWORDS_TAG, emptySet())?.toMutableSet()
                ?: mutableSetOf()
        val userNames =
            sharedPreferences.getStringSet(NAMES_TAG, emptySet())?.toMutableSet()
                ?: mutableSetOf()
        val userAges =
            sharedPreferences.getStringSet(AGES_TAG, emptySet())?.toMutableSet()
                ?: mutableSetOf()

        val prefixUserEmail = String.format("%s_", email)

        userEmails.remove(email)
        userPasswords.removeIf { it.contains(prefixUserEmail) }
        userNames.removeIf { it.contains(prefixUserEmail) }
        userAges.removeIf { it.contains(prefixUserEmail) }

        sharedPreferences.edit().putStringSet(EMAILS_TAG, userEmails).apply()
        sharedPreferences.edit().putStringSet(PASSWORDS_TAG, userPasswords).apply()
        sharedPreferences.edit().putStringSet(NAMES_TAG, userNames).apply()
        sharedPreferences.edit().putStringSet(AGES_TAG, userAges).apply()
    }

    override fun logout() {
        sharedPreferences.edit().putString(CURRENT_EMAIL, "").apply()
    }

    override fun registerUser(user: User, password: String) {
        val userEmails = getUserEmails()?.toMutableSet()
        val userPasswords = getUserPasswords()?.toMutableSet()
        val userNames = getUserNames()?.toMutableSet()
        val userAges = getUserAges()?.toMutableSet()

        userEmails?.add(user.email)

        userPasswords?.add(
            String.format(
                "%s_%s",
                user.email,
                password
            )
        )
        userNames?.add(
            String.format(
                "%s_%s",
                user.email,
                user.name
            )
        )
        userAges?.add(
            String.format(
                "%s_%s",
                user.email,
                user.age
            )
        )

        sharedPreferences.edit().putStringSet(EMAILS_TAG, userEmails).apply()
        sharedPreferences.edit().putStringSet(PASSWORDS_TAG, userPasswords).apply()
        sharedPreferences.edit().putStringSet(NAMES_TAG, userNames).apply()
        sharedPreferences.edit().putStringSet(AGES_TAG, userAges).apply()
    }

    override fun getAllUsers(): List<User> {
        val result = mutableListOf<User>()

        val userEmails = getUserEmails()?.toMutableSet()
        val userNames = getUserNames()?.toMutableSet()
        val userAges = getUserAges()?.toMutableSet()

        userEmails?.forEach {
            val prefixUserEmail = String.format("%s_", it)
            result.add(
                User(
                    it,
                    userAges?.find { ages -> ages.contains(prefixUserEmail) }
                        ?.replace(prefixUserEmail, "") ?: "",
                    userNames?.find { names -> names.contains(prefixUserEmail) }
                        ?.replace(prefixUserEmail, "") ?: "",
                )
            )
        }

        return result
    }
}