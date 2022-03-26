package com.platform

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.platform.api.EmsApi
import com.platform.databinding.ActivitySellInvoiceBinding
import com.platform.databinding.ActivitySolarContractBinding
import com.platform.pojo.sellInvoices.sellInvoice.SellInvoice
import com.platform.pojo.solarContracts.solarContract.SolarContract
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
class SolarContractActivity : AppCompatActivity() {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    private lateinit var numer: TextView
    private lateinit var data: TextView
    private lateinit var recipient: TextView
    private lateinit var contactPerson: TextView
    private lateinit var netTotal: TextView
    private lateinit var grossTotal: TextView
    private lateinit var module: TextView
    private lateinit var attachments: TextView
    private lateinit var binding: ActivitySolarContractBinding
    private var index: Int =-1;
    var solarContract: SolarContract= SolarContract()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solar_contract)
        binding = ActivitySolarContractBinding.inflate(layoutInflater)
        setContentView(binding.root)
        index = intent.getIntExtra("index",-1)
        numer=binding.SCNumberTV
        data=binding.SCDateTV
        recipient=binding.SCRecipientTV
        contactPerson=binding.SCContactPersonTV
        netTotal=binding.SCNetTotalTV
        grossTotal=binding.SCGrossTotalTV
        module=binding.SCModuleTV
        attachments=binding.SCAttachmentsTV

        attachments.setOnClickListener(){
            openFragmentAttachmentsViewOnly(index,"com.platform.solarContract.model.SolarContract","solarContract")
        }

        getSolarContractByID()
    }
    /**
     * Metoda otwiera fragment z Załącznikami
     * @author Rafał Pasternak
     **/
    fun openFragmentAttachmentsViewOnly(text: Int?, model: String,section:String?) {
        val fragment = com.platform.Myfragments.attachments.viewOnlyAttachments.ViewOnlyAttachmentsFragment.newInstance(text,model,section)
        val fragmentManager = supportFragmentManager

        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_right,
            R.anim.enter_from_right,
            R.anim.exit_to_right
        )
        transaction.addToBackStack(null)
        transaction.replace(R.id.fragment_container,fragment).commit()

    }


    private fun getSolarContractByID() {
        val call = emsApi.getSolarContractById(
            index
        )
        call.enqueue(object : Callback<SolarContract> {
            override fun onResponse(call: Call<SolarContract>, response: Response<SolarContract>) {
                if (response.isSuccessful) {
                    solarContract= response.body()!!
                    importData()
                }
                else {
                    openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SolarContract>, t: Throwable) {
                openDialog(resources.getString(R.string.connectiontimeout))
            }
        })
    }

    private fun importData() {
        numer.text=solarContract?.number?.toString()
        if(solarContract?.date!=null)
            data.text=convertLongToTime(solarContract?.date)
        recipient.text=solarContract?.recipient?.shortName?.toString()
        contactPerson.text=solarContract?.contact?.name+" "+solarContract?.contact?.surname
        netTotal.text=solarContract?.netValue?.toString()
        grossTotal.text=solarContract?.grossValue?.toString()
        module.text=solarContract?.module?.name?.toString()
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


}