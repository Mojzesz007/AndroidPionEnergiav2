package com.platform

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.platform.api.EmsApi
import com.platform.databinding.ActivityContractBinding
import com.platform.pojo.contracts.contract.Contract

import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ContractActivity : AppCompatActivity() {

    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    private lateinit var binding: ActivityContractBinding
    private var contract :Contract = Contract()

    lateinit var number :TextView
    lateinit var state :TextView
    lateinit var dateFrom :TextView
    lateinit var dateTo :TextView
    lateinit var supplier: TextView
    lateinit var recipient :TextView
    lateinit var title: TextView
    lateinit var subject :TextView

    private var index: Int =-1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contract)
        binding = ActivityContractBinding.inflate(layoutInflater)
        setContentView(binding.root)
        index = intent.getIntExtra("index",-1)
        number=binding.CTRNumberTV
        state=binding.CTRStateTV
        dateFrom=binding.CTRDateFromTV
        dateTo=binding.CTRDateToTV
        supplier=binding.CTRSupplierTV
        recipient=binding.CTRRecipientTV
        title=binding.CTRTitleTV
        subject=binding.CTRSubjectTV

        if(index!=-1)
            getContractByID(index)
    }

    private fun getContractByID(index: Int) {
        val call = emsApi.getContractById(
            index
        )
        call.enqueue(object : Callback<Contract> {
            override fun onResponse(call: Call<Contract>, response: Response<Contract>) {
                if (response.isSuccessful) {
                    contract= response.body()!!
                  importData()
                }
                else {
                    openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Contract>, t: Throwable) {
                openDialog(resources.getString(R.string.connectiontimeout))
            }
        })
    }

    private fun importData() {
        number?.text=contract?.number?.toString()
        state?.text=contract?.status?.name.toString()
        if(contract?.date!=null)
            dateFrom?.text=convertLongToTime(contract?.date)
        if(contract?.endDate!=null)
            dateTo?.text=convertLongToTime(contract?.endDate)
        supplier?.text=contract?.supplier?.shortName
        recipient?.text=contract?.recipient?.shortName
        title?.text=contract?.title?.toString()
        if(contract?.description!=null)
        subject?.text= contract?.description?.toString()?.let { cleanFromCKEditor(it) }
    }

    /**
     * Metoda do wyświetlenia komunikatu użytkownikowi
     * @author Rafał Pasternak
     **/
    fun openDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.messageTitle)) //jako res string
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
            }
            .show()
    }
    /**
     *Metoda konwertująca datę w formacie Long do formatu czytelnego dla użytkownika
     * @param time data w formacie long
     * @return data w formacie ludzkim
     **/
    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        var date = Date(time)
        val calendar= Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE,1)
        val utilDate= calendar.time as java.util.Date
        date= java.sql.Date(utilDate.getTime() )
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }
    /**
     *  Metoda czyści wyniki z pozostałości ckeditora
     * @param time data w formacie long
     * @return data w formacie ludzkim
     **/
    fun cleanFromCKEditor(text : String): String {
        var str =text
        str = str.replace("[<]*[a-z]*[>]".toRegex(),"")
        str =str.replace('/',' ')
        str =str.replace("[<][ ]".toRegex(),"")
        str =str.replace("[&][a-z]*[;]".toRegex(),"")
        return str
    }
}