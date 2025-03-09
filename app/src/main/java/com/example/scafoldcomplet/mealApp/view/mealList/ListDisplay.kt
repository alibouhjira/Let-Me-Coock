package com.example.scafoldcomplet.mealApp.view.mealList

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.scafoldcomplet.mealApp.MealViewModel

//affichage en forme liste
@Composable
fun ListDisplay(vm: MealViewModel, navController: NavHostController) {
    val list: Int? by vm.c.observeAsState()
    LazyColumn(modifier = Modifier.fillMaxHeight()) {

        list?.let {
            items(vm._meals.value.meals) { meal ->
                CardMealList(meal = meal, navController, vm)
            }
        }
    }
}