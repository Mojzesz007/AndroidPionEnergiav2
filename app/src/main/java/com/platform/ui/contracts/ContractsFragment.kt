package com.platform.ui.contracts

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.platform.adapters.ContractRecyclerAdapter
import com.platform.R
import com.platform.api.EmsApi
import com.platform.databinding.FragmentContractsBinding
import com.platform.pojo.contracts.Contracts
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.SimpleDateFormat
import javax.inject.Inject


/**
 * Klasa przedstawiająca umowy
 * @author Rafał Pasternak
 * **/
@AndroidEntryPoint
class ContractsFragment : Fragment(), ContractRecyclerAdapter.OnItemClickListener{

    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    lateinit var swipeContainer: SwipeRefreshLayout
    lateinit var progresbar :ProgressBar
    private lateinit  var contractsViewModel: ContractsViewModel
    lateinit var nestedScrollView :NestedScrollView
    private var binding: FragmentContractsBinding? = null

    var contracts: Contracts = Contracts()
    var contractsAll=Contracts()
    var start=0 as Integer
    var max=10 as Integer
    var currentDataSource="All"
    var dataSourceAll="All"
    var dataSourceIn90Days="In90Days"

    override  fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        contractsViewModel = ViewModelProvider(this).get(ContractsViewModel::class.java)
        binding = FragmentContractsBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        progresbar= binding!!.FCProgresBarPB
        nestedScrollView= binding!!.FCNestedScrollViewNS
        getContracts()
       /**
        * Metoda obsługująca paginację danych
        * @author Rafał Pasternak
        **/
        nestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(scrollY==nestedScrollView.getChildAt(0).measuredHeight-nestedScrollView.measuredHeight&&start<=contracts.totalCount) {
                start = max;
                progresbar!!.visibility=View.VISIBLE
                var step =max.toInt()+10
                max= step as Integer
                if(currentDataSource.equals(dataSourceAll))
                    getContracts()
                else if(currentDataSource.equals(dataSourceIn90Days))
                    getEndingIn90Days()
            }
        })
        /**
         *Funkcja króra przy pociągnięciu w dół odświeża dane
         * @author Rafał Pasternak
         **/
        swipeContainer= binding!!.FCSwipeRefreshSR
        swipeContainer.setOnRefreshListener {
            start=0 as Integer
            max=10 as Integer
            contracts.results.clear()
            if(currentDataSource.equals(dataSourceAll))
                getContracts()
            else if(currentDataSource.equals(dataSourceIn90Days))
                getEndingIn90Days()
        }
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }
    /**
     *Funkcja inicjalizująca menu sortowania
     *@author Rafał Pasterak
     **/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.navigation_drawer,menu)
        menu.findItem(R.id.action_settings2).isVisible=true
        menu.findItem(R.id.action_settings).title="Wszystkie"
        menu.findItem(R.id.action_settings2).title="90 dni do wypowiedzenia"
        menu.findItem(R.id.action_search).isVisible=true
        menu.findItem(R.id.action_settings3).isVisible=false
        menu.findItem(R.id.action_settings4).isVisible=false
        var searchItem = menu.findItem(R.id.action_search)
        var searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /**
             *Metoda wyszukiwająca po rekordach umów
             * @author Rafał Pasternak1
             **/
        override fun onQueryTextChange(newText: String): Boolean {
            if(newText.isEmpty()){
                //beznadziejne rozwiązanie ale kotlin ogranicza meh
                val stringCopier = Gson().toJson(contractsAll, Contracts::class.java)
                contracts= Gson().fromJson<Contracts>(stringCopier, Contracts::class.java)
            }
            else
            {
                contracts.results.clear()
                var i=0
                while(i!=contractsAll.results.size){
                    if(contractsAll?.results[i]?.title?.toLowerCase()?.contains(newText.toLowerCase())==true
                        ||contractsAll?.results[i]?.supplierNumber?.toLowerCase()?.contains(newText.toLowerCase())==true
                        ||contractsAll?.results[i]?.recipient?.shortName?.toLowerCase()?.contains(newText.toLowerCase())==true
                        ||convertLongToTime(contractsAll.results[i].endDate).toLowerCase().contains(newText.toLowerCase())
                    )
                        contracts.results.add(contractsAll.results[i])
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
     *Funkcja obsłująca kliknięcia elementów wyboru sortowania
     * @author Rafał Pasternak
     **/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if(id==R.id.action_settings){
            currentDataSource=dataSourceAll
            contracts.results.clear()
            start=0 as Integer
            max=10 as Integer
            getContracts()
            Toast.makeText(activity,"Wszystkie",Toast.LENGTH_SHORT).show()
        }
        if(id==R.id.action_settings2){
            currentDataSource=dataSourceIn90Days
            contracts.results.clear()
            start=0 as Integer
            max=10 as Integer
            getEndingIn90Days()
            Toast.makeText(activity,"90 dni do wypowiedzenia",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    var responseCode: String =""
    /**
     * Metoda pobierająca wszystkie umowy
     * @author Rafał Pasternak
     * **/
    private fun getContracts(){
        val call = emsApi.getContracts(
            max,start
        )
        call.enqueue(object : Callback<Contracts> {
            override fun onResponse(call: Call<Contracts>, response: Response<Contracts>) =
                if (response.isSuccessful) {
                    if(contracts.results==null)
                        contracts= response.body()!!
                    else if(!response.body()!!.results.isEmpty())
                        contracts.results.addAll( response.body()!!.results)
                    val stringCopier = Gson().toJson(contracts, Contracts::class.java)
                    contractsAll= Gson().fromJson<Contracts>(stringCopier, Contracts::class.java)
                    progresbar?.visibility=View.GONE
                    swipeContainer.setRefreshing(false)
                    initRecyclerView()
                } else {
                    progresbar?.visibility=View.GONE
                    swipeContainer.setRefreshing(false)
                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<Contracts>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                responseCode = t.message.toString()
                progresbar?.visibility=View.GONE
                swipeContainer.setRefreshing(false)
                openDialog(resources.getString(R.string.connectiontimeout))
                t.printStackTrace()
            }

        })
    }
    /**
     * Metoda pobierająca umowy kończące sie w 90 dni
     * @author Rafał Pasternak
     * **/
    private fun getEndingIn90Days(){
        val call = emsApi.getContractsEndingin90days(
            max,start
        )
        call.enqueue(object : Callback<Contracts> {
            override fun onResponse(call: Call<Contracts>, response: Response<Contracts>) =
                if (response.isSuccessful) {
                    if(contracts.results==null)
                        contracts= response.body()!!
                    else if(!response.body()!!.results.isEmpty())
                        contracts.results.addAll( response.body()!!.results)
                    val stringCopier = Gson().toJson(contracts, Contracts::class.java)
                    contractsAll= Gson().fromJson<Contracts>(stringCopier, Contracts::class.java)
                    progresbar?.visibility=View.GONE
                    swipeContainer.setRefreshing(false)
                    initRecyclerView()
                } else {
                    progresbar?.visibility=View.GONE
                    swipeContainer.setRefreshing(false)
                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<Contracts>, t: Throwable) {
                progresbar?.visibility=View.GONE
                swipeContainer.setRefreshing(false)
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                responseCode = t.message.toString()
                openDialog(resources.getString(R.string.connectiontimeout))
                t.printStackTrace()
            }
        })
    }
    /**
     * Metoda do wyświetlenia komunikatu użytkownikowi
     * @author Rafał Pasternak
     **/
    fun openDialog(message: String) {
        activity?.let {
            MaterialAlertDialogBuilder(it.applicationContext)
                .setTitle(resources.getString(R.string.messageTitle)) //jako res string
                .setMessage(message)
                .setPositiveButton("OK") { _, _ ->
                }
                .show()
        }
    }
    /**
     * Metoda inicjalizująca Recycler Viewer dla umów
     * @author Rafał Pasternak
     * **/
    fun initRecyclerView() {
        val recycleAdapter= activity?.let {
            ContractRecyclerAdapter(
                it,
              contracts,this
            )
        }
       val recyclerView = binding!!.FCRowsRV
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
    /**
     * Metoda nadpisująca metodę z interfejsu onItemClickListener
     * odsyła do wybranej przez użytkownika umowy
     * @author Rafał Pasternak
     * **/
    override fun onItemClick(position: Int) {
        Toast.makeText(activity, "Item $position clicked", Toast.LENGTH_SHORT).show()
        //tu bedize odsyłka do nowego activity z przesłanym id
    }
    /**
     *Metoda konwertująca datę w formacie Long do formatu czytelnego dla użytkownika
     * @param time data w formacie long
     * @return data w formacie ludzkim
     **/
    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long?): String {
        if(time!=null){val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd")
            return format.format(date)
        }
        else
            return ""
    }
}