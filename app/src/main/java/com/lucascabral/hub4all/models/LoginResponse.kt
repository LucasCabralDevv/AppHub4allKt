package com.lucascabral.hub4all.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("id")
    val id: Int?
)