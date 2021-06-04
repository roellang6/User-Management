package dev.ran.usermanage.data.Repository

import androidx.lifecycle.LiveData
import dev.ran.usermanage.data.Model.UserTableModel
import dev.ran.usermanage.data.RoomDb.UserDao

class UserRepository(private val userDao: UserDao) {

    val getAlldata : LiveData<List<UserTableModel>> = userDao.getAllData()

    suspend fun insertUser(userdata : UserTableModel){
        userDao.saveUser(userdata)
    }

    fun checkUser(user : String, pass: String) : LiveData<UserTableModel> {
        return userDao.getUser(user, pass)
    }
}