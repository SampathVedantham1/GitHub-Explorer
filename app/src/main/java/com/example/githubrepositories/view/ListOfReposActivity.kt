package com.example.githubrepositories.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepositories.R
import com.example.githubrepositories.domain.model.RepoItem
import com.example.githubrepositories.domain.model.SourceType
import com.example.githubrepositories.domain.viewmodel.ListOfRepoViewModel
import com.google.firebase.messaging.FirebaseMessaging

/**
 * Activity class responsible for displaying a list of GitHub repositories.
 */
class ListOfReposActivity : AppCompatActivity(), RepoItemClickListener {

    private lateinit var viewModel: ListOfRepoViewModel
    private lateinit var recyclerView: RecyclerView
    private var listOfRepoAdapter: ListOfReposAdapter? = null
    private var listOfRepo = listOf<RepoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_repositories)

        // Set up the action bar and initialize UI components.
        supportActionBar?.setTitle("GitHub Repositories")
        supportActionBar?.elevation = 0f

        recyclerView = findViewById(R.id.repo_recycler_view)

        getFCMToken()

        // Initialize the ViewModel and fetch the list of repositories.
        viewModel = ListOfRepoViewModel()
        viewModel.fetchRepoList(applicationContext)
        viewModel.repoListLiveData.observe(this@ListOfReposActivity) {
            showReposList(it)
            listOfRepo = it
        }
    }

    /**
     * Displays the list of repositories using a RecyclerView.
     *
     * @param reposList The list of repositories to be displayed.
     */
    private fun showReposList(reposList: List<RepoItem>) {
        listOfRepoAdapter = ListOfReposAdapter(reposList, this)
        recyclerView.adapter = listOfRepoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this@ListOfReposActivity)
        Log.d(
            "Application Debug",
            "showReposList-ListOfReposActivity: Adapter for the recycler view has been setup with list ${reposList}"
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_res_file, menu)
        val searchMenuItem = menu?.findItem(R.id.search)
        val searchView = searchMenuItem?.actionView as SearchView

        // Set up the SearchView with a listener for handling search queries.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Launch SearchResultActivity with the search query.
                val intent = Intent(this@ListOfReposActivity, SearchResultActivity::class.java)
                intent.putExtra("query", query)
                startActivity(intent)
                Log.d(
                    "Application Debug",
                    "onQueryTextSubmit-ListOfReposActivity: SearchResult Activity has been launched ${query}"
                )
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                // Launch SettingsActivity when the "Settings" menu item is selected.
                val settingsIntent = Intent(this@ListOfReposActivity, SettingsActivity::class.java)
                startActivity(settingsIntent)
                Log.d(
                    "Application Debug",
                    "onOptionsItemSelected-ListOfReposActivity: SettingsActivity has been launched"
                )
                true
            }

            else ->
                super.onOptionsItemSelected(item)

        }
    }

    /**
     * Handles clicks on individual repository items in the list.
     *
     * @param position The position of the clicked item in the list.
     */
    override fun onItemClick(position: Int) {
        val intent = Intent(this, RepoDetailsActivity::class.java)
        Log.d(
            "Application Debug",
            "onItemClick-ListOfReposActivity: The clicked item was ${listOfRepo[position]}"
        )

        // Pass repository item and source type to RepoDetailsActivity.
        intent.putExtra("RepoItem", listOfRepo[position])
        startActivity(intent)
    }

    /**
     * Retrieves the Firebase Cloud Messaging (FCM) token for the device.
     */
    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                val token = it.result
                Log.d("Application Debug", "getFCMToken-ListOfReposActivity: $token")
            }
        }
    }
}