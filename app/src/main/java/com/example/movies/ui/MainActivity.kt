package com.example.movies.ui

import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.example.movies.R
import com.example.movies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    private val homeFragment = HomeFragment()
    private val favouritesFragment = FavouritesFragment()
    private val watchedFragment = WatchedFragment()

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setInsets()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)

        replaceFragment(homeFragment)

        binding.bottomNavigationBar.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.favourites -> replaceFragment(favouritesFragment)
                R.id.watched -> replaceFragment(watchedFragment)
                else -> {  }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        if (!fragmentManager.fragments.contains(fragment)) {
            transaction.add(R.id.fragmentContainer, fragment)
        }

        fragmentManager.fragments.forEach { transaction.hide(it) }
        transaction.show(fragment)

        transaction.commit()
    }

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.fragmentContainer) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }
}
