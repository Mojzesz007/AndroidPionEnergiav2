package com.platform.ui.solarContracts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.platform.R
import com.platform.SolarContractActivity
import com.platform.adapters.SolarContractRecyclerAdapter
import com.platform.api.EmsApi
import com.platform.databinding.FragmentSolarContactsBinding
import com.platform.pojo.contracts.Contracts
import com.platform.pojo.solarContracts.SolarContracts
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
class SolarontractsFragment : Fragment(), SolarContractRecyclerAdapter.OnItemClickListener {

    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    lateinit var swipeContainer: SwipeRefreshLayout
    lateinit var progresbar :ProgressBar
    private lateinit  var contractsViewModel: SolarContractsViewModel
    lateinit var nestedScrollView :NestedScrollView
    private var binding: FragmentSolarContactsBinding? = null

    var solarContracts: SolarContracts = SolarContracts()
    var solarContractsAll=SolarContracts()
    var start=0 as Integer
    var max=10 as Integer

    override  fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        contractsViewModel = ViewModelProvider(this).get(SolarContractsViewModel::class.java)
        binding = FragmentSolarContactsBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        progresbar= binding!!.FCSProgresBarPB
        nestedScrollView= binding!!.FCSNestedScrollViewNS
        getSolarContracts()
       /**
        * Metoda obsługująca paginację danych
        * @author Rafał Pasternak
        **/
        nestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(scrollY==nestedScrollView.getChildAt(0).measuredHeight-nestedScrollView.measuredHeight&&start<=solarContracts.totalCount) {
                start = max;
                progresbar!!.visibility=View.VISIBLE
                var step =max.toInt()+10
                max= step as Integer
                    getSolarContracts()
            }
        })
        /**
         *Funkcja króra przy pociągnięciu w dół odświeża dane
         * @author Rafał Pasternak
         **/
        swipeContainer= binding!!.FCSSwipeRefreshSR
        swipeContainer.setOnRefreshListener {
            start=0 as Integer
            max=10 as Integer
            solarContracts?.results?.clear()
            getSolarContracts()
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
        menu.findItem(R.id.action_settings).isVisible=false
        menu.findItem(R.id.action_settings2).isVisible=false
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
                val stringCopier = Gson().toJson(solarContractsAll, SolarContracts::class.java)
                solarContracts= Gson().fromJson<SolarContracts>(stringCopier, SolarContracts::class.java)
            }
            else
            {
                solarContracts?.results?.clear()
                var i=0
                while(i!=solarContractsAll.results.size){
                    if(solarContractsAll?.results[i]?.number?.toLowerCase()?.contains(newText.toLowerCase())==true
                        ||solarContractsAll?.results[i]?.recipient?.shortName?.toLowerCase()?.contains(newText.toLowerCase())==true
                        ||convertLongToTime(solarContractsAll.results[i].date).toLowerCase().contains(newText.toLowerCase())
                    )
                        solarContracts.results.add(solarContractsAll.results[i])
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    var responseCode: String =""
    /**
     * Metoda pobierająca wszystkie umowy
     * @author Rafał Pasternak
     * **/
    private fun getSolarContracts(){
        val call = emsApi.getSolarContracts(
            max,start
        )
        call.enqueue(object : Callback<SolarContracts> {
            override fun onResponse(call: Call<SolarContracts>, response: Response<SolarContracts>) =
                if (response.isSuccessful) {
                    if(solarContracts.results==null)
                        solarContracts= response.body()!!
                    else if(!response.body()!!.results.isEmpty())
                        solarContracts.results.addAll( response.body()!!.results)
                    val stringCopier = Gson().toJson(solarContracts, SolarContracts::class.java)
                    solarContractsAll= Gson().fromJson<SolarContracts>(stringCopier, SolarContracts::class.java)
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

            override fun onFailure(call: Call<SolarContracts>, t: Throwable) {
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
     * Metoda do wyświetlenia komunikatu użytkownikowi
     * @author Rafał Pasternak
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
     * Metoda inicjalizująca Recycler Viewer dla umów
     * @author Rafał Pasternak
     * **/
    fun initRecyclerView() {
        val recycleAdapter= activity?.let {
            SolarContractRecyclerAdapter(
                it,
              solarContracts,this
            )
        }
       val recyclerView = binding!!.FCSRowsRV
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
    /**
     * Metoda nadpisująca metodę z interfejsu onItemClickListener
     * odsyła do wybranej przez użytkownika umowy
     * @author Rafał Pasternak
     * **/
    override fun onItemClick(position: Int) {
        var index=solarContracts.results[position].id
        val solarContractIntent = Intent(context, SolarContractActivity::class.java)
        solarContractIntent.putExtra("index",index)
        startActivity(solarContractIntent)
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