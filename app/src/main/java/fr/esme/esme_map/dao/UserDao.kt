package fr.esme.esme_map.dao

import androidx.room.*
import fr.esme.esme_map.model.User

@Dao
interface UserDao {

    @Insert
    fun createUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)

}