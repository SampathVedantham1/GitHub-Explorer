package com.example.githubrepositories.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.githubrepositories.MainActivity
import com.example.githubrepositories.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

/**
 * Activity to display and manage user settings.
 */
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Set up the action bar title
        supportActionBar?.setTitle("Settings")
        supportActionBar?.elevation = 0f

        // Initialize sign-out button and Firebase Authentication
        val signOutButton = findViewById<MaterialButton>(R.id.settings_button)
        val auth = FirebaseAuth.getInstance()

        Log.d("Application Debug", "onCreate-SettingActivity: inside")

        // Set up sign-out button click listener
        signOutButton.setOnClickListener {
            Log.d("Application Debug", "onCreate-SettingActivity: signout button was clicked")
            // Sign out from Firebase
            auth.signOut()

            // Sign out from Google
            val googleSignInClient =
                GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
            googleSignInClient.signOut().addOnCompleteListener(this) {
                // Redirect to the main activity after sign-out
                val intent = Intent(this@SettingsActivity, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}