package com.lucascabral.hub4all.activities

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucascabral.hub4all.R
import com.lucascabral.hub4all.adapter.EnterpriseAdapter
import com.lucascabral.hub4all.api.RequestAllEnterprisesService
import com.lucascabral.hub4all.api.RequestSearchEnterprisesService
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
        requestAllEnterprises()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_menu)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Pesquisar"
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()

                if (query != null) {
                    requestSearchEnterprises(query)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun requestSearchEnterprises(query: String) {
        val remote = RetrofitClient.createService(RequestSearchEnterprisesService::class.java)
        val call: Call<EnterpriseResponse> =
            remote.searchEnterprises(accessToken, client, uid, query)

        call.enqueue(object : Callback<EnterpriseResponse>{
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
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.connecting_server_error_message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<EnterpriseResponse>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.internet_failure_message),
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }

    private fun requestAllEnterprises() {
        val remote = RetrofitClient.createService(RequestAllEnterprisesService::class.java)

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
                    adapterEnterprise.notifyDataSetChanged()

                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.connecting_server_error_message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<EnterpriseResponse>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.internet_failure_message),
                    Toast.LENGTH_LONG
                ).show()
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
