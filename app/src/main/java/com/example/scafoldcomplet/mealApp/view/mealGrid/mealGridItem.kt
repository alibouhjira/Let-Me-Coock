package com.example.scafoldcomplet.mealApp.view.mealGrid

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.scafoldcomplet.mealApp.MealViewModel
import com.example.scafoldcomplet.mealApp.data.models.MealX
import com.example.scafoldcomplet.mealApp.view.openYouTubeVideo

//reunis tout les attribut ingrediants du model mealX en une seule list pour simplifier l'utilisation
fun combineIngredients(meal: MealX): MutableList<String> {
    val ingredients = mutableListOf<String>()

    for (i in 1..20) {
        val fieldName = "strIngredient$i"
        val field = try {
            meal.javaClass.getDeclaredField(fieldName).apply {
                isAccessible = true
            }
        } catch (e: NoSuchFieldException) {
            null
        }


        if (field != null) {
            val fieldValue = field.get(meal)
            if (fieldValue != null && fieldValue is String && fieldValue.isNotBlank()) {
                ingredients.add(fieldValue)
            }
        }
    }
    return ingredients;
}

//reunis tout les attribut mesures du model mealX en une seule list pour simplifier l'utilisation
fun combineMeasure(meal: MealX): MutableList<String> {
    val mesures = mutableListOf<String>()

    for (i in 1..20) {

        val fieldName2 = "strMeasure$i"
        val field2 = try {
            meal.javaClass.getDeclaredField(fieldName2).apply {
                isAccessible = true
            }
        } catch (e: NoSuchFieldException) {
            null
        }

        if (field2 != null) {
            val fieldValue2 = field2.get(meal)
            if (fieldValue2 != null && fieldValue2 is String && fieldValue2.isNotBlank()) {
                mesures.add(fieldValue2)
            }
        }
    }
    return mesures;
}


//Composable qui affiche les attribut d'un plats dans la grille
@SuppressLint("SuspiciousIndentation", "UnrememberedMutableState")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardMealGrid(
    meal: MealX,
    navController: NavHostController,
    vm: MealViewModel,
) {
    val ingredients = combineIngredients(meal);

    val counter by vm.c.observeAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp)
            .clickable(onClick = {
                vm.loaded.value = false;navController.navigate("detailles/${meal.idMeal}")
            })
            .background(color = Color.White, shape = RectangleShape)
            .border(0.5.dp, Color.Gray, shape = RectangleShape),


        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Box {
                GlideImage(
                    model = meal.strMealThumb,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)

                )
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(20.dp),
                    onClick = {
                        vm.mealtofav.value = meal
                        if (meal.favoris == false) {
                            vm.addMeal()
                        } else {
                            vm.deleteMeal()
                        }
                        vm.incrementCounter()
                    },

                    ) {
                    Icon(
                        imageVector = if (meal.favoris == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = meal.strMeal,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(5.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )





            Box(
                Modifier
                    .background(Color.Red, shape = RoundedCornerShape(5.dp))
                    .size(50.dp, 25.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { openYouTubeVideo(context, meal.strYoutube, meal.strMeal) },

                    ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }


        }
    }
}



