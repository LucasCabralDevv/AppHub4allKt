package com.lucascabral.hub4all.api.response

import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("success")
    var success: Boolean = true

    @SerializedName("id")
    var id: Int = 0
}