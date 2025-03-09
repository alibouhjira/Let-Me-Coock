package com.example.scafoldcomplet.mealApp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.scafoldcomplet.R
import com.example.scafoldcomplet.mealApp.theme.Purple40
import com.example.scafoldcomplet.mealApp.theme.ScafoldCompletTheme
import com.example.scafoldcomplet.mealApp.view.MealScreenDetail
import com.example.scafoldcomplet.mealApp.view.mealGrid.GridDisplay
import com.example.scafoldcomplet.mealApp.view.mealList.CardMealList
import com.example.scafoldcomplet.mealApp.view.mealList.ListDisplay
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)

    val vm: MealViewModel by viewModels {
        MealViewModel.MealVeiwModelFactory((application as MealApplication).repository)
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val aff by vm.display.observeAsState()


            // Screen content

            ScafoldCompletTheme(
            ) {


                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),

                    bottomBar = {

                        if (!vm.is_on_detail.value && !vm.is_on_splash.value) {

                            BottomNavigation(backgroundColor = Color.White) {

                                val navBackStackEntry by navController.currentBackStackEntryAsState()


                                BottomNavigationItem(
                                    selectedContentColor = Purple40,
                                    unselectedContentColor = Color.Black,

                                    icon = {
                                        Icon(
                                            Icons.Filled.RestaurantMenu,
                                            contentDescription = null
                                        )
                                    },
                                    selected = vm.is_on_list.value,
                                    onClick = {
                                        if (!vm.is_on_list.value) navController.navigate("list")
                                    }
                                )
                                BottomNavigationItem(
                                    selectedContentColor = Purple40,
                                    unselectedContentColor = Color.Black,
                                    icon = {
                                        Icon(
                                            Icons.Filled.Favorite,
                                            contentDescription = null
                                        )
                                    },
                                    selected = vm.is_on_favorit.value,
                                    onClick = {
                                        if (!vm.is_on_favorit.value) navController.navigate("favoris")
                                    }
                                )


                            }
                        }
                    },

                    floatingActionButton = {
                        if (vm.is_on_list.value) {
                            if (aff == true) {
                                FloatingActionButton(
                                    backgroundColor = Purple40,
                                    shape = RoundedCornerShape(10.dp),
                                    content = {
                                        Icon(
                                            Icons.Filled.GridView,
                                            contentDescription = "filter",
                                            modifier = Modifier.shadow(10.dp)
                                        )
                                    },
                                    onClick = {
                                        vm.display.postValue(false)
                                    }
                                )
                            } else {
                                FloatingActionButton(
                                    shape = RoundedCornerShape(10.dp),
                                    content = {
                                        Icon(
                                            Icons.Default.ListAlt,
                                            contentDescription = "filter",
                                            modifier = Modifier.shadow(10.dp)
                                        )
                                    },
                                    onClick = {
                                        vm.display.postValue(true)
                                    }
                                )

                            }
                        }
                    }


                ) { it ->
                    val c by vm.c.observeAsState()
                    MyAppNavHost(vm, navController = navController, it)

                }


                // A surface container using the 'background' color from the theme

            }
        }
    }
}


@Composable
fun MyAppNavHost(
    vm: MealViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues,

    ) {

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }

        composable("favoris") {
            Favoris(vm, navController, paddingValues)
        }

        composable("list") {
            ListMeal(vm, navController)
        }

        composable(
            "detailles/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            MealScreenDetail(backStackEntry.arguments?.getString("id").toString(), vm = vm)
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ListMeal(
    vm: MealViewModel,
    navController: NavHostController,

    ) {


    vm.is_on_detail.value = false
    vm.is_on_favorit.value = false
    vm.is_on_list.value = true
    vm.is_on_splash.value = false
    val aff by vm.display.observeAsState()



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


    }


    if (vm.errorMessage.isEmpty()) {


        if (aff == true) {

            ListDisplay(vm, navController)
        } else {
            GridDisplay(vm, navController)
        }


    } else {
        Text(vm.errorMessage)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favoris(
    vm: MealViewModel,
    navController: NavHostController,
    it: PaddingValues,


    ) {
    vm.is_on_detail.value = false
    vm.is_on_favorit.value = true
    vm.is_on_list.value = false

    val favmeals by remember {
        vm.favourites
    }

    if (favmeals.isEmpty()) {
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Image(
                    modifier = Modifier.padding(20.dp),
                    painter = painterResource(id = R.drawable.alien), // Remplacez "votre_image" par le nom de votre image dans les ressources
                    contentDescription = "Alien",
                )
                Text(
                    text = "You didn't liked anything ? ",
                )
                Text(
                    text = "You must be an alien or something!!!",
                )
            }

        }


    } else {


        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(paddingValues = it)
        ) {
            items(favmeals) { meal ->
                CardMealList(meal = meal, navController, vm)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }


}

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.appicon), // Remplacez avec votre propre ressource d'image
            contentDescription = null,

            )
    }

    LaunchedEffect(true, block = {
        delay(2000)
        navController.navigate("list")
    })
}

