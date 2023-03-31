package com.hearhere.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//TODO 추후 Hilt로 Context 주입
abstract class BaseViewModel : ViewModel() {

    val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
}