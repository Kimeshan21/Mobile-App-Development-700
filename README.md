# Smart Pantry Manager

## Project Description

**Smart Pantry Manager** is a Java-based Android application developed for the **Mobile App Development 700** practical assignment,
created by: Kimeshan Naidoo student Number: 402313340

The application helps users manage ingredients stored in their pantry and discover recipes that can be prepared using ingredients they already have available. The main purpose of the application is to help reduce food waste by making better use of leftover ingredients.

The application allows users to:

* Add pantry ingredients
* View pantry ingredients
* Edit existing pantry ingredients
* Delete pantry ingredients
* Store ingredient quantities and units
* Store optional expiry dates
* View suggested recipes
* View recipe ingredients and cooking instructions
* Manage a local user profile
* Access application settings
* Receive recipe suggestions based strictly on available pantry ingredients

A recipe is only suggested when **all of its required ingredients are available in sufficient quantities**. Recipes with missing or insufficient ingredients are excluded from the suggestions.

---

## Technologies Used

* **Java**
* **Android Studio**
* **XML**
* **Android RecyclerView**
* **Room Persistence Library**
* **SQLite**
* **Android Intents**
* **SharedPreferences**
* **Git**
* **GitHub**

---

## Database

The application uses a local **SQLite database through the Room Persistence Library**.

Room was selected because the application does not require an online server or external API. It provides a structured way to work with the local SQLite database while allowing the application's data to persist after the application is closed and reopened.

The database contains information for pantry items, recipes and recipe ingredients.

### Pantry Items

Each pantry item contains:

* ID
* Ingredient name
* Quantity
* Unit
* Optional expiry date

### Recipes

Each recipe contains:

* ID
* Recipe name
* Cooking instructions

### Recipe Ingredients

Each recipe ingredient contains:

* ID
* Recipe ID
* Ingredient name
* Required quantity
* Unit

The application seeds the recipe database with a collection of recipes when the recipe table is empty.

---

## Main Features

### Pantry Management

Users can perform full CRUD operations on pantry ingredients:

* **Create** — Add a new ingredient
* **Read** — View pantry ingredients
* **Update** — Edit an existing ingredient
* **Delete** — Remove an ingredient

Input validation is used when adding or editing pantry items.

The application also displays an empty-state message when no pantry ingredients are available.

---

### Strict Recipe Matching

The application compares the user's pantry against the ingredients required by each recipe.

A recipe is displayed only when:

1. Every required ingredient exists in the pantry.
2. The available quantity is sufficient.
3. The ingredient and unit can be matched appropriately.

Partial matches are not displayed.

The matching system also handles common singular/plural variations and compatible unit conversions.

For example, if a recipe requires **500 g of chicken** and the pantry contains **1 kg of chicken**, the quantity can be converted and the recipe can qualify.

---

### Suggested Recipes

The Suggested Recipes screen displays only recipes that satisfy the strict recipe-matching rules.

If no recipes can be prepared using the current pantry contents, the application displays a meaningful empty-state message.

---

### Recipe Details

Selecting a suggested recipe opens the Recipe Detail screen.

The screen provides:

* Recipe name
* Required ingredients
* Quantities
* Units
* Cooking instructions

---

### User Profile

The Profile screen allows the user to enter and save:

* Name
* Email address

Profile information is stored locally using Android `SharedPreferences`, allowing the information to remain available when the user leaves and returns to the Profile screen.

---

### Settings

The Settings screen provides information about:

* Smart Pantry Manager
* The purpose of the application
* The database technology used
* The application's food-waste reduction objective

---

## Application Screens

The application contains the following main screens:

1. **My Pantry**
2. **Add/Edit Ingredient**
3. **Suggested Recipes**
4. **Recipe Detail**
5. **Settings**
6. **My Profile**

---

## Navigation

The main application navigation is structured as follows:

```text
My Pantry
│
├── Add Ingredient
│
├── Edit Ingredient
│
├── Suggested Recipes
│   │
│   └── Recipe Detail
│
├── Settings
│
└── My Profile
```

---

## Project Structure

The project is organised into separate components for activities, database functionality and RecyclerView adapters.

```text
MobileAppDev700/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com.example.thesmartpantrymanager/
│           │       ├── adapter/
│           │       ├── database/
│           │       ├── AddEditIngredientActivity.java
│           │       ├── MainActivity.java
│           │       ├── SuggestedRecipesActivity.java
│           │       ├── RecipeDetailActivity.java
│           │       ├── SettingsActivity.java
│           │       └── ProfileActivity.java
│           │
│           └── res/
│               ├── layout/
│               └── values/
│
└── README.md
```

---

## How to Run the Application

### Requirements

The following software is required:

* Android Studio
* Android SDK
* Java Development Kit
* Android emulator or compatible Android device

### Steps

1. Clone or download the repository.
2. Open the project in Android Studio.
3. Allow Android Studio to synchronise the Gradle project.
4. Connect an Android device or start an Android emulator.
5. Build the project.
6. Run the application from Android Studio.

The application creates and uses its local Room/SQLite database when it is first run.

---

## Testing

The application was tested for the following functionality:

* Adding pantry ingredients
* Viewing pantry ingredients
* Editing pantry ingredients
* Deleting pantry ingredients
* Input validation
* Pantry empty-state handling
* Database persistence after closing and reopening the application
* Recipe suggestions
* Strict recipe matching
* Missing ingredient handling
* Insufficient quantity handling
* Singular/plural ingredient matching
* Compatible unit conversion
* Recipe detail navigation
* Settings navigation
* Profile navigation
* Profile information persistence

---

## GitHub Repository

**Mobile App Development 700**

https://github.com/Kimeshan21/Mobile-App-Development-700

The repository contains the Android Studio source code and the incremental development history for the project.

---

## Project Purpose

Smart Pantry Manager was developed to demonstrate practical Android application development using Java, XML layouts, Room/SQLite database persistence, RecyclerView, custom adapters, Android Intents, SharedPreferences and Android application lifecycle concepts.

The application focuses on reducing food waste by helping users identify recipes that can be prepared using ingredients they already have available.
