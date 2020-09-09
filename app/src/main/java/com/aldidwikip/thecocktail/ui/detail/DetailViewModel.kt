package com.aldidwikip.thecocktail.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aldidwikip.thecocktail.data.AppRepository
import com.aldidwikip.thecocktail.data.model.CocktailDetail

class DetailViewModel @ViewModelInject constructor(private val appRepository: AppRepository) : ViewModel() {
    private val _cocktailName = MutableLiveData("Loading...")
    private val _cocktailGlass = MutableLiveData("Loading...")
    private val _cocktailInstruction = MutableLiveData("Loading...")
    private val _cocktailImg = MutableLiveData<String>()

    val cocktailName: LiveData<String> get() = _cocktailName
    val cocktailGlass: LiveData<String> get() = _cocktailGlass
    val cocktailInstruction: LiveData<String> get() = _cocktailInstruction
    val cocktailImg: LiveData<String> get() = _cocktailImg

    fun cocktail(cocktailId: String) = appRepository.getCocktail(cocktailId)
            .asLiveData(viewModelScope.coroutineContext)

    fun getCocktail(data: CocktailDetail) {
        _cocktailName.value = data.cocktailName
        _cocktailGlass.value = data.cocktailGlass
        _cocktailInstruction.value = data.cocktailInstructions
        _cocktailImg.value = data.cocktailImg
    }


}