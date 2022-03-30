package com.sedat.protodatastoredemo

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.sedat.protodatastoredemo.model.UserPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor( @ApplicationContext private val context: Context) {

    private val Context.datastore: DataStore<User> by dataStore(
        fileName = "user.pb",
        serializer = UserSerializer
    )

    suspend fun writeData(name: String, age: Int) = context.datastore.updateData {
        it.toBuilder()
            .setName(name)
            .setAge(age)
            .build()
    }

    val readData: Flow<UserPrefs> = context.datastore.data
        .catch { exception->
            if(exception is IOException){
                Log.e("TAG", "Error reading sort order preferences.", exception)
                emit(User.getDefaultInstance())
            }else{
                throw exception
            }
        }.map {
            val userPrefs = UserPrefs(it.name, it.age)
            userPrefs
        }
}