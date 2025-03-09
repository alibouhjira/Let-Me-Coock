package com.example.scafoldcomplet.mealApp.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.scafoldcomplet.mealApp.MealViewModel
import com.example.scafoldcomplet.mealApp.view.mealList.combineIngredients
import com.example.scafoldcomplet.mealApp.view.mealList.combineMeasure

//ecran detailles d'un plat
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MealScreenDetail(
    id: String,
    vm: MealViewModel
) {
    vm.getMealDetails(id)

    val meal by vm.mealDetail
    val k = vm._meals.value.meals.find { m -> m.idMeal == meal.idMeal }
    if (k != null) {
        vm.mealDetail.value.favoris = k.favoris
        Log.d("ali", k.favoris.toString())

    }


    vm.is_on_detail.value = true
    vm.is_on_favorit.value = false
    vm.is_on_list.value = false
    val context = LocalContext.current
    val counter by vm.c.observeAsState()
    LaunchedEffect(counter) {
    }
    if (!vm.loaded.value) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = Color.Blue,
                strokeWidth = 5.dp
            )
        }


    } else {
        val ingredients = combineIngredients(meal);
        val mesures = combineMeasure(meal);

        Scaffold(
            modifier = Modifier.statusBarsPadding(),
            bottomBar = {

                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Bouton Favoris
                    IconButton(
                        onClick = {
                            if (k != null) {
                                vm.mealtofav.value = k
                            }
                            if (meal.favoris == false) {
                                vm.addMeal()
                            } else {
                                vm.deleteMeal()
                            }
                            vm.incrementCounter()
                        },
                        Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = if (meal.favoris == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                        )
                    }

                    // Bouton Style YouTube (remplacé par n'importe quel icône de votre choix)
                    Box(
                        Modifier
                            .weight(1f)
                            .background(Color.Red, shape = MaterialTheme.shapes.medium)
                    ) {
                        IconButton(
                            onClick = { openYouTubeVideo(context, meal.strYoutube, meal.strMeal) },
                            Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }


                }
            },
        ) { it ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Affichage de l'image du meal sur la moitié supérieure de l'écran
                GlideImage(
                    // pour les miniatures
                    // model = photo.thumbnailUrl,
                    // pour les images réelles
                    model = meal.strMealThumb,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp) // Ajustez la hauteur selon vos besoins
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp
                            )
                        ), // Coins arrondis uniquement en haut

                )

                // Titre du meal
                Text(
                    text = meal.strMeal,
                    style = androidx.compose.material.MaterialTheme.typography.h1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )

                // Catégorie du meal
                Text(
                    text = "Category: ${meal.strCategory}",
                    style = androidx.compose.material.MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Blue

                )
                Text(
                    text = "Description:",

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black

                )
                Text(
                    text = meal.strInstructions, Modifier
                        .fillMaxWidth()
                        .padding(6.dp), fontStyle = FontStyle.Italic
                )

                Text(
                    text = "Ingredients:",

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black

                )

                // Grille de 2 colonnes pour les ingrédients et mesures
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .padding(bottom = 50.dp)

                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 128.dp),

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 15.dp)
                    ) {
                        mesures?.let {
                            items(it.size - 1) { index ->

                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = ingredients!![index],
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = mesures!![index],
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }

                }


            }

        }
    }
}


fun openYouTubeVideo(context: Context, youTubeLink: String, nom: String) {
    if (!youTubeLink.isNullOrBlank()) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
        context.startActivity(intent)
    } else {
        // Si le lien YouTube est vide, effectuer une recherche sur YouTube avec le nom du plat
        val searchUrl = "https://www.youtube.com/results?search_query=${nom.replace(" ", "+")}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl))
        context.startActivity(intent)
    }
}
