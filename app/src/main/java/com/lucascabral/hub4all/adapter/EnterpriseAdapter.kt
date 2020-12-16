package com.lucascabral.hub4all.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lucascabral.hub4all.R
import com.lucascabral.hub4all.models.EnterpriseModel

class EnterpriseAdapter : RecyclerView.Adapter<EnterpriseAdapter.EnterpriseViewHolder>() {

    private var listEnterprise: List<EnterpriseModel>? = arrayListOf()
    private val baseUrl = "https://empresas.ioasys.com.br/api/v1/"

    fun setEnterpriseList(enterpriseList: List<EnterpriseModel>?) {
        this.listEnterprise = enterpriseList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnterpriseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_enterprise, parent, false)
        return EnterpriseViewHolder(view)
    }

    override fun onBindViewHolder(holder: EnterpriseViewHolder, position: Int) {

        val enterprise: EnterpriseModel = listEnterprise!![position]
        holder.bindData(enterprise, holder)
    }

    override fun getItemCount(): Int {
        return listEnterprise!!.count()
    }

    class EnterpriseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var imageEnterprise: ImageView = itemView.findViewById(R.id.adapterImageView)
        private var nameEnterprise: TextView = itemView.findViewById(R.id.adapterNameTextView)
        private var typeEnterprise: TextView = itemView.findViewById(R.id.adapterTypeTextView)
        private var countryEnterprise: TextView = itemView.findViewById(R.id.adapterCountryTextView)



        fun bindData(enterprise: EnterpriseModel, holder: EnterpriseViewHolder) {
            val baseUrl = "https://empresas.ioasys.com.br"

            Glide.with(holder.itemView.context)
                .load(baseUrl + enterprise.photo).into(holder.imageEnterprise)

            holder.nameEnterprise.text = enterprise.enterpriseName
            holder.typeEnterprise.text = enterprise.enterpriseType.enterpriseTypeName
            holder.countryEnterprise.text = enterprise.country
        }
    }

}