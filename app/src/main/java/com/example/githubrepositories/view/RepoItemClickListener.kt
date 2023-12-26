package com.example.githubrepositories.view

/**
 * Interface definition for handling item clicks in a repository list.
 */
interface RepoItemClickListener {

    /**
     * Method to be invoked when a repository item is clicked.
     *
     * @param position The position of the clicked item in the list.
     */
    fun onItemClick(position: Int)
}