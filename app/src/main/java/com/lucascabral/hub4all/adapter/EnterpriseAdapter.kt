package com.lucascabral.hub4all.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lucascabral.hub4all.R

class EnterpriseAdapter : RecyclerView.Adapter<EnterpriseAdapter.EnterpriseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnterpriseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_enterprise, parent, false)
        return EnterpriseViewHolder(view)
    }

    override fun onBindViewHolder(holder: EnterpriseViewHolder, position: Int) {

        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return 0
    }


    class EnterpriseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var imageEnterprise: ImageView = itemView.findViewById(R.id.adapterImageView)
        private var nameEnterprise: TextView = itemView.findViewById(R.id.adapterNameTextView)
        private var typeEnterprise: TextView = itemView.findViewById(R.id.adapterTypeTextView)
        private var countryEnterprise: TextView = itemView.findViewById(R.id.adapterCountryTextView)

        fun bindData(position: Int) {

            this.nameEnterprise.text = ""

        }
    }

}