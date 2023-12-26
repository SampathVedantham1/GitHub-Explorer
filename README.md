# GitHub-Explorer
- Explore GitHub effortlessly with our Android app! Log in securely using Firebase and Google account. Fetch user repositories via GitHub API with Retrofit, ensuring offline caching through Room DB. Enjoy the seamless search functionality directly from GitHub API. Logout with ease. Embracing MVVM architecture for a smooth experience.

# Get started
- In this application we acheived
  - Fetching the data from the GitHub API using Retrofit,
  - Caching the fetched data using Room database ,
  - Searching the repositories using remote API,
  - Integrating the FCM for push notifications(shown in the explanation video),
  - Adding support to signout.
 
# Technologies Used
- Kotlin
- Room
- Retrofit
- Android Studio
- Kotlin Coroutines

# References
- Screenshots:
    - [Login screen](https://drive.google.com/file/d/1Dfmn2zbyL3lWu3TiGEIuNECxEnk6rbYp/view?usp=sharing)
    - [List of Repositories screen](https://drive.google.com/file/d/1jYiINfdxqXF8N0cDgXZZLECsRBdxxVYo/view?usp=sharing)
    - [Repo details screen](https://drive.google.com/file/d/1EbzzGXCsM0uEscDbU9xd-15633a3wA_5/view?usp=sharing)
    - [Search result sceen](https://drive.google.com/file/d/1BsahKVnVNoN3_5JQN8s-EOHY-CNKE-qb/view?usp=sharing)
    - [Invalid search screen](https://drive.google.com/file/d/1L2OigrZoUEzPYccxLJLwc2FoSJ_ERTMa/view?usp=sharing)
    - [Settings screen](https://drive.google.com/file/d/1g-VAVZsjclfiBodyIIGlS3pFOg6TnYvT/view?usp=sharing)
- Video : 
    - [Video reference](https://drive.google.com/file/d/1uX2Sj_TvmzdYcroQ1q1XPN2FRH-6R7kM/view?usp=sharing)

# Projuct Structure
- At outer level, it consists of three modules :
  - data module :
    - Encompasses code related to the Room database.
  - domain module :
    - Involves code related to business logic.
  - view module :
    - Hold the code related to all screens that is all activities and adapters.

# Data module
  - Consists a file named RepoItemDAO which is a DAO(Data access object). This file is responsible for defining the methods that access the database.
  - Includes a file named AppDataBase which is a abstract class exteneding to RoomDatabase class and annotated with annotation @database.
  - Holds a file named RepoRepositoryImpl. Implementation of the RepoRepository interface, responsible for interacting with the data source.

# Domain module
  - The domain module consists of 3 sub modules. They are
    - model :
      - This module consists of all the data classes and enum class.
    - remote :
      - This module consists of all the classes related to notifications and API.

# viewModel
  - This module consists of the view model class .

# view
  - This consists of code related to all the activities and the adapters as follows :
    - ListOfReposActivity
    - RepoDetailsActivity
    - SearchResultActivity
    - SettingsActivity
    - ListOfReposAdapter
    - SearchResultAdapter
   
# Components used in project
- Activities and Fragments:
  - ListOfReposActivity:
    - Represents the main activity for displaying a list of GitHub repositories.
  - RepoDetailsActivity:
    - Displays details of a selected repository.
  - SearchResultActivity:
    - Shows search results for GitHub repositories.
  - SettingsActivity:
    - Shows settings scree.
- ViewModels:
  - ListOfRepoViewModel:
    - Manages UI-related data for the ListOfReposActivity and SearchResultActivity.
- Adapters:
  - ListOfReposAdapter:
    - Custom RecyclerView.Adapter used for populating the list of repositories in ListOfReposActivity.
  - SearchResultAdapter:
    - Adapter for displaying search results in SearchResultActivity.
- Database and Room:
  - AppDatabase:
    - Extends RoomDatabase and provides an abstract interface to access the underlying SQLite database.
  - RepoItemDAO:
    - Data Access Object interface for interacting with the local database.
  - RepoItem:
    - Represents a GitHub repository and is annotated as an @Entity for Room.
- Repository Pattern:
  - RepoRepository:
    - Interface defining repository operations, such as fetching repositories from the API and local database.
  - RepoRepositoryImpl:
    - Implementation of the RepoRepository interface, handling data retrieval and caching logic
- Retrofit for API Requests:
  - GitHubUserAPI:
    -  Interface defining API endpoints using Retrofit annotations.
  - Google Sign-In:
    - Integration with Google Sign-In for user authentication using Firebase.
  - Firebase Cloud Messaging (FCM):
    - FCMNotificationService: A service extending FirebaseMessagingService for handling FCM messages.
- ViewModels and LiveData:
  - Usage of ViewModel and LiveData for managing and observing UI-related data changes.
- RecyclerView:
  - To display the fetched list of Repositories.
