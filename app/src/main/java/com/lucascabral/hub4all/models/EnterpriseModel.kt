package com.lucascabral.hub4all.models

import com.google.gson.annotations.SerializedName

data class EnterpriseModel(

    @SerializedName("id")
    val id: Int?,
    @SerializedName("enterprise_name")
    val enterpriseName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("photo")
    val photoUrl: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("enterprise_type")
    val enterpriseType: EnterpriseType?
)