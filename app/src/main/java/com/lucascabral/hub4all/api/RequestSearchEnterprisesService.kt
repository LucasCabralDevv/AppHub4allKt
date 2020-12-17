package com.lucascabral.hub4all.api

import com.lucascabral.hub4all.api.response.EnterpriseResponse
import com.lucascabral.hub4all.constants.HeaderConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RequestSearchEnterprisesService {

    @GET("enterprises/")
    fun searchEnterprises(
        @Header(HeaderConstants.ACCESS_TOKEN) token: String,
        @Header(HeaderConstants.CLIENT) client: String,
        @Header(HeaderConstants.UID) uid: String,
        @Query("name") search: String
    ): Call<EnterpriseResponse>
}