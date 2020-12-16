package com.lucascabral.hub4all.models

import com.google.gson.annotations.SerializedName

class EnterpriseType {

    companion object {
        @SerializedName("id")
        private var idType: Int = 0

        @SerializedName("enterprise_type_name")
        private var enterpriseTypeName: String = ""
    }
}