package dev.ran.usermanage.data.RoomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ran.usermanage.data.Model.UserTableModel

@Database(entities = [UserTableModel::class], version = 1, exportSchema = false)
abstract class UserDb : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE : UserDb? = null

        fun getDatabase (context: Context) : UserDb{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDb::class.java,
                    "testDb"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}