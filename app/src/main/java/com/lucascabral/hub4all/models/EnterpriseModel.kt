package com.lucascabral.hub4all.models

import com.google.gson.annotations.SerializedName

class EnterpriseModel {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("enterprise_name")
    var enterpriseName: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("photo")
    var photo: String = ""

    @SerializedName("country")
    var country: String = ""

    @SerializedName("enterprise_type")
    lateinit var enterpriseType: EnterpriseType


}