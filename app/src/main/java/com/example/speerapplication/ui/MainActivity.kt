package com.example.speerapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.speerapplication.R
/**
 * `MainActivity` is a class that extends `AppCompatActivity`. It represents the main activity of the application.
 *
 * @property navController This is a `NavController` instance used for navigation between different fragments in the application.
 * 
 * Inside this function:
 * - `installSplashScreen()` is called to install a splash screen for the activity.
 * - A `NavHostFragment` is found by its ID in the support fragment manager and is cast to `NavHostFragment`.
 * - The `NavController` for the `NavHostFragment` is retrieved and assigned to `navController`.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.
        findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.navController

    }
}