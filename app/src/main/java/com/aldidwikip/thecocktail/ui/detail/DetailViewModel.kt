package com.aldidwikip.thecocktail.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldidwikip.thecocktail.data.AppRepository
import com.aldidwikip.thecocktail.data.model.CocktailDetail
import com.aldidwikip.thecocktail.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DetailViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    private val _cocktailName = MutableStateFlow("Loading...")
    private val _cocktailGlass = MutableStateFlow("Loading...")
    private val _cocktailInstruction = MutableStateFlow("Loading...")
    private val _cocktailImg = MutableStateFlow("")

    val cocktailName: StateFlow<String> get() = _cocktailName
    val cocktailGlass: StateFlow<String> get() = _cocktailGlass
    val cocktailInstruction: StateFlow<String> get() = _cocktailInstruction
    val cocktailImg: StateFlow<String> get() = _cocktailImg

    private val cocktailId = MutableStateFlow("")
    fun setCocktailId(cocktailId: String) {
        this.cocktailId.value = cocktailId
    }

    fun cocktail() = cocktailId.filter { it.isNotEmpty() }.flatMapLatest { appRepository.getCocktail(it) }
            .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    DataState.Loading
            )

    fun setCocktail(data: CocktailDetail) {
        _cocktailName.value = data.cocktailName
        _cocktailGlass.value = data.cocktailGlass
        _cocktailInstruction.value = data.cocktailInstructions
        _cocktailImg.value = data.cocktailImg
    }
}