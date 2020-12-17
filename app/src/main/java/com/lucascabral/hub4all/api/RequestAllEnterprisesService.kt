package com.lucascabral.hub4all.api

import com.lucascabral.hub4all.api.response.EnterpriseResponse
import com.lucascabral.hub4all.constants.HeaderConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RequestAllEnterprisesService {

    @GET("enterprises/")
    fun getEnterprises(
        @Header(HeaderConstants.ACCESS_TOKEN) token: String,
        @Header(HeaderConstants.CLIENT) client: String,
        @Header(HeaderConstants.UID) uid: String
    ): Call<EnterpriseResponse>
}