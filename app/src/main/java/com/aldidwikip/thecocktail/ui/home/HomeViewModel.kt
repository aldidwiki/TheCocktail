package com.aldidwikip.thecocktail.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldidwikip.thecocktail.data.AppRepository
import com.aldidwikip.thecocktail.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    private val ingredientQuery = MutableStateFlow("Rum")
    fun setIngredientQuery(ingredient: String) {
        ingredientQuery.value = ingredient
    }

    private val searchQuery = MutableStateFlow("")
    fun setSearchQuery(searchQuery: String) {
        this.searchQuery.value = searchQuery
    }

    fun getFilteredCocktails() = ingredientQuery.flatMapLatest { appRepository.getCocktails(it) }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DataState.Loading
    )

    fun getSearchCocktailResult() = searchQuery.filter { it.isNotEmpty() }.flatMapLatest { appRepository.getSearchResult(it) }
            .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    DataState.Loading
            )

    fun getIngredientList() = appRepository.getIngredientList.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
    )
}
