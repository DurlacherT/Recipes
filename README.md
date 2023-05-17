


## Recipes app

1.	Introduction
1.1	Purpose 
The main purpose of this app is to allow users to manage their favorite recipes. Users obtain recipes by searching and filtering a set of already existing recipes. Those recipes can be added to individual, thematically structured collections. The app also allows users to write their recipes either based on already existing recipes or from scratch. Users should also have the possibility to add pictures to recipes and share them with other users. The app should furthermore give different users the possibility to write comments for the recipes of others and to give them a rating. By doing this the app combines traditional recipe collecting with social media elements.
MoSCoW Method
This system should provide a secure and transparent method for storing and transferring data and transactions. The MoSCoW method will be used to divide the requirements into four categories: Must-have, Should-have, Could-have, and Won't-have.

1.1.1	Must-have Requirements
•	The app must include a series of screens connected by navigation elements. Those screens include a “home screen”, a “search and filter screen”, an “individual recipe screen”, an “own collections screen”, and a “new recipe screen”. The main UI elements are built with the help of the Jetpack Compose toolkit. (Durlacher)
•	The search and filter screen must allow users to search for recipes according to criteria such as breakfast, lunch or dinner, prep time, or by ingredients. This screen allows user input in the form of text fields and checkboxes. Results are presented in the form of a list. (Merve)
•	The app must be able to display individual recipes. This includes a textual description as well as pictures of the dish. The textual description of a recipe consists of a name, description, ingredients, and steps in the preparation.
•	The app must give users the possibility to select individual recipes and add them to their collections. Adding individual recipes to an individual collection is possible in the search screen as well as the individual recipe screen. The “own collections screen” provides an overview of the collection the user has created. (Merve)
•	The app must give users the possibility to write new recipes and permanently store them. The “new recipe screen” includes text fields for the name, description, ingredients, and steps in the preparation. (Durlacher)
1.1.2	Should-have Requirements
•	The app should allow users to register with their email addresses and log in with their email addresses and a password. To implement user authentication as well as the database we are going to use Google Firebase. Google Firebase provides drop-in authentication solutions that manage the UI flows for signing-in users. (Durlacher)
•	The app should store user data as well as recipe data in a cloud-based database. For the database backend, we are also going to use Google Firebase. This service provides NoSQL cloud database services to store recipes in the form of JSON documents. Cloud Storage for Firebase is used to store images. (Durlacher)
•	The app should allow users to add their pictures to newly created recipes. Users should be able to add pictures from their phones to their recipes.
•	The app should allow users to share recipes on social media. (Durlacher)
•	The app should allow users to create more than one collection of recipes. (Merve)
1.1.3	Could-have Requirements
•	The app could allow users to rate and review recipes and view ratings and reviews from other users. The rating system should be based on numerical values between 0 and 5 represented in the form of stars.
•	The app could organize recipes into categories, such as breakfast, lunch, dinner, dessert, and snacks. Those categories are added to the data of individual recipes and on the search and filter screen. 
•	The app could allow users to view and edit their profile information, such as their name, email address, and dietary preferences. An additional profile screen could allow users to customize the app. (Merve)
1.1.4	Won't-have Requirements
•	The app won't include ads to provide users with an uninterrupted experience while browsing recipes.
•	The app won't require users to make in-app purchases to access features or content.

1.1.5	Non-functional Requirements

The app should provide an intuitive and easy way to navigate between screens. The color design should fit the main purpose of the app, namely presenting dishes to the user. The UI design should also be colorful and inviting.
The stored information in the app, which is based on cloud services, should be always available and reliable.
The app should load quickly and respond to user actions promptly, with minimal lag or delay. It is also intended that the app can be used on mobile phones of the last generation, 2-3 years ago.
