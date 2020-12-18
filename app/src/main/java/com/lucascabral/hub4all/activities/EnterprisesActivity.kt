package com.lucascabral.hub4all.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucascabral.hub4all.R
import com.lucascabral.hub4all.adapter.EnterpriseAdapter
import com.lucascabral.hub4all.api.RequestEnterprisesService
import com.lucascabral.hub4all.api.RetrofitClient
import com.lucascabral.hub4all.models.EnterpriseResponse
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
        requestAllEnterprises()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.search_menu)
        val searchView = searchItem?.actionView as SearchView

        searchView.queryHint = getString(R.string.search_query_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.setQuery("", false)

                query?.let {
                    requestSearchEnterprises(it)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView.setOnCloseListener {
            requestAllEnterprises()
            false
        }

        return true
    }

    fun sendMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun requestAllEnterprises() {
        val remote = RetrofitClient.createService(RequestEnterprisesService::class.java)

        val call: Call<EnterpriseResponse> = remote.searchAllEnterprises(accessToken, client, uid)
        call.enqueue(object : Callback<EnterpriseResponse> {
            override fun onResponse(
                call: Call<EnterpriseResponse>,
                response: Response<EnterpriseResponse>
            ) {
                if (response.isSuccessful) {
                    val enterprisesResponse: EnterpriseResponse? = response.body()
                    val enterprises: List<EnterpriseModel>? = enterprisesResponse?.enterprises
                    adapterEnterprise.setEnterpriseList(enterprises ?: arrayListOf())
                    enterprisesRecycler.adapter = adapterEnterprise
                } else {
                    sendMessage(getString(R.string.connecting_server_error_message))
                }
            }

            override fun onFailure(call: Call<EnterpriseResponse>, t: Throwable) {
                sendMessage(getString(R.string.internet_failure_message))
            }
        })
    }

    private fun requestSearchEnterprises(query: String) {
        val remote = RetrofitClient.createService(RequestEnterprisesService::class.java)
        val call: Call<EnterpriseResponse> =
            remote.filterEnterprises(accessToken, client, uid, query)

        call.enqueue(object : Callback<EnterpriseResponse> {
            override fun onResponse(
                call: Call<EnterpriseResponse>,
                response: Response<EnterpriseResponse>
            ) {
                if (response.isSuccessful) {

                    showSearchedEnterprise(response, query)
                } else {
                    sendMessage(getString(R.string.connecting_server_error_message))
                }
            }

            override fun onFailure(call: Call<EnterpriseResponse>, t: Throwable) {
                sendMessage(getString(R.string.internet_failure_message))
            }
        })
    }

    private fun showSearchedEnterprise(
        response: Response<EnterpriseResponse>,
        query: String
    ) {
        val enterprisesResponse: EnterpriseResponse? = response.body()
        val enterprises: List<EnterpriseModel>? = enterprisesResponse?.enterprises
        if (enterprises?.isNotEmpty() == true) {
            adapterEnterprise.setEnterpriseList(enterprises)
            enterprisesRecycler.adapter = adapterEnterprise
            adapterEnterprise.notifyDataSetChanged()
        } else {
            sendMessage(getString(R.string.search_empty_enterprise_message, query))
        }
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
