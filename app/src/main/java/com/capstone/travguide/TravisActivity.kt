package com.capstone.travguide

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.capstone.travguide.databinding.ActivityTravisBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class TravisActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityTravisBinding
    private lateinit var navController: NavController

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTravisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)

        createGoogleSignInBuilderClient()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 444) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                checkAndGetGoogleUser()?.let {
                    navController.navigate(R.id.action_travisHomePageFragment_to_travisProfilePageFragment)
                } ?: run {
                    navController.navigate(R.id.action_travisHomePageFragment_to_travisLoginFragment)
                }
                true
            }
            R.id.action_logout -> {
                googleSignOut()
                Toast.makeText(this, "You are Logged out of Travis Guide!", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_travisHomePageFragment_to_travisLoginFragment)
                true
            }
            R.id.action_support -> { true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val signedInAccount = task.getResult(ApiException::class.java)
            navController.navigate(R.id.action_travisLoginFragment_to_travisHomePageFragment)

            Log.e("SIGNIN SUCCESS", "${signedInAccount.id}, ${signedInAccount.email} ${signedInAccount.displayName}" +
                    "${signedInAccount.account} ${signedInAccount.photoUrl}")
        } catch (e: ApiException) {
            Log.e(TravisActivity::class.java.simpleName, "Google SignIn Exception is ${e.localizedMessage}")
        }
    }

    private fun createGoogleSignInBuilderClient() {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestEmail()
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this@TravisActivity, googleSignInOption)
    }

    fun checkAndGetGoogleUser() = GoogleSignIn.getLastSignedInAccount(this@TravisActivity)

    fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 444)
    }

    fun googleSignOut() {
        checkAndGetGoogleUser()?.let {
            googleSignInClient.signOut().addOnCompleteListener { signOutTask ->
            }
        }
    }

    fun toolBarVisible(isVisible: Boolean = true) {
        binding.toolbar.visibility = if(isVisible) View.VISIBLE else View.GONE
    }
}