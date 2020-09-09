package com.aldidwikip.thecocktail.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aldidwikip.thecocktail.data.AppRepository

class HomeViewModel @ViewModelInject constructor(appRepository: AppRepository) : ViewModel() {

    val cocktails = appRepository.getCocktails()
            .asLiveData(viewModelScope.coroutineContext)
}
