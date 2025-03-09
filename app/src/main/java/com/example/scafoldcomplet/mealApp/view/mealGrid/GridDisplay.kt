package com.example.scafoldcomplet.mealApp.view.mealGrid

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.scafoldcomplet.mealApp.MealViewModel

//affichage en forme de grilleD
@Composable
fun GridDisplay(vm: MealViewModel, navController: NavHostController) {
    val list: Int? by vm.c.observeAsState()
    LazyVerticalGrid(
        contentPadding = PaddingValues(5.dp),
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxHeight()
    ) {
        list?.let {
            items(vm._meals.value.meals) { meal ->
                CardMealGrid(meal = meal, navController, vm)
            }
        }
    }
}