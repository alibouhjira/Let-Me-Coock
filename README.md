# 🍽️ RecipeApp – Application Android de recettes de cuisine

**RecipeApp** est une application Android développée en **Jetpack Compose** permettant de découvrir, rechercher et sauvegarder des recettes de cuisine, avec un accès direct à des **vidéos YouTube** pour chaque plat.

---

## ✨ Fonctionnalités

* 📋 **Liste de recettes** avec affichage clair et moderne
* 🔍 **Recherche de recettes**

  * par **nom**
  * par **ingrédient**
* ⭐ **Gestion des favoris**

  * ajout / suppression
  * accès rapide aux recettes sauvegardées
* ▶️ **Lien vers une vidéo YouTube**

  * redirection directe vers la vidéo de préparation du plat
* 🎨 Interface utilisateur **100 % Jetpack Compose**
* 📱 Architecture moderne et maintenable

---

## 🛠️ Technologies utilisées

* **Kotlin**
* **Jetpack Compose**
* **Material 3**
* **Navigation Compose**
* **Android Architecture Components**

  * ViewModel
  * State / StateFlow
* **Intent Android** (ouverture des liens YouTube)

---

## 🧩 Architecture

L’application suit une architecture **MVVM** claire et évolutive :

```
ui/            → Composables (écrans et composants UI)
viewmodel/     → Logique métier & gestion des états
data/          → Modèles de données (Recipe)
navigation/    → Gestion de la navigation
```

---

## 🔍 Recherche de recettes

La fonctionnalité de recherche permet :

* de filtrer les recettes par **nom**
* de filtrer par **ingrédients** (un ou plusieurs)

Le filtrage est **réactif** et se met à jour dynamiquement selon les entrées utilisateur.

---

## ⭐ Favoris

Les utilisateurs peuvent :

* ajouter une recette à leurs favoris
* consulter la liste des recettes favorites
* supprimer une recette des favoris à tout moment

---

## ▶️ Vidéos YouTube

Chaque recette peut inclure un **lien vers une vidéo YouTube**.
Un clic permet d’ouvrir automatiquement la vidéo dans :

* l’application YouTube (si installée)
* ou le navigateur par défaut

---

## 🚀 Installation

1. Cloner le dépôt :

```
git clone https://github.com/tonpseudo/recipeapp.git
```

2. Ouvrir le projet avec **Android Studio**
3. Lancer l’application sur un émulateur ou un appareil Android

---

## 📌 Améliorations possibles

* 💾 Persistance des favoris (Room / DataStore)
* 🌐 Connexion à une API distante
* 🧪 Tests unitaires et UI tests
* 🌍 Mode hors ligne
* ⏱️ Filtres avancés (temps de préparation, type de plat)

---

## 👤 Auteur

Développé par **Ali Bouhjira**
🔗 LinkedIn : [https://linkedin.com/in/ali-bouhjira-iot](https://linkedin.com/in/ali-bouhjira-iot)
💻 GitHub : [https://github.com/tonpseudo](https://github.com/tonpseudo)
