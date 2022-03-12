package com.platform.ui.costInvoices

import android.annotation.SuppressLint
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
import com.platform.R
import com.platform.adapters.CostInvoicesRecyclerAdapter
import com.platform.api.EmsApi
import com.platform.databinding.FragmentCostInvoicesBinding
import com.platform.pojo.costInvoices.CostInvoices
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class CostInvoicesFragment : Fragment(), CostInvoicesRecyclerAdapter.OnItemClickListener {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    private lateinit var costInvoicesViewModel: CostInvoicesViewModel
    lateinit var progresbar : ProgressBar
    lateinit var nestedScrollView : NestedScrollView
    lateinit var swipeContainer: SwipeRefreshLayout

    var costInvoices: CostInvoices = CostInvoices()
    var costInvoicesAll= CostInvoices()
    private var binding: FragmentCostInvoicesBinding? = null
    var start=0 as Integer
    var max=10 as Integer

    var currentDataSource="All"
    var dataSourceAll="All"
    var dataSourcePaid="UnPaid"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        costInvoicesViewModel = ViewModelProvider(this).get(
            CostInvoicesViewModel::class.java
        )
        binding = FragmentCostInvoicesBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        costInvoicesViewModel!!.text.observe(viewLifecycleOwner, Observer { })
        progresbar= binding!!.CLProgresBarPB
        nestedScrollView= binding!!.CLNestedScrollViewNS
        getCostInvoices()
        /**
         * Metoda obsługująca paginację danych
         * @author Rafał Pasternak
         **/
        nestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(scrollY==nestedScrollView.getChildAt(0).measuredHeight-nestedScrollView.measuredHeight&&start<=costInvoices.totalCount) {
                start = max;
                progresbar!!.visibility=View.VISIBLE
                var step =max.toInt()+10
                max= step as Integer
                if(currentDataSource.equals(dataSourceAll))
                    getCostInvoices()
                else if(currentDataSource.equals(dataSourcePaid))
                    getUnpaidCostInvoices()
            }
        })
        /**
         *Funkcja króra przy pociągnięciu w dół odświeża dane
         * @author Rafał Pasternak
         **/
        swipeContainer= binding!!.CLSwipeRefreshSR
        swipeContainer.setOnRefreshListener {
            start=0 as Integer
            max=10 as Integer
            costInvoices.results.clear()
            if(currentDataSource.equals(dataSourceAll))
                getCostInvoices()
            else if(currentDataSource.equals(dataSourcePaid))
                getUnpaidCostInvoices()
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
     * Metoda pobierająca wszystkie faktory sprzedażowe
     * @author Rafał Pasternak
     * **/
    private fun getCostInvoices(){
        val call = emsApi.getCostInvoices(
            max,start
        )
        call.enqueue(object : Callback<CostInvoices> {
            override fun onResponse(call: Call<CostInvoices>, response: Response<CostInvoices>) =
                if (response.isSuccessful) {
                    if(costInvoices.results==null)
                        costInvoices= response.body()!!
                    else if(!response.body()!!.results.isEmpty())
                        costInvoices.results.addAll( response.body()!!.results)
                    val stringCopier = Gson().toJson(costInvoices, CostInvoices::class.java)
                    costInvoicesAll= Gson().fromJson<CostInvoices>(stringCopier, CostInvoices::class.java)
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

            override fun onFailure(call: Call<CostInvoices>, t: Throwable) {
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
     * Metoda pobierająca niezapłacone faktory sprzedażowe
     * @author Rafał Pasternak
     * **/
    private fun getUnpaidCostInvoices(){
        val call = emsApi.getUnpaidCostInvoices(
            max,start
        )
        call.enqueue(object : Callback<CostInvoices> {
            override fun onResponse(call: Call<CostInvoices>, response: Response<CostInvoices>) =
                if (response.isSuccessful) {
                    if(costInvoices.results==null)
                        costInvoices= response.body()!!
                    else if(!response.body()!!.results.isEmpty())
                        costInvoices.results.addAll( response.body()!!.results)
                    val stringCopier = Gson().toJson(costInvoices, CostInvoices::class.java)
                    costInvoicesAll= Gson().fromJson<CostInvoices>(stringCopier, CostInvoices::class.java)
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

            override fun onFailure(call: Call<CostInvoices>, t: Throwable) {
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
     *Funkcja inicjalizująca menu sortowania
     *@author Rafał Pasterak
     **/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.navigation_drawer,menu)
        menu.findItem(R.id.action_search).isVisible=true
        menu.findItem(R.id.action_settings2).isVisible=true
        menu.findItem(R.id.action_settings3).isVisible=false
        menu.findItem(R.id.action_settings4).isVisible=false

        menu.findItem(R.id.action_settings).title="Wszystkie"
        menu.findItem(R.id.action_settings2).title="Niezapłacone"

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
                    val stringCopier = Gson().toJson(costInvoicesAll, CostInvoices::class.java)
                    costInvoices= Gson().fromJson<CostInvoices>(stringCopier, CostInvoices::class.java)
                }
                else
                {
                    costInvoices.results.clear()
                    var i=0
                    while(i!=costInvoicesAll.results.size){
                        if( costInvoicesAll.results[i].grossTotal?.toString()?.toLowerCase()?.contains(newText.toLowerCase())==true
                            ||costInvoicesAll.results[i].number.toLowerCase().contains(newText.toLowerCase())
                            ||costInvoicesAll.results[i].contractor?.shortName?.toString()?.toLowerCase()?.contains(newText.toLowerCase())==true
                            ||convertLongToTime(costInvoicesAll.results[i].issueDate).toLowerCase().contains(newText.toLowerCase())
                        )
                            costInvoices.results.add(costInvoicesAll.results[i])
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
            costInvoices.results.clear()
            start=0 as Integer
            max=10 as Integer
            getCostInvoices()
            Toast.makeText(activity,"Wszystkie",Toast.LENGTH_SHORT).show()
        }
        if(id==R.id.action_settings2){
            currentDataSource=dataSourcePaid
            costInvoices.results.clear()
            start=0 as Integer
            max=10 as Integer
            getUnpaidCostInvoices()
            Toast.makeText(activity,"Niezapłacone",Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
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
            CostInvoicesRecyclerAdapter(
                it,
                costInvoices,this
            )
        }
        val recyclerView = binding!!.CLRowsRV
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
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }
}