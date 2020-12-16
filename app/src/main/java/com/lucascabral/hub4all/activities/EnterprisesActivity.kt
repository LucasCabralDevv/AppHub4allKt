package com.lucascabral.hub4all.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucascabral.hub4all.R
import com.lucascabral.hub4all.adapter.EnterpriseAdapter
import com.lucascabral.hub4all.api.RequestEnterpriseService
import com.lucascabral.hub4all.api.RetrofitClient
import com.lucascabral.hub4all.api.response.EnterpriseResponse
import com.lucascabral.hub4all.constants.HeaderConstants
import com.lucascabral.hub4all.models.EnterpriseModel
import kotlinx.android.synthetic.main.activity_enterprises.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterprisesActivity : AppCompatActivity() {

    private var accessToken: String = ""
    private var client: String = ""
    private var uid: String = ""

    private val adapterEnterprise: EnterpriseAdapter = EnterpriseAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enterprises)

        getHeaders()
        setupRecyclerView()
        requestEnterprises()
    }

    private fun requestEnterprises() {
        val remote = RetrofitClient.createService(RequestEnterpriseService::class.java)

        val call: Call<EnterpriseResponse> = remote.getEnterprises(accessToken, client, uid)
        call.enqueue(object : Callback<EnterpriseResponse> {
            override fun onResponse(
                call: Call<EnterpriseResponse>,
                response: Response<EnterpriseResponse>
            ) {
                if (response.isSuccessful) {
                    val enterprisesResponse: EnterpriseResponse? = response.body()
                    val enterprises: List<EnterpriseModel>? = enterprisesResponse?.enterprises
                    Log.d("Enterprises", "onResponse: " + enterprises.toString())
                    adapterEnterprise.setEnterpriseList(enterprises)
                    enterprisesRecycler.adapter = adapterEnterprise
                }else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.connecting_server_error_message),
                    Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<EnterpriseResponse>, t: Throwable) {

            }

        })
    }

    private fun getHeaders() {
        accessToken = intent.getStringExtra(HeaderConstants.ACCESS_TOKEN).toString()
        client = intent.getStringExtra(HeaderConstants.CLIENT).toString()
        uid = intent.getStringExtra(HeaderConstants.UID).toString()
    }

    private fun setupRecyclerView() {

        enterprisesRecycler.layoutManager = LinearLayoutManager(applicationContext)
        enterprisesRecycler.setHasFixedSize(true)
        enterprisesRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
    }
}