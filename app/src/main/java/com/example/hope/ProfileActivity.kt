package com.example.hope

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class ProfileActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var profileImageView: ImageView
    private lateinit var headerProfileImageView: ImageView
    private lateinit var welcomeTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var academicsButton: Button
    private lateinit var aboutCollegeButton: Button
    private lateinit var menuIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        headerProfileImageView = headerView.findViewById(R.id.profileImageView)
        profileImageView = findViewById(R.id.profileImageView)
        emailTextView = headerView.findViewById(R.id.emailTextView)
        welcomeTextView = findViewById(R.id.welcomeTextView)
        academicsButton = findViewById(R.id.academicsButton)
        aboutCollegeButton = findViewById(R.id.aboutCollegeButton)
        menuIcon = findViewById(R.id.menuIcon)

        val usernameFromIntent = intent.getStringExtra("USERNAME")
        val emailFromIntent = intent.getStringExtra("EMAIL")
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val usernameFromPreferences = sharedPreferences.getString("Username", "User")
        val emailFromPreferences = sharedPreferences.getString("Email", "user@example.com")

        val displayName = usernameFromIntent ?: usernameFromPreferences
        val displayEmail = emailFromIntent ?: emailFromPreferences

        welcomeTextView.text = "Hi,\n$displayName"
        emailTextView.text = displayEmail

        // Load saved profile image
        loadProfileImage()

        // Set up the profile image view to allow changing the profile photo
        headerProfileImageView.setOnClickListener {
            showImagePickerDialog()
        }
        profileImageView.setOnClickListener {
            showImagePickerDialog()
        }

        // Set up buttons for navigating to other activities
        academicsButton.setOnClickListener {
            // Handle Academics button click
        }

        aboutCollegeButton.setOnClickListener {
            // Handle About College button click
        }

        menuIcon.setOnClickListener {
            drawerLayout.openDrawer(navigationView)
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_about_us -> {
                    // Handle About Us navigation
                }
                R.id.nav_contact_us -> {
                    // Handle Contact Us navigation
                }
                R.id.nav_logout -> {
                    // Handle Logout navigation
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun showImagePickerDialog() {
        val images = arrayOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)

        val dialogView = layoutInflater.inflate(R.layout.dialog_image_picker, null)
        val recyclerView: RecyclerView = dialogView.findViewById(R.id.imageRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ImageAdapter(images.toList()) { imageResId ->
            saveProfileImage(imageResId)
            headerProfileImageView.setImageResource(imageResId)
            profileImageView.setImageResource(imageResId)
        }

        AlertDialog.Builder(this)
            .setTitle("Choose Profile Image")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun saveProfileImage(imageResId: Int) {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("ProfileImage", imageResId)
        editor.apply()
    }

    private fun loadProfileImage() {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val imageResId = sharedPreferences.getInt("ProfileImage", R.drawable.default_profile)
        headerProfileImageView.setImageResource(imageResId)
        profileImageView.setImageResource(imageResId)
    }
}




