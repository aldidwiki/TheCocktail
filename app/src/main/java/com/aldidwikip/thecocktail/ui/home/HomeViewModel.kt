package com.aldidwikip.thecocktail.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aldidwikip.thecocktail.data.AppRepository
import com.aldidwikip.thecocktail.data.model.Cocktail
import com.aldidwikip.thecocktail.data.model.Ingredients
import com.aldidwikip.thecocktail.util.DataState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(private val appRepository: AppRepository) : ViewModel() {
    private val _cocktails: MutableLiveData<DataState<List<Cocktail>>> = MutableLiveData()
    private val _cocktailsResult: MutableLiveData<DataState<List<Cocktail>>> = MutableLiveData()
    val cocktails: LiveData<DataState<List<Cocktail>>> = _cocktails
    val cocktailsResult: LiveData<DataState<List<Cocktail>>> = _cocktailsResult

    fun getFilteredCocktails(ingredient: String) = viewModelScope.launch {
        appRepository.getCocktails(ingredient).collect {
            _cocktails.postValue(it)
        }
    }

    fun getSearchResult(keywords: String) = viewModelScope.launch {
        appRepository.getSearchResult(keywords).collect {
            _cocktailsResult.postValue(it)
        }
    }

    val ingredientList: LiveData<List<Ingredients>> = appRepository.getIngredientList.asLiveData()
}
