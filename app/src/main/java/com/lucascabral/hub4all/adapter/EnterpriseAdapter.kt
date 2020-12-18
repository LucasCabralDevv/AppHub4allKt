package com.lucascabral.hub4all.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lucascabral.hub4all.R
import com.lucascabral.hub4all.activities.EnterpriseDescriptionActivity
import com.lucascabral.hub4all.constants.ProjectConstants
import com.lucascabral.hub4all.models.EnterpriseModel

class EnterpriseAdapter : RecyclerView.Adapter<EnterpriseAdapter.EnterpriseViewHolder>() {

    private var listEnterprise: List<EnterpriseModel> = arrayListOf()

    fun setEnterpriseList(enterpriseList: List<EnterpriseModel>) {
        this.listEnterprise = enterpriseList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnterpriseViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_enterprise, parent, false)
        return EnterpriseViewHolder(view)
    }

    override fun onBindViewHolder(holder: EnterpriseViewHolder, position: Int) {

        val enterprise: EnterpriseModel = listEnterprise[position]
        holder.bindData(enterprise)
    }

    override fun getItemCount(): Int {
        return listEnterprise.count()
    }

    inner class EnterpriseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageEnterprise: ImageView = itemView.findViewById(R.id.adapterImageView)
        private val nameEnterprise: TextView = itemView.findViewById(R.id.adapterNameTextView)
        private val typeEnterprise: TextView = itemView.findViewById(R.id.adapterTypeTextView)
        private val countryEnterprise: TextView = itemView.findViewById(R.id.adapterCountryTextView)

        fun bindData(enterprise: EnterpriseModel) {

            itemView.apply {

                val baseUrl = ProjectConstants.BASE_URL_PHOTO

                Glide.with(context)
                    .load(baseUrl + enterprise.photoUrl).into(imageEnterprise)

                nameEnterprise.text = enterprise.enterpriseName
                typeEnterprise.text = enterprise.enterpriseType?.enterpriseTypeName
                countryEnterprise.text = enterprise.country

                setOnClickListener {
                    val intent =
                        Intent(context, EnterpriseDescriptionActivity::class.java)
                    intent.putExtra(ProjectConstants.PHOTO, enterprise.photoUrl)
                    intent.putExtra(ProjectConstants.NAME, enterprise.enterpriseName)
                    intent.putExtra(ProjectConstants.DESCRIPTION, enterprise.description)
                    context.startActivity(intent)
                }
            }
        }
    }
}

