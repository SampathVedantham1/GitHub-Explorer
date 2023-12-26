package com.example.githubrepositories.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.githubrepositories.R
import com.example.githubrepositories.domain.model.RepoItem
import com.example.githubrepositories.domain.model.SearchRepoItem

/**
 * Activity to display details of a repository.
 */
class RepoDetailsActivity : AppCompatActivity() {
    private var receivedRepoItem: RepoItem? = null
    private var receivedSearchRepoItem: SearchRepoItem? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_details)

        val version = Build.VERSION.SDK_INT

        // Retrieve RepoItem from Intent
        receivedRepoItem = if (version >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("RepoItem", RepoItem::class.java)

        } else {
            intent.getParcelableExtra("RepoItem")
        }

        // Retrieve SearchRepoItem from Intent
        receivedSearchRepoItem = if (version >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("SearchRepoItem", SearchRepoItem::class.java)
        } else {
            intent.getParcelableExtra("SearchRepoItem")
        }


        supportActionBar?.elevation = 0f

        val description = findViewById<TextView>(R.id.description)
        val ownerName = findViewById<TextView>(R.id.owner_name)
        val language = findViewById<TextView>(R.id.language)
        val starCount = findViewById<TextView>(R.id.star_count)

        // Display details based on the received type (RepoItem or SearchRepoItem)
        if (receivedRepoItem != null) {
            Log.d("Application Debug", "onCreate-RepoDetailsActivity: The received repoItem is $receivedRepoItem")
            supportActionBar?.setTitle("${receivedRepoItem?.name} ")
            description.text = receivedRepoItem?.description
            ownerName.text = receivedRepoItem?.ownerName
            language.text = receivedRepoItem?.language
            starCount.text = receivedRepoItem?.stargazersCount.toString()
        } else if (receivedSearchRepoItem != null) {
            Log.d("Application Debug", "onCreate-RepoDetailsActivity: The received searchRepoItem is $receivedSearchRepoItem")
            supportActionBar?.setTitle("${receivedSearchRepoItem?.name} ")
            description.text = receivedSearchRepoItem?.description
            ownerName.text = receivedSearchRepoItem?.owner?.login
            language.text = receivedSearchRepoItem?.language
            starCount.text = receivedSearchRepoItem?.stargazers_count.toString()
        }
    }
}