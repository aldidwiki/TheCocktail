package com.aldidwikip.thecocktail.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aldidwikip.thecocktail.data.model.Cocktail
import com.aldidwikip.thecocktail.data.model.CocktailDetail
import com.aldidwikip.thecocktail.data.model.Ingredients
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalService {

    @Query("SELECT * FROM cocktail_table ORDER BY cocktailName ASC")
    fun load(): Flow<List<Cocktail>>

    @Query("SELECT * FROM cocktail_detail_table WHERE cocktailId = :id")
    fun loadDetail(id: String): Flow<List<CocktailDetail>>

    @Query("SELECT * FROM ingredient_table ORDER BY ingredient ASC")
    fun loadIngredients(): Flow<List<Ingredients>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveIngredients(ingredients: List<Ingredients>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(cocktail: List<Cocktail>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDetail(cocktailDetail: List<CocktailDetail>)

    @Query("DELETE FROM cocktail_table")
    suspend fun deleteAllCocktails()

    @Query("DELETE FROM cocktail_detail_table WHERE cocktailId = :id")
    suspend fun deleteCocktailDetail(id: String)
}