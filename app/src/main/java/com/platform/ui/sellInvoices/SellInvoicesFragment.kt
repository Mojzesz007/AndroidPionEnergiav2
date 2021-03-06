package com.platform.ui.sellInvoices

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.platform.CostInvoiceActivity
import com.platform.R
import com.platform.SellInvoiceActivity
import com.platform.adapters.SellInvoicesRecyclerAdapter
import com.platform.api.EmsApi
import com.platform.databinding.FragmentSellInvoicesBinding
import com.platform.pojo.sellInvoices.SellInvoices
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class SellInvoicesFragment : Fragment(), SellInvoicesRecyclerAdapter.OnItemClickListener {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    private lateinit var sellInvoicesViewModel: SellInvoicesViewModel
    lateinit var progresbar : ProgressBar
    lateinit var nestedScrollView : NestedScrollView
    lateinit var swipeContainer: SwipeRefreshLayout

    var sellInvoices: SellInvoices = SellInvoices()
    var sellInvoicesAll= SellInvoices()
    private var binding: FragmentSellInvoicesBinding? = null
    var start=0 as Integer
    var max=10 as Integer

    var currentDataSource="All"
    var dataSourceAll="All"
    var dataSourcePaid="UnPaid"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        sellInvoicesViewModel = ViewModelProvider(this).get(
            SellInvoicesViewModel::class.java
        )
        binding = FragmentSellInvoicesBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        sellInvoicesViewModel!!.text.observe(viewLifecycleOwner, Observer { })
        progresbar= binding!!.SLProgresBarPB
        nestedScrollView= binding!!.SLNestedScrollViewNS
        getSellInvoices()
        /**
         * Metoda obs??uguj??ca paginacj?? danych
         * @author Rafa?? Pasternak
         **/
        nestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(scrollY==nestedScrollView.getChildAt(0).measuredHeight-nestedScrollView.measuredHeight&&start<=sellInvoices.totalCount) {
                start = max;
                progresbar!!.visibility=View.VISIBLE
                var step =max.toInt()+10
                max= step as Integer
                if(currentDataSource.equals(dataSourceAll))
                    getSellInvoices()
                else if(currentDataSource.equals(dataSourcePaid))
                    getUnpaidSellInvoices()
            }
        })
        /**
         *Funkcja kr??ra przy poci??gni??ciu w d???? od??wie??a dane
         * @author Rafa?? Pasternak
         **/
        swipeContainer= binding!!.SLSwipeRefreshSR
        swipeContainer.setOnRefreshListener {
            start=0 as Integer
            max=10 as Integer
            sellInvoices?.results?.clear()
            if(currentDataSource.equals(dataSourceAll))
                getSellInvoices()
            else if(currentDataSource.equals(dataSourcePaid))
                getUnpaidSellInvoices()
        }
        return root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    var responseCode: String =""
    /**
     * Metoda pobieraj??ca wszystkie faktory sprzeda??owe
     * @author Rafa?? Pasternak
     * **/
    private fun getSellInvoices(){
        val call = emsApi.getSellInvoices(
            max,start
        )
        call.enqueue(object : Callback<SellInvoices> {
            override fun onResponse(call: Call<SellInvoices>, response: Response<SellInvoices>) =
                if (response.isSuccessful) {
                    if(sellInvoices.results==null)
                        sellInvoices= response.body()!!
                    else if(!response.body()!!.results.isEmpty())
                        sellInvoices.results.addAll( response.body()!!.results)
                    val stringCopier = Gson().toJson(sellInvoices, SellInvoices::class.java)
                    sellInvoicesAll= Gson().fromJson<SellInvoices>(stringCopier, SellInvoices::class.java)
                    progresbar?.visibility=View.GONE
                    swipeContainer.setRefreshing(false)
                    initRecyclerView()
                } else {

                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                        progresbar?.visibility=View.GONE
                        swipeContainer.setRefreshing(false)
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<SellInvoices>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                responseCode = t.message.toString()
                openDialog(resources.getString(R.string.connectiontimeout))
                t.printStackTrace()
                progresbar?.visibility=View.GONE
                swipeContainer.setRefreshing(false)
            }
        })
    }
    /**
     * Metoda pobieraj??ca niezap??acone faktory sprzeda??owe
     * @author Rafa?? Pasternak
     * **/
    private fun getUnpaidSellInvoices(){
        val call = emsApi.getUnpaidSellInvoices(
            max,start
        )
        call.enqueue(object : Callback<SellInvoices> {
            override fun onResponse(call: Call<SellInvoices>, response: Response<SellInvoices>) =
                if (response.isSuccessful) {
                    if(sellInvoices.results==null)
                        sellInvoices= response.body()!!
                    else if(!response.body()!!.results.isEmpty())
                        sellInvoices.results.addAll( response.body()!!.results)
                    val stringCopier = Gson().toJson(sellInvoices, SellInvoices::class.java)
                    sellInvoicesAll= Gson().fromJson<SellInvoices>(stringCopier, SellInvoices::class.java)
                    progresbar?.visibility=View.GONE
                    swipeContainer.setRefreshing(false)
                    initRecyclerView()
                } else {

                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                        progresbar?.visibility=View.GONE
                        swipeContainer.setRefreshing(false)
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<SellInvoices>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                responseCode = t.message.toString()
                openDialog(resources.getString(R.string.connectiontimeout))
                t.printStackTrace()
                progresbar?.visibility=View.GONE
                swipeContainer.setRefreshing(false)
            }
        })
    }
    /**
     *Funkcja inicjalizuj??ca menu sortowania
     *@author Rafa?? Pasterak
     **/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.navigation_drawer,menu)
        menu.findItem(R.id.action_search).isVisible=true
        menu.findItem(R.id.action_settings2).isVisible=true
        menu.findItem(R.id.action_settings3).isVisible=false
        menu.findItem(R.id.action_settings4).isVisible=false

        menu.findItem(R.id.action_settings).title="Wszystkie"
        menu.findItem(R.id.action_settings2).title="Niezap??acone"

        var searchItem = menu.findItem(R.id.action_search)
        var searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /**
             *Metoda wyszukiwaj??ca po rekordach um??w
             * @author Rafa?? Pasternak1
             **/
            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.isEmpty()){
                    //beznadziejne rozwi??zanie ale kotlin ogranicza meh
                    val stringCopier = Gson().toJson(sellInvoicesAll, SellInvoices::class.java)
                    sellInvoices= Gson().fromJson<SellInvoices>(stringCopier, SellInvoices::class.java)
                }
                else
                {
                    sellInvoices?.results?.clear()
                    var i=0
                    while(i!=sellInvoicesAll.results.size){
                        if( sellInvoicesAll.results[i].grossTotal?.toString()?.toLowerCase()?.contains(newText.toLowerCase())==true
                            ||sellInvoicesAll.results[i].number.toLowerCase().contains(newText.toLowerCase())
                            ||sellInvoicesAll.results[i].contractorName?.toString()?.toLowerCase()?.contains(newText.toLowerCase())==true
                            ||convertLongToTime(sellInvoicesAll.results[i].issueDate).toLowerCase().contains(newText.toLowerCase())
                        )
                            sellInvoices.results.add(sellInvoicesAll.results[i])
                        i++
                    }
                }
                initRecyclerView()
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
    /**
     *Funkcja obs??uj??ca klikni??cia element??w wyboru sortowania
     * @author Rafa?? Pasternak
     **/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if(id==R.id.action_settings){
            currentDataSource=dataSourceAll
            sellInvoices?.results?.clear()
            start=0 as Integer
            max=10 as Integer
            getSellInvoices()
            Toast.makeText(activity,"Wszystkie",Toast.LENGTH_SHORT).show()
        }
        if(id==R.id.action_settings2){
            currentDataSource=dataSourcePaid
            sellInvoices?.results?.clear()
            start=0 as Integer
            max=10 as Integer
            getUnpaidSellInvoices()
            Toast.makeText(activity,"Niezap??acone",Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }
    /**
     * Metoda do wy??wietlenia komunikatu u??ytkownikowi
     * @author Rafa?? Pasternak
     **/
    fun openDialog(message: String) {
        /*activity?.let {
            MaterialAlertDialogBuilder(it.applicationContext)
                .setTitle(resources.getString(R.string.messageTitle)) //jako res string
                .setMessage(message)
                .setPositiveButton("OK") { _, _ ->
                }
                .show()
        }*/
    }
    /**
     * Metoda inicjalizuj??ca Recycler Viewer dla um??w
     * @author Rafa?? Pasternak
     * **/
    fun initRecyclerView() {
        val recycleAdapter= activity?.let {
            SellInvoicesRecyclerAdapter(
                it,
                sellInvoices,this
            )
        }
        val recyclerView = binding!!.SLRowsRV
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
    /**
     * Metoda nadpisuj??ca metod?? z interfejsu onItemClickListener
     * odsy??a do wybranej przez u??ytkownika umowy
     * @author Rafa?? Pasternak
     * **/
    override fun onItemClick(position: Int) {
        var index=sellInvoices.results[position].id
        val sellInvoiceIntent = Intent(context, SellInvoiceActivity::class.java)
        sellInvoiceIntent.putExtra("index",index)
        startActivity(sellInvoiceIntent)
    }
    /**
     *Metoda konwertuj??ca dat?? w formacie Long do formatu czytelnego dla u??ytkownika
     * @param time data w formacie long
     * @return data w formacie ludzkim
     **/
    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }
}