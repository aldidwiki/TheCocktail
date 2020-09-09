package com.aldidwikip.thecocktail.data.remote

import com.aldidwikip.thecocktail.data.model.CocktailDetailResponse
import com.aldidwikip.thecocktail.data.model.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET("filter.php")
    suspend fun getCocktails(@Query("i") ingredient: String): Response<CocktailResponse>

    @GET("lookup.php")
    suspend fun getCocktail(@Query("i") cocktailId: String): Response<CocktailDetailResponse>
}