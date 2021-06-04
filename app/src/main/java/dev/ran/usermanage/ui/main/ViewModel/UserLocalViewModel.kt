package dev.ran.usermanage.ui.main.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.ran.usermanage.data.Repository.UserRepository
import dev.ran.usermanage.data.RoomDb.UserDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import dev.ran.usermanage.data.Model.*

class UserLocalViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<UserTableModel>>
    private val repository: UserRepository

    init {
        val userDao = UserDb.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.getAlldata

    }

    fun validateUser(user: String, pass: String): LiveData<UserTableModel>? {
        var userdata : LiveData<UserTableModel>? = null
        viewModelScope.launch {Dispatchers.IO
            userdata  = repository.checkUser(user, pass)
        }
        return userdata
    }

    fun addUser(user: UserTableModel) {
        viewModelScope.launch {
            Dispatchers.IO
            repository.insertUser(user)
        }
    }
}