package com.lucascabral.hub4all.models

import com.google.gson.annotations.SerializedName

class EnterpriseModel {

    @SerializedName("id")
    private var id: Int = 0

    @SerializedName("enterprise_name")
    private var enterpriseName: String = ""

    @SerializedName("description")
    private var description: String = ""

    @SerializedName("photo")
    private var photo: String = ""

    @SerializedName("country")
    private var country: String = ""

    @SerializedName("enterprise_type")
    private lateinit var enterpriseType: EnterpriseType.Companion
}