package dev.ran.usermanage.data.RoomDb

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.ran.usermanage.data.Model.UserTableModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveUser(users : UserTableModel)

    @Query("SELECT * FROM UserTableModel")
    fun getAllData(): LiveData<List<UserTableModel>>

    @Query("SELECT * FROM UserTableModel WHERE username = :user AND password = :pass" )
    fun getUser(user: String, pass: String): LiveData<UserTableModel>
}