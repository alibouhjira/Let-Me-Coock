package com.example.scafoldcomplet.mealApp.data.models


import com.squareup.moshi.Json

data class Meal(
    @Json(name = "meals")
    val meals: List<MealX>
)