package com.aldidwikip.thecocktail.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aldidwikip.thecocktail.data.model.Cocktail
import com.aldidwikip.thecocktail.data.model.CocktailDetail
import com.aldidwikip.thecocktail.data.model.Ingredients

@Database(entities = [Cocktail::class, CocktailDetail::class, Ingredients::class],
        version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun localService(): LocalService

    companion object {
        const val DB_NAME = "cocktail_db"
    }
}