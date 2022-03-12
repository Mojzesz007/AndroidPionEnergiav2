package com.platform

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.platform.api.EmsApi
import com.platform.databinding.ActivityCostInvoiceBinding
import com.platform.databinding.ActivityCostInvoiceBinding.inflate
import com.platform.databinding.ActivityLoginBinding

import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CostInvoiceActivity : AppCompatActivity() {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    private lateinit var binding: ActivityCostInvoiceBinding
    private lateinit var foreignNumber : TextView
    private lateinit var issueDate: TextView
    private lateinit var contractor: TextView
    private lateinit var user: TextView
    private lateinit var paid: TextView
    private lateinit var paymentDate: TextView
    private lateinit var netTotal: TextView
    private lateinit var vatTotal: TextView
    private lateinit var grossTotal: TextView
    private lateinit var sum: TextView
    private lateinit var comment: TextView
    private lateinit var attachments: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        foreignNumber=binding.CINumberTV
        issueDate=binding.CIIssueDateTV
        contractor=binding.CIContractorTV
        user=binding.CIUserTV
        paid=binding.CIPaidS
        paymentDate=binding.CIPaymentDateTV
        netTotal=binding.CINetTotalTV
        vatTotal=binding.CIVatTotalTV
        grossTotal=binding.CIGrossTotalTV
        sum=binding.CISumTV
        comment=binding.CICommentsTV
        attachments=binding.CIAttachmentsTV
        val calendar = Calendar.getInstance()
        /**
         * Metoda pozwalająca wybrać datę
         *@author Rafał Pasternak
         **/
        val datePicker = DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
        }
        issueDate.setOnClickListener{
            DatePickerDialog(this,datePicker,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
            updateIssueDate(calendar)
        }
        paymentDate.setOnClickListener{
            DatePickerDialog(this,datePicker,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
            updatePaymentDate(calendar)
        }

    }

    /**
     * Funkcja aktualizująca datę wystawienia
     * @author Rafał Pasternak
     **/
    fun updateIssueDate(calendar: Calendar){
        val dateFormat ="dd-MM-yyyy"
        val sdf=SimpleDateFormat(dateFormat,Locale.GERMANY)
        issueDate.setText(sdf.format(calendar.time))
    }
    /**
     * Funkcja aktualizująca datę płatności
     * @author Rafał Pasternak
     **/
    fun updatePaymentDate(calendar: Calendar){
        val dateFormat ="dd-MM-yyyy"
        val sdf=SimpleDateFormat(dateFormat,Locale.GERMANY)
        paymentDate.setText(sdf.format(calendar.time))
    }
}