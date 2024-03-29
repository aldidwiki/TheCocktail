package com.aldidwikip.thecocktail.data.remote

import com.aldidwikip.thecocktail.data.model.CocktailDetailResponse
import com.aldidwikip.thecocktail.data.model.CocktailResponse
import com.aldidwikip.thecocktail.data.model.IngredientResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET("filter.php")
    suspend fun getCocktails(@Query("i") ingredient: String): Response<CocktailResponse>

    @GET("lookup.php")
    suspend fun getCocktail(@Query("i") cocktailId: String): Response<CocktailDetailResponse>

    @GET("list.php?i=list")
    suspend fun getIngredientList(): Response<IngredientResponse>

    @GET("search.php")
    suspend fun getSearchResult(@Query("s") keywords: String): Response<CocktailResponse>
}