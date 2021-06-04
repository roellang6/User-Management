package dev.ran.usermanage.data.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTableModel")
data class UserTableModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val fullname: String,
    val username: String,
    val password: String,
    val country: String,
    val secretquestion: String,
    val secretanswer: String
)
