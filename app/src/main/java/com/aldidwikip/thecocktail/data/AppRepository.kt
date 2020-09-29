package com.aldidwikip.thecocktail.data

import android.util.Log
import com.aldidwikip.thecocktail.data.local.LocalService
import com.aldidwikip.thecocktail.data.model.Ingredients
import com.aldidwikip.thecocktail.data.remote.RemoteService
import com.aldidwikip.thecocktail.util.DataState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val TAG = "AppRepository"

class AppRepository @Inject constructor(private val remoteService: RemoteService,
                                        private val localService: LocalService) {

    fun getCocktails(ingredient: String) = flow {
        emit(DataState.Loading)
        try {
            val response = remoteService.getCocktails(ingredient)
            if (response.isSuccessful) {
                localService.deleteAllCocktails()
                localService.save(response.body()!!.drinks)
            } else {
                Log.e(TAG, "getCocktails: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        } finally {
            emit(DataState.Success(localService.load()))
        }
    }.flowOn(IO)

    fun getCocktail(cocktailId: String) = flow {
        emit(DataState.Loading)
        try {
            val response = remoteService.getCocktail(cocktailId)
            if (response.isSuccessful) {
                localService.deleteCocktailDetail(cocktailId)
                localService.saveDetail(response.body()!!.detailDrinks)
            } else {
                Log.e(TAG, "getCocktail: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        } finally {
            emit(DataState.Success(localService.loadDetail(cocktailId)))
        }
    }.flowOn(IO)

    suspend fun getIngredientList(): Flow<List<Ingredients>> {
        try {
            val response = remoteService.getIngredientList()
            if (response.isSuccessful) {
                localService.saveIngredients(response.body()!!.ingredientList)
            } else {
                Log.e(TAG, "getIngredientList: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "getIngredientList: $e")
        } finally {
            return localService.loadIngredients().flowOn(IO)
        }
    }

    fun getSearchResult(keywords: String) = flow {
        emit(DataState.Loading)
        try {
            val response = remoteService.getSearchResult(keywords)
            if (response.isSuccessful && response.body()!!.drinks.isNotEmpty()) {
                emit(DataState.Success(response.body()!!.drinks))
            } else {
                Log.e(TAG, "getSearchResult: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.flowOn(IO)
}