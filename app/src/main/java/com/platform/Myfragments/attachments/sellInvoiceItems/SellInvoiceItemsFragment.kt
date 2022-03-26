package com.platform.Myfragments.attachments.sellInvoiceItems

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

import com.platform.ImageActivity
import com.platform.Myfragments.attachments.sellInvoiceItems.SellInvoiceItemsViewModel
import com.platform.R

import com.platform.adapters.SellInvoiceItemsRecyclerAdapter
import com.platform.api.EmsApi
import com.platform.databinding.FragmentAttachmentsBinding
import com.platform.databinding.FragmentSellInvoiceItemsBinding
import com.platform.pojo.costInvoice.attachments.Attachments
import com.platform.pojo.sellInvoices.sellInvoice.SellInvoice
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject


@AndroidEntryPoint
class  SellInvoiceItemsFragment : Fragment(), SellInvoiceItemsRecyclerAdapter.OnItemClickListener {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil
    var index: Int =-1
    var sellInvoice: SellInvoice= SellInvoice()
    var downloadid: Long = 0
    private lateinit var viewOnlyattachmentsViewModel: SellInvoiceItemsViewModel
    lateinit var progresbar : ProgressBar
    lateinit var nestedScrollView : NestedScrollView
    lateinit var swipeContainer: SwipeRefreshLayout

    private var binding: FragmentSellInvoiceItemsBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewOnlyattachmentsViewModel = ViewModelProvider(this).get(
           SellInvoiceItemsViewModel::class.java
        )
        binding = FragmentSellInvoiceItemsBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        viewOnlyattachmentsViewModel!!.text.observe(viewLifecycleOwner, Observer { })
        progresbar= binding!!.SIIProgresBarPB
        nestedScrollView= binding!!.SIINestedScrollViewNS


        /**
         *Funkcja króra przy pociągnięciu w dół odświeża dane
         * @author Rafał Pasternak
         **/
        swipeContainer= binding!!.SIISwipeRefreshSR
        swipeContainer.setOnRefreshListener {
            sellInvoice?.items?.clear()
            getSellInvoiceByID()
        }
        return root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        if (arguments != null) {
            index = requireArguments().getString(TEXT.toString()).toString().toInt()
            println(index) }
        getSellInvoiceByID()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    /**
     * Metoda do wyświetlenia komunikatu użytkownikowi
     * @author Rafał Pasternak
     **/

    fun openDialog(message: String) {

    }
    private fun getSellInvoiceByID() {
        val call = emsApi.getSellInvoiceById(
            index
        )
        call.enqueue(object : Callback<SellInvoice> {
            override fun onResponse(call: Call<SellInvoice>, response: Response<SellInvoice>) {
                if (response.isSuccessful) {
                    sellInvoice= response.body()!!
                   initRecyclerView()
                    swipeContainer.setRefreshing(false)
                    progresbar?.visibility=View.GONE
                }
                else {
                    progresbar?.visibility=View.GONE
                    swipeContainer.setRefreshing(false)
                    openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SellInvoice>, t: Throwable) {
                openDialog(resources.getString(R.string.connectiontimeout))
                swipeContainer.setRefreshing(false)
                progresbar?.visibility=View.GONE
            }
        })
    }
    /**
     * Metoda inicjalizująca Recycler Viewer dla umów
     * @author Rafał Pasternak
     * **/
    fun initRecyclerView() {
        val recycleAdapter= activity?.let {
            SellInvoiceItemsRecyclerAdapter(
                it,
                sellInvoice,this
            )
        }
        val recyclerView = binding!!.SIIRowsRV
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
    /**
     * Metoda nadpisująca metodę z interfejsu onItemClickListener
     * odsyła do wybranej przez użytkownika umowy
     * @author Rafał Pasternak
     * **/
    override fun onItemClick(position: Int) {
        }


    companion object {
        private const val TEXT = -1
        fun newInstance(text: Int?): SellInvoiceItemsFragment {
            val fragment = SellInvoiceItemsFragment()
            val args = Bundle()
            args.putString(TEXT.toString(), text.toString())
            fragment.arguments = args
            return fragment
        }
    }

}