package com.example.scafoldcomplet.mealApp.data

import android.util.Log
import com.example.scafoldcomplet.mealApp.data.API.APIService
import com.example.scafoldcomplet.mealApp.data.API.Meal2
import com.example.scafoldcomplet.mealApp.data.ROOM.dao.MealDao
import com.example.scafoldcomplet.mealApp.data.models.Meal
import com.example.scafoldcomplet.mealApp.data.models.MealX

typealias Meals = List<MealX>

class Repository(private val mealDao: MealDao) {

//reunis tout les plat en faisant des appels sur les lettres car l'api ne les donne pas tous directement
    suspend fun getAllMeals(): Meal {
        val allMeals = mutableListOf<MealX>()

        // Itérer sur toutes les lettres de l'alphabet
        for (letter in 'a'..'z') {
            try {
                // Appeler l'API pour la lettre actuelle
                val mealForLetter = APIService.mealAPIService.getMealsByLetter(letter.toString())

                // Ajouter les repas à la liste globale
                allMeals.addAll(mealForLetter.meals)
            } catch (e: Exception) {
                // Gérer les erreurs d'appel API pour la lettre actuelle
                e.printStackTrace()
            }
        }
        allMeals.shuffle()
        return Meal(meals = allMeals)
    }

    // api renvois les detaille sur un plat
    suspend fun getMealDetail(id: String): Meal2 {

        return APIService.mealAPIService.getMealDetails(id)
    }
//list des favoris dans la db
    suspend fun getAllFavouritMeals(): List<MealX> {
        return mealDao.getMeals()
    }
// ajoute un favoris dans la db
    suspend fun addMeal(meal: MealX): Long {
        return mealDao.addMeal(meal)
    }
//supprime un favoris dans la db
    suspend fun deletMealFromRoom(meal: MealX) {
        return mealDao.deletMeal(meal)
    }




}