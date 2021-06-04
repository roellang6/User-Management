package dev.ran.usermanage.data.Repository

import android.content.Context
import android.content.SharedPreferences

class Session(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("saveData", 0)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    init {
        editor.apply()
    }

    fun setUser(user: String) {
        editor.putString("KEY_USER", user)
        editor.commit()
    }

    fun getUser(): String {
        return sharedPreferences.getString("KEY_USER", "").toString()
    }
}