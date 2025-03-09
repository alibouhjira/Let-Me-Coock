package com.example.scafoldcomplet.mealApp.data.API

import com.example.scafoldcomplet.mealApp.data.API.Meal2
import com.example.scafoldcomplet.mealApp.data.models.MealX
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPIService {

//renvois tout les plats commancant par la lettre
    @GET("api/json/v1/1/search.php")
    suspend fun getMealsByLetter(@Query("f") letter: String): Meal2
//renvois les detailles d'un plat dont l'id est passe en parametre
    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealDetails(@Query("i") letter: String): Meal2



}