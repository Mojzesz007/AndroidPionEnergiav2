package com.platform.ui.sellInvoices

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.platform.R
import com.platform.adapters.SellInvoicesRecyclerAdapter
import com.platform.api.EmsApi
import com.platform.databinding.FragmentSellInvoicesBinding
import com.platform.pojo.contracts.Contracts
import com.platform.pojo.sellInvoices.SellInvoices
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SellInvoicesFragment : Fragment(), SellInvoicesRecyclerAdapter.OnItemClickListener {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    var sellInvoices: SellInvoices = SellInvoices()
    var sellInvoicesAll= SellInvoices()
    private var sellInvoicesViewModel: SellInvoicesViewModel? = null
    private var binding: FragmentSellInvoicesBinding? = null

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
        getSellInvoices()
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
    private fun getSellInvoices(){
        val call = emsApi.getSellInvoices(
        )
        call.enqueue(object : Callback<SellInvoices> {
            override fun onResponse(call: Call<SellInvoices>, response: Response<SellInvoices>) =
                if (response.isSuccessful) {
                    sellInvoices= response.body()!!
                    val stringCopier = Gson().toJson(sellInvoices, SellInvoices::class.java)
                    sellInvoicesAll= Gson().fromJson<SellInvoices>(stringCopier, SellInvoices::class.java)
                    initRecyclerView()
                } else {

                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)

                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<SellInvoices>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                responseCode = t.message.toString()
                openDialog(resources.getString(R.string.connectiontimeout))
                t.printStackTrace()
            }
        })
    }
    /**
     * Metoda pobierająca niezapłacone faktory sprzedażowe
     * @author Rafał Pasternak
     * **/
    private fun getUnpaidSellInvoices(){
        val call = emsApi.getUnpaidSellInvoices()
        call.enqueue(object : Callback<SellInvoices> {
            override fun onResponse(call: Call<SellInvoices>, response: Response<SellInvoices>) =
                if (response.isSuccessful) {
                    sellInvoices= response.body()!!
                    val stringCopier = Gson().toJson(sellInvoices, SellInvoices::class.java)
                    sellInvoicesAll= Gson().fromJson<SellInvoices>(stringCopier, SellInvoices::class.java)
                    initRecyclerView()
                } else {
                    val errorUtil = ee.parseError(response)
                }

            override fun onFailure(call: Call<SellInvoices>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                responseCode = t.message.toString()
                openDialog(resources.getString(R.string.connectiontimeout))
                t.printStackTrace()
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
                    val stringCopier = Gson().toJson(sellInvoicesAll, SellInvoices::class.java)
                    sellInvoices= Gson().fromJson<SellInvoices>(stringCopier, SellInvoices::class.java)
                }
                else
                {
                    sellInvoices.results.clear()
                    var i=0
                    /*
                    * sellInvoicesAll.results[i].grossTotal?.toString()?.toLowerCase()
                                ?.contains(newText.toLowerCase(Locale.ROOT)) == true
                            ||
                    * */
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
     *Funkcja obsłująca kliknięcia elementów wyboru sortowania
     * @author Rafał Pasternak
     **/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if(id==R.id.action_settings){
            getSellInvoices()
            Toast.makeText(activity,"Wszystkie",Toast.LENGTH_SHORT).show()
        }
        if(id==R.id.action_settings2){
            getUnpaidSellInvoices()
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