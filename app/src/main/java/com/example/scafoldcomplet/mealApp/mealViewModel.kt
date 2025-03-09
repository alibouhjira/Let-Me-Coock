package com.example.scafoldcomplet.mealApp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.scafoldcomplet.mealApp.data.Repository
import com.example.scafoldcomplet.mealApp.data.models.Meal
import com.example.scafoldcomplet.mealApp.data.models.MealX
import kotlinx.coroutines.launch


class MealViewModel(private val repository: Repository) : ViewModel() {


    // les données reçues de l'API

    val mealtofav = mutableStateOf<MealX>(MealX())
    val mealDetail = mutableStateOf<MealX>(MealX())
    val is_on_favorit = mutableStateOf(true)
    val is_on_list = mutableStateOf(false)
    val is_on_detail = mutableStateOf(false)
    val is_on_splash = mutableStateOf(true)
    val loaded = mutableStateOf(false)
    val _meals = mutableStateOf(Meal(emptyList()))
    val c = MutableLiveData(0)
    val display = MutableLiveData(true)

    // utilisation d'un Flow pour mise à jour de l'IHM
    private val _fav: MutableState<List<MealX>> = mutableStateOf(emptyList())

    // les données exposées à l'IHM
    val favourites: State<List<MealX>> = _fav


    // message d'erreur éventuel
    var errorMessage: String by mutableStateOf("")

    // au lancement on récupère les données de l'API
    init {
        getMeal()
        getFavouritMeals()
    }

    // on tente de récupérer les données de l'API. Tout est interruptible (suspend function) ==> launch
    private fun getMeal() {
        viewModelScope.launch {

            try {
                _meals.value = repository.getAllMeals()
                loaded.value = true;

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getMealDetails(id: String) {
        var d: MealX = MealX()
        viewModelScope.launch {

            try {

                val temp = repository.getMealDetail(id).meals[0]
                if (temp.idMeal != mealDetail.value.idMeal) {
                    mealDetail.value = temp
                }
                loaded.value = true


            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }

        }

    }

    fun getFavouritMeals() {
        viewModelScope.launch {
            _fav.value = repository.getAllFavouritMeals()
        }
    }

    fun addMeal() {
        viewModelScope.launch {
            mealtofav.value.favoris = true
            repository.addMeal(mealtofav.value)

            getFavouritMeals()
            incrementCounter()
        }

    }

    fun deleteMeal() {

        viewModelScope.launch {
            repository.deletMealFromRoom(mealtofav.value)
            mealtofav.value.favoris = false


            getFavouritMeals()
            incrementCounter()
        }

    }

    fun incrementCounter() {
        c.postValue(c.value?.plus(1))
    }

    class MealVeiwModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MealViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MealViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}
