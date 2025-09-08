package thecyberseguy.usersapp.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import thecyberseguy.usersapp.R
import thecyberseguy.usersapp.databinding.ActivityMainBinding
import thecyberseguy.usersapp.extensions.addOnBackPressedDispatcher

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        addOnBackPressedDispatcher()
        setContentView(binding.root)
        setupStatusBar()
        setupNavController()
    }

    private fun setupStatusBar() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(systemBar.left, 0, systemBar.right, systemBar.bottom)
            binding.statusBarSpace.layoutParams.height = systemBar.top
            insets
        }

        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
    }

    private fun setupNavController() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

}