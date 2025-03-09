package com.example.scafoldcomplet.mealApp.data.API

import com.example.scafoldcomplet.mealApp.data.models.MealX
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


data class Meal2(
    @Json(name = "meals")
    var meals: List<MealX>
)

object APIService {
    private const val BASE_URL = "https://www.themealdb.com"
    private val moshiBuilder = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshiBuilder))
        .build()

    val mealAPIService: MealAPIService by lazy {
        retrofit.create(MealAPIService::class.java)
    }
}

