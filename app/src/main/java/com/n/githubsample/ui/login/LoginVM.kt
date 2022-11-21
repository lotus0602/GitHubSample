package com.n.githubsample.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n.githubsample.core.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val dataStore: DataStoreManager
) : ViewModel() {

    fun saveAccessToken(token: String) {
        viewModelScope.launch {
            dataStore.setAccessToken(token)
        }
    }
}