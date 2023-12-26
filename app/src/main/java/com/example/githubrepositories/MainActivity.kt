package com.example.githubrepositories

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.githubrepositories.view.ListOfReposActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onStart() {
        super.onStart()
        // Check if the user is already signed in and skip sign-in and go to the ListOfReposActivity
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this@MainActivity, ListOfReposActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check for notification permissions on Android versions higher than TIRAMISU
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }

        val btSignIn = findViewById<Button>(R.id.signin_button)

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Configure Google sign in options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Initialize Google sign in client
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Create the activity result launcher
        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // Check if the result is OK
                if (result.resultCode == Activity.RESULT_OK) {
                    // Get the sign in intent data
                    val data = result.data
                    // Handle the sign in result
                    handleSignInResult(data)
                }
            }

        // Set up the sign in button
        btSignIn.setOnClickListener {
            // Launch the sign in intent
            googleSignInLauncher.launch(googleSignInClient.signInIntent)
        }
    }

    /**
     * Handles the result of the Google Sign-In intent.
     *
     * @param data The intent data containing the sign-in result.
     */
    private fun handleSignInResult(data: Intent?) {
        try {
            // Get the Google sign in account from the intent data
            val account =
                GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)
            // Sign in with Firebase using the account
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            // Log the exception
            Log.w("TAG", "Google sign in failed", e)
            // Show a toast message with the error
            Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Signs in with Firebase using the Google Sign-In account.
     *
     * @param account The Google Sign-In account.
     */
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        // Get the credential from the account
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        // Sign in with Firebase using the credential
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                // Check if the sign in is successful
                if (task.isSuccessful) {
                    // Get the current user
                    val user = firebaseAuth.currentUser
                    // Show a toast message with the user's name and email
                    Toast.makeText(
                        this,
                        "Signed in as ${user?.displayName} (${user?.email})",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(
                        "Application Debug",
                        "firebaseAuthWithGoogle-MainActivity: signed is successfully as ${user?.displayName}"
                    )

                    // Navigate to ListOfReposActivity
                    val intent = Intent(this@MainActivity, ListOfReposActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Log the exception
                    Log.e("Application Error", "signInWithCredential:failure", task.exception)
                    // Show a toast message with the error
                    Toast.makeText(
                        this,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}