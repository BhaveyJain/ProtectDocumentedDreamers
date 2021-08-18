package com.bhavey.protectlegaldreamers

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bhavey.protectlegaldreamers.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore
    private val TAG = "MainActivity"
    private var index = 0
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val NAME = "name"
        private const val AGEOUT = "ageOut"
        private const val LIVE = "live"
        private const val ABOUTME = "aboutMe"
        private const val PAINPOINT = "painPoint"
        private const val INDEX = "index"
        private const val URL = "url"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        index = 0
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        openDialog()
        populateScreen(null)


        binding.prevButton.setOnClickListener {
           if (index != 0) {
               populateScreen(true)
           }
        }
        binding.nextButton.setOnClickListener {
            populateScreen(false)
        }

        binding.storyButton.setOnClickListener {
            openDialog()
        }
    }

    private fun populateScreen(isLeft: Boolean?) {
        if (isLeft != null) {
            if (isLeft) {
                index--;
            } else {
                index++
            }
        }
        db.collection("Users").whereEqualTo(INDEX, index).get()
                .addOnSuccessListener { documents ->
                    if (documents.size() == 0) {
                        if (isLeft == true) {
                            index++
                        } else {
                            index--
                        }
                    }

                    for (document in documents) {
                        binding.tvInputName.text = document.getString(NAME)
                        binding.tvAgeOutInput.text = document.getString(AGEOUT)
                        binding.tvLiveInput.text = document.getString(LIVE)
                        binding.storyInput.setText(document.getString(ABOUTME))
                        binding.painPointInput.setText(document.getString(PAINPOINT))
                        Glide.with(this).load(document.getString(URL)).into(binding.profileImg)
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }.addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
    }

    private fun openDialog() {
        var dialog = InfoDialog()
        dialog.show(supportFragmentManager,"InfoDialog")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miSignPetition) {
            goToUrl("https://www.change.org/p/u-s-house-of-representatives-improve-the-dream-act?" +
                    "recruiter=945175323&utm_source=share_petition&utm_medium=copylink&utm_campaign=share_petition")
        } else if (item.itemId == R.id.miLegislation) {
            goToUrl("https://www.americaschildrenact.com/")
        } else if (item.itemId == R.id.miImproveTheDream) {
            goToUrl("https://www.improvethedream.org/takeaction")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToUrl(s: String) {
        val url = Uri.parse(s)
        startActivity(Intent(Intent.ACTION_VIEW, url))
    }
}