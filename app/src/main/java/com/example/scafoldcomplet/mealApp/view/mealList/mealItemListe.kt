package com.example.scafoldcomplet.mealApp.view.mealList

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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


//Composable qui affiche les attribut d'un plats dans la liste
@SuppressLint("SuspiciousIndentation", "UnrememberedMutableState")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CardMealList(meal: MealX, navController: NavHostController, vm: MealViewModel) {
    val ingredients = combineIngredients(meal);

    val counter by vm.c.observeAsState()
    val context = LocalContext.current
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                vm.loaded.value = false;
                navController.navigate("detailles/${meal.idMeal}")
            }),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),

        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            GlideImage(
                model = meal.strMealThumb,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)


            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = meal.strMeal,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = meal.strCategory,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(6.dp),
                    color = Color.Blue
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        Modifier
                            .background(Color.Red, shape = RoundedCornerShape(10.dp))
                            .width(80.dp),
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
                    IconButton(
                        onClick = {
                            vm.mealtofav.value = meal
                            if (meal.favoris == false) {
                                vm.addMeal()
                            } else {
                                vm.deleteMeal()
                            }
                            vm.incrementCounter()
                        },
                        modifier = Modifier
                            .size(60.dp)
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
            }

            LazyRow(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                items(ingredients.orEmpty()) { ingredient ->
                    ElevatedCard(
                        modifier = Modifier
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),

                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = ingredient,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}



