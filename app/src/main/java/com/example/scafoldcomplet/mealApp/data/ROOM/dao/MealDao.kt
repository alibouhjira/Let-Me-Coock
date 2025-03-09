package com.example.scafoldcomplet.mealApp.data.ROOM.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.scafoldcomplet.mealApp.data.models.MealX
import com.example.scafoldcomplet.mealApp.data.ROOM.Utils.Companion.MEAL_TABLE

@Dao
interface MealDao {

// renvois tout les plats favoris
    @Query("SELECT * FROM $MEAL_TABLE ")
    suspend fun getMeals(): List<MealX>
//ajoute un plat en favoris
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMeal(meal: MealX): Long
//supprime un plat des favoris
    @Delete
    suspend fun deletMeal(meal: MealX)
}