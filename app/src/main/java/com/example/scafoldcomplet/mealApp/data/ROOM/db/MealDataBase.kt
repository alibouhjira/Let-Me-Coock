package com.example.scafoldcomplet.mealApp.data.ROOM.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.scafoldcomplet.mealApp.data.models.MealX
import com.example.scafoldcomplet.mealApp.data.ROOM.Utils.Companion.MEAL_DATABASE
import com.example.scafoldcomplet.mealApp.data.ROOM.dao.MealDao

@Database(
    entities = [MealX::class],
    version = 1,
    exportSchema = false
)
abstract class MealDataBase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var instance: MealDataBase? = null

        fun getDatabase(context: Context): MealDataBase {

            // uniquement pour les tests, on vide la BDD à chaque fois (run from scratch), donc pas besoin
            // de gérer le changement de numéro de version
            // Ne pas oublier de retirer cette ligne à la fin !!!!

            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MealDataBase::class.java,
                    MEAL_DATABASE
                )
                    // Cette ligne provoque la suppression et la recréation de la base de données en cas de migration
                    // .fallbackToDestructiveMigration()
                    // Cette ligne provoque (en théorie) le préremplissage de la BDD, mais KO pour le moment :-(
                    // .addCallback(BookDatabaseCallback())
                    .build()
            }
        }
    }
}

