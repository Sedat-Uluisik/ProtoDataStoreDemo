package com.sedat.protodatastoredemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.protodatastoredemo.model.UserPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProtoViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    fun writeData(name: String, age: Int) = viewModelScope.launch{
        repository.writeData(name, age)
    }

    val readData = repository.readData
}