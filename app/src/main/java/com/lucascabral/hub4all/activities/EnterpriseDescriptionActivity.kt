package com.lucascabral.hub4all.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.lucascabral.hub4all.constants.ProjectConstants
import com.lucascabral.hub4all.databinding.ActivityEnterpriseDescriptionBinding

class EnterpriseDescriptionActivity : AppCompatActivity() {

    private var photoUrl: String = ""
    private var name: String = ""
    private var description: String = ""
    private lateinit var descriptionBinding: ActivityEnterpriseDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        descriptionBinding = ActivityEnterpriseDescriptionBinding.inflate(layoutInflater)
        setContentView(descriptionBinding.root)

        getEnterpriseDescription()
        setupToolbar()
        setupViews()
    }

    private fun getEnterpriseDescription() {
        photoUrl = intent.getStringExtra(ProjectConstants.PHOTO).toString()
        name = intent.getStringExtra(ProjectConstants.NAME).toString()
        description = intent.getStringExtra(ProjectConstants.DESCRIPTION).toString()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = name
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupViews() {
        Glide.with(applicationContext).load(ProjectConstants.BASE_URL_PHOTO + photoUrl)
            .into(descriptionBinding.descriptionEnterpriseImageView)
        descriptionBinding.descriptionEnterpriseTextView.text = description
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}