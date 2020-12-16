package com.lucascabral.hub4all.api.response

import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("success")
    private var success: Boolean = true

    @SerializedName("id")
    private var id: Int = 0
}