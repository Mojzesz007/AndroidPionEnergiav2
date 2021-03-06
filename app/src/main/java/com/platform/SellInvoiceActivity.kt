package com.platform

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.platform.Myfragments.attachments.sellInvoiceItems.SellInvoiceItemsFragment.Companion.newInstance
import com.platform.api.EmsApi
import com.platform.databinding.ActivityContractBinding
import com.platform.databinding.ActivitySellInvoiceBinding
import com.platform.pojo.contracts.contract.Contract
import com.platform.pojo.sellInvoices.sellInvoice.SellInvoice
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
class SellInvoiceActivity : AppCompatActivity() {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    private lateinit var contractor: TextView
    private lateinit var recipient: TextView
    private lateinit var netTotal: TextView
    private lateinit var grossTotal: TextView
    private lateinit var issueDate: TextView
    private lateinit var paymentDate: TextView
    private lateinit var attachments: TextView
    private lateinit var items: TextView
    private var sellInvoice: SellInvoice=SellInvoice()
    private lateinit var binding: ActivitySellInvoiceBinding
    private var index: Int =-1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell_invoice)

        binding = ActivitySellInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        index = intent.getIntExtra("index",-1)

        contractor=binding.SIContractorTV
        recipient=binding.SIRecipientTV
        netTotal=binding.SINetTotalTV
        grossTotal=binding.SIGrossTotalTV
        issueDate=binding.SIIssueDateTV
        paymentDate=binding.SIPaymentDateTV
        attachments=binding.SIAttachmentsTV
        items=binding.SIItemsTV

        attachments.setOnClickListener(){
            var index=sellInvoice.id
            openFragmentAttachmentsViewOnly(index,"com.platform.finanse.model.SellInvoice","document")
        }
        items.setOnClickListener(){
            var index=sellInvoice.id
            openFragmentItems(index)
        }

        if(index!=-1)
            getSellInvoiceByID()
    }

    private fun getSellInvoiceByID() {
        val call = emsApi.getSellInvoiceById(
            index
        )
        call.enqueue(object : Callback<SellInvoice> {
            override fun onResponse(call: Call<SellInvoice>, response: Response<SellInvoice>) {
                if (response.isSuccessful) {
                    sellInvoice= response.body()!!
                    importData()
                }
                else {
                    openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SellInvoice>, t: Throwable) {
                openDialog(resources.getString(R.string.connectiontimeout))
            }
        })
    }

    private fun importData() {
        contractor.text=sellInvoice?.contractor?.shortName
        recipient.text=sellInvoice?.recipientContractor?.shortName
        netTotal.text=sellInvoice?.netTotal?.toString()
        grossTotal.text=sellInvoice?.grossTotal?.toString()
        if(sellInvoice?.issueDate!=null)
            issueDate.text=convertLongToTime(sellInvoice?.issueDate)
        if(sellInvoice.paymentTerm!=null)
            paymentDate.text=convertLongToTime(sellInvoice.paymentTerm)
    }


    /**
     * Metoda otwiera fragment z Za????cznikami
     * @author Rafa?? Pasternak
     **/
    fun openFragmentAttachmentsViewOnly(text: Int?, model: String, section:String?) {
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
    /**
     * Metoda otwiera fragment z Za????cznikami
     * @author Rafa?? Pasternak
     **/
    fun openFragmentItems(text: Int?) {
        val fragment = com.platform.Myfragments.attachments.sellInvoiceItems.SellInvoiceItemsFragment.newInstance(text)
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

    /**
     * Metoda do wy??wietlenia komunikatu u??ytkownikowi
     * @author Rafa?? Pasternak
     **/
    fun openDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.messageTitle)) //jako res string
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
            }
            .show()
    }
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