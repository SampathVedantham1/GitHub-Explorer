package com.example.githubrepositories.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepositories.R
import com.example.githubrepositories.domain.model.SearchResult
import com.example.githubrepositories.domain.model.SourceType
import com.example.githubrepositories.domain.viewmodel.ListOfRepoViewModel

/**
 * Activity to display search results for repositories based on a query.
 */
class SearchResultActivity : AppCompatActivity(), RepoItemClickListener {
    var searchResult: SearchResult? = null
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val query = intent.getStringExtra("query")

        // Set up the action bar title
        supportActionBar?.setTitle("Search results for \"$query\" ")
        supportActionBar?.elevation = 0f

        val viewModel = ListOfRepoViewModel()
        recyclerView = findViewById(R.id.search_result_recycler_view)

        // Fetch search results based on the query
        if (query != null) {
            viewModel.fetchSearchRepos(query)
            viewModel.searchLiveData.observe(this@SearchResultActivity) {
                Log.d(
                    "Application Debug",
                    "onCreate-SearchResultActivity: Observer for the query list value in onCreate is ${it.items}"
                )
                showSearchResult(it)
                searchResult = it
            }
        }
    }

    /**
     * Displays the search results in the RecyclerView.
     */
    private fun showSearchResult(searchResult: SearchResult?) {
        val invalidSearch = findViewById<View>(R.id.invalid_search)
        if (searchResult?.items?.isEmpty() == true) {
            invalidSearch.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.VISIBLE
            invalidSearch.visibility = View.GONE
            val adapter = searchResult?.let {
                Log.d(
                    "Application Debug",
                    "showSearchResult-SearchResultActivity: Inside the showSearch result fun and list is ${searchResult}"
                )
                SearchResultAdapter(it, this@SearchResultActivity)
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@SearchResultActivity)
        }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, RepoDetailsActivity::class.java)
        Log.d(
            "Application Debug",
            "onItemClick-SearchResultActivity: Clicked item is ${searchResult?.items?.get(position)}"
        )
        intent.putExtra("SearchRepoItem", searchResult?.items?.get(position))
        intent.putExtra("enum", SourceType.SEARCH_REPOS)
        startActivity(intent)
    }
}