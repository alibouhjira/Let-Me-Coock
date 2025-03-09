package com.example.scafoldcomplet.mealApp

import android.app.Application
import com.example.scafoldcomplet.mealApp.data.ROOM.dao.MealDao
import com.example.scafoldcomplet.mealApp.data.ROOM.db.MealDataBase
import com.example.scafoldcomplet.mealApp.data.Repository

class MealApplication : Application() {


    val database: MealDataBase by lazy { MealDataBase.getDatabase(this) }
    val mealDao: MealDao by lazy { database.mealDao() }
    val repository: Repository by lazy { Repository(mealDao) }
}
