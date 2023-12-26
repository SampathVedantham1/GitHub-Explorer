package com.example.githubrepositories.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepositories.R
import com.example.githubrepositories.domain.model.SearchRepoItem
import com.example.githubrepositories.domain.model.SearchResult
import com.example.githubrepositories.domain.viewmodel.ListOfRepoViewModel

/**
 * Adapter for displaying search results in a RecyclerView.
 *
 * @param listOfRepos The [SearchResult] containing the list of search results.
 * @param itemClickListener The click listener for handling item clicks in the list.
 */
class SearchResultAdapter(private val listOfRepos: SearchResult,
                          private val itemClickListener: RepoItemClickListener
): RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    val viewModel : ListOfRepoViewModel = ListOfRepoViewModel()

    /**
     * ViewHolder class for search result items.
     *
     * @param itemView The view representing a search result item.
     */
    inner class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("Application Debug", "onClick-SearchResultViewHolder: position is ${adapterPosition}")
            val position = adapterPosition
            viewModel.position.postValue(position)
            if (position != RecyclerView.NO_POSITION) {
                itemClickListener.onItemClick(position)
            }
        }

        val title = itemView.findViewById<TextView>(R.id.repo_name_in_list_of_repos)
        val description = itemView.findViewById<TextView>(R.id.repo_description_in_list_of_repos)
        val count = itemView.findViewById<TextView>(R.id.repo_count_in_list_of_repos)

        /**
         * Sets data for the search result item.
         *
         * @param repo The [SearchRepoItem] object representing the search result.
         */
        fun setData(repo: SearchRepoItem) {
            Log.d("Application Debug", "setData-SearchResultViewHolder: the name is ${repo.owner.login} and the stars is ${repo.stargazers_count}")
            title.text = repo.name
            description.text = repo.description
            count.text = repo.stargazers_count.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_of_repo_item, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfRepos.items.size
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val repo = listOfRepos.items[position]
        holder.setData(repo)
    }
}