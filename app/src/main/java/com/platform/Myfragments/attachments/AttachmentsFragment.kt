package com.platform.Myfragments.attachments

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
import com.platform.adapters.CostInvoicesAttachmentsAdapter
import com.platform.adapters.CostInvoicesRecyclerAdapter
import com.platform.api.EmsApi
import com.platform.databinding.FragmentAttachmentsBinding
import com.platform.pojo.costInvoice.attachments.Attachments
import com.platform.pojo.costInvoices.CostInvoices
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.SimpleDateFormat
import javax.inject.Inject
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import com.platform.*


@AndroidEntryPoint
class AttachmentsFragment : Fragment(), CostInvoicesAttachmentsAdapter.OnItemClickListener {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil
    var index: Int =-1
    var attachments: Attachments=Attachments()

    private lateinit var attachmentsViewModel: AttachmentsViewModel
    lateinit var progresbar : ProgressBar
    lateinit var nestedScrollView : NestedScrollView
    lateinit var swipeContainer: SwipeRefreshLayout


    private var binding: FragmentAttachmentsBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        attachmentsViewModel = ViewModelProvider(this).get(
           AttachmentsViewModel::class.java
        )
        binding = FragmentAttachmentsBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        attachmentsViewModel!!.text.observe(viewLifecycleOwner, Observer { })
        progresbar= binding!!.AProgresBarPB
        nestedScrollView= binding!!.ANestedScrollViewNS

        /**
         *Funkcja króra przy pociągnięciu w dół odświeża dane
         * @author Rafał Pasternak
         **/
        swipeContainer= binding!!.ASwipeRefreshSR
        swipeContainer.setOnRefreshListener {
            attachments.attachments.clear()
            getAttachmentAttachments()
        }
        return root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        if (arguments != null) {
            index = requireArguments().getString(TEXT.toString()).toString().toInt()
            println(index) }
        getAttachmentAttachments()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    var responseCode: String =""



    /**
     * Metoda do wyświetlenia komunikatu użytkownikowi
     * @author Rafał Pasternak
     **/

    fun openDialog(message: String) {

    }
    private fun getAttachmentAttachments() {
        val call = emsApi.getCostInvoiceAttachments(
            index
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) =
                if (response.isSuccessful) {
                    var rawJsonString:String? = response.body()?.string()
                    rawJsonString="{\"Attachments\":"+rawJsonString+"}"
                    attachments= Gson().fromJson(rawJsonString, Attachments::class.java)
                    initRecyclerView()
                    swipeContainer.setRefreshing(false)
                } else {
                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
            }
        })
    }
    /**
     * Metoda inicjalizująca Recycler Viewer dla umów
     * @author Rafał Pasternak
     * **/
    fun initRecyclerView() {
        val recycleAdapter= activity?.let {
            CostInvoicesAttachmentsAdapter(
                it,
                attachments,this
            )
        }
        val recyclerView = binding!!.ARowsRV
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
    /**
     * Metoda nadpisująca metodę z interfejsu onItemClickListener
     * odsyła do wybranej przez użytkownika umowy
     * @author Rafał Pasternak
     * **/
    override fun onItemClick(position: Int,type :Int) {
        if(type==0)
            Toast.makeText(activity, "Item $position Pobierz kiedyś", Toast.LENGTH_SHORT).show()
        else if(type==1) {
            removeAttachment(attachments.attachments[position].id)
            Toast.makeText(activity, "Item $position Usuń", Toast.LENGTH_SHORT).show()
        }
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

    companion object {
        private const val TEXT = -1
        fun newInstance(text: Int?): AttachmentsFragment {
            val fragment = AttachmentsFragment()
            val args = Bundle()
            args.putString(TEXT.toString(), text.toString())
            fragment.arguments = args
            return fragment
        }
    }
    fun removeAttachment(position: Int){

        val call = emsApi.removeAttachment(position)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) =
                if (response.isSuccessful) {
                   getAttachmentAttachments()

                } else {
                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
            }
        })
    }




}