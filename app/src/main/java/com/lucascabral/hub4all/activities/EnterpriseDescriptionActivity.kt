package com.lucascabral.hub4all.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.lucascabral.hub4all.R
import com.lucascabral.hub4all.constants.ProjectConstants
import kotlinx.android.synthetic.main.activity_enterprise_description.*

class EnterpriseDescriptionActivity : AppCompatActivity() {

    private var photo: String = ""
    private var name: String = ""
    private var description: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enterprise_description)

        getHeaders()
        setupToolbar()
        setupViews()
    }

    private fun getHeaders() {
        photo = intent.getStringExtra(ProjectConstants.PHOTO).toString()
        name = intent.getStringExtra(ProjectConstants.NAME).toString()
        description = intent.getStringExtra(ProjectConstants.DESCRIPTION).toString()
    }

    private fun setupToolbar() {
        if (supportActionBar != null) {
            supportActionBar!!.title = name
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupViews() {
        Glide.with(applicationContext).load(ProjectConstants.BASE_URL_PHOTO + photo)
            .into(descriptionEnterpriseImageView)
        descriptionEnterpriseTextView.text = description
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}