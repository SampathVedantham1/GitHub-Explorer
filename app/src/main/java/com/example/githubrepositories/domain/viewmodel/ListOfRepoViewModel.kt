package com.example.githubrepositories.domain.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.githubrepositories.data.AppDatabase
import com.example.githubrepositories.data.RepoRepositoryImpl
import com.example.githubrepositories.domain.model.RepoItem
import com.example.githubrepositories.domain.model.SearchResult
import com.example.githubrepositories.domain.remote.GitHubUserAPI
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ViewModel responsible for handling data related to repositories.
 */
class ListOfRepoViewModel : ViewModel() {
    private val _repoListMutableLiveData = MutableLiveData<List<RepoItem>>()
    val repoListLiveData: LiveData<List<RepoItem>> = _repoListMutableLiveData

    private var _searchMutableLiveData = MutableLiveData<SearchResult>()
    var searchLiveData: LiveData<SearchResult> = _searchMutableLiveData

    private val baseUrl = "https://api.github.com/"

    val position = MutableLiveData<Int>()

    /**
     * Fetches the list of repositories from the API or local database based on availability.
     *
     * @param applicationContext The application context.
     */
    fun fetchRepoList(applicationContext: Context) = runBlocking {
        val api =
            Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .build().create(GitHubUserAPI::class.java)

        val roomDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()

        val postDao = roomDatabase.repoItemDao()

        val repository = RepoRepositoryImpl()

        val listOfRepo = repository.getRepos(api, postDao)
        _repoListMutableLiveData.postValue(listOfRepo)

        Log.d("Application Debug", "fetchRepoList-ListOfRepoViewModel: the posted list is ${listOfRepo}")
    }

    /**
     * Fetches the search results for repositories based on the provided query.
     *
     * @param query The search query.
     */
    fun fetchSearchRepos(query: String) = runBlocking {
        val api =
            Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .build().create(GitHubUserAPI::class.java)

        api.getSearchRepositories(query)
            .enqueue(object : Callback<SearchResult> {
                override fun onResponse(
                    call: Call<SearchResult>,
                    response: Response<SearchResult>,
                ) {
                    if (response.body() != null) {
                        _searchMutableLiveData.postValue(response.body()!!)
                        Log.d(
                            "Application Debug",
                            "onResponse-ListOfRepoViewModel: Successfully search event and result is ${response.body()}"
                        )
                    }
                }

                override fun onFailure(
                    call: Call<SearchResult>,
                    throws: Throwable,
                ) {
                    Log.e(
                        "Application error",
                        "onFailure: Failed search event with error ${throws.message}"
                    )
                }
            })
    }
}