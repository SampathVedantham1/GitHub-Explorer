package com.example.githubrepositories.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepositories.domain.viewmodel.ListOfRepoViewModel
import com.example.githubrepositories.R
import com.example.githubrepositories.domain.model.RepoItem

/**
 * Adapter class for displaying a list of GitHub repositories in a RecyclerView.
 *
 * @param listOfRepos The list of repositories to be displayed.
 * @param itemClickListener The listener for handling item clicks in the list.
 */
class ListOfReposAdapter(
    private val listOfRepos: List<RepoItem>,
    private val itemClickListener: RepoItemClickListener
) : RecyclerView.Adapter<ListOfReposAdapter.ListOfReposViewHolder>() {
    val viewModel : ListOfRepoViewModel = ListOfRepoViewModel()

    /**
     * ViewHolder class representing individual items in the list.
     *
     * @param itemView The view representing a repository item.
     */
    inner class ListOfReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        /**
         * Handles clicks on repository items.
         */
        override fun onClick(v: View) {
            Log.d("Application Debug", "onClick: onCLick and position is ${adapterPosition}")
            val position = adapterPosition
            viewModel.position.postValue(position)
            if (position != RecyclerView.NO_POSITION) {
                itemClickListener.onItemClick(position)
            }
        }

        // Views within the repository item layout.
        val title = itemView.findViewById<TextView>(R.id.repo_name_in_list_of_repos)
        val description = itemView.findViewById<TextView>(R.id.repo_description_in_list_of_repos)
        val count = itemView.findViewById<TextView>(R.id.repo_count_in_list_of_repos)

        /**
         * Sets data for the repository item.
         *
         * @param repo The repository item to display.
         */
        fun setData(repo: RepoItem) {
            title.text = repo.name
            description.text = repo.description
            count.text = repo.stargazersCount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfReposViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_of_repo_item, parent, false)
        return ListOfReposViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfRepos.size
    }

    override fun onBindViewHolder(holder: ListOfReposViewHolder, position: Int) {
        val repo = listOfRepos[position]
        holder.setData(repo)
    }
}