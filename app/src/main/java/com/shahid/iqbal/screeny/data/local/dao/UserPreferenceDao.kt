package com.shahid.iqbal.screeny.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shahid.iqbal.screeny.models.UserPreference
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferenceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserPreference(userPreference: UserPreference)

    @Query("Update user_preference set languageCode =:code")
    suspend fun updateLanguage(code: String)


    @Query("SELECT * FROM user_preference limit 1")
    fun getUserPreference(): Flow<UserPreference>
}