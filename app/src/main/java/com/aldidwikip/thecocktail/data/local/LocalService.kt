package com.aldidwikip.thecocktail.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aldidwikip.thecocktail.data.model.Cocktail
import com.aldidwikip.thecocktail.data.model.CocktailDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalService {

    @Query("SELECT * FROM cocktail_table")
    fun load(): List<Cocktail>

    @Query("SELECT * FROM cocktail_detail_table WHERE cocktailId = :id")
    fun loadDetail(id: String): List<CocktailDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(cocktail: List<Cocktail>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDetail(cocktailDetail: List<CocktailDetail>)

    @Query("DELETE FROM cocktail_table")
    suspend fun deleteAllCocktails()

}