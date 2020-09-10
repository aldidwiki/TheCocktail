package com.aldidwikip.thecocktail.data

import android.util.Log
import com.aldidwikip.thecocktail.data.local.LocalService
import com.aldidwikip.thecocktail.data.remote.RemoteService
import com.aldidwikip.thecocktail.util.DataState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepository @Inject constructor(private val remoteService: RemoteService,
                                        private val localService: LocalService) {

    fun getCocktails() = flow {
        emit(DataState.Loading)
        try {
            val response = remoteService.getCocktails("Rum")
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

    companion object {
        private const val TAG = "AppRepository"
    }
}