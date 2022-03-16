package com.platform

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.platform.api.EmsApi
import com.platform.databinding.ActivityCostInvoiceBinding
import com.platform.databinding.ActivityCostInvoiceBinding.inflate
import com.platform.pojo.contracts.Contracts
import com.platform.pojo.costInvoice.CostInvoice
import com.platform.pojo.currencies.Currencies
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_cost_invoice.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.cos
import android.widget.ArrayAdapter
import com.platform.pojo.currencies.Result
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.platform.pojo.contractors.Contractors
import com.platform.pojo.costInvoice.Contractor
import com.platform.pojo.costInvoice.Currency
import com.platform.pojo.costInvoice.User
import com.platform.pojo.employees.Employees
import com.platform.ui.costInvoices.CostInvoicesFragment


@AndroidEntryPoint
class CostInvoiceActivity : AppCompatActivity(){
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    private lateinit var binding: ActivityCostInvoiceBinding
    private lateinit var foreignNumber : TextView
    private lateinit var issueDate: TextView
    private lateinit var contractor: Spinner
    private lateinit var user: Spinner
    private lateinit var paid: Switch
    private lateinit var paymentDate: TextView
    private lateinit var netTotal: TextView
    private lateinit var vatTotal: TextView
    private lateinit var grossTotal: TextView
    private lateinit var sum: TextView
    private lateinit var comment: TextView
    private lateinit var attachments: TextView
    private lateinit var currency: Spinner
    private var index=-1
    var costInvoice: CostInvoice = CostInvoice()
    var currencies: Currencies= Currencies()
    var employees: Employees= Employees()
    var contractors: Contractors= Contractors()
    val x : List<String> = listOf("Kontrahent", "bar", "baz")
    val y : List<String> = listOf("Waluta", "bar", "baz")
    val z : List<String> = listOf("Wprowadził", "bar", "baz")
    val listOfCurrenciesName: MutableList<String> = y.toMutableList()
    val listOfContractorsName: MutableList<String> = x.toMutableList()
    val listOfEmployeesName: MutableList<String> = z.toMutableList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        foreignNumber=binding.CINumberTV
        issueDate=binding.CIIssueDateTV
        contractor=binding.CIContractorS
        user=binding.CIUserS
        paid=binding.CIPaidS
        paymentDate=binding.CIPaymentDateTV
        netTotal=binding.CINetTotalTV
        vatTotal=binding.CIVatTotalTV
        grossTotal=binding.CIGrossTotalTV
        sum=binding.CISumTV
        comment=binding.CICommentsTV
        attachments=binding.CIAttachmentsTV
        currency=binding.CICurrencyS

        index = intent.getIntExtra("index",-1)
        val issueDateField="IssueDate"
        val paymentDateField="PaymentDate"
        var dateField=issueDateField
        val calendar = Calendar.getInstance()
        /**
         * Metoda pozwalająca wybrać datę
         *@author Rafał Pasternak
         **/
        val datePicker = DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            if(dateField==paymentDateField)
                updatePaymentDate(calendar)
            else
                updateIssueDate(calendar)
        }
        issueDate.setOnClickListener{
            DatePickerDialog(this,datePicker,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        dateField=issueDateField
        }
        paymentDate.setOnClickListener{
            DatePickerDialog(this,datePicker,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
            dateField=paymentDateField
        }
        setSupportActionBar(toolbar_typical)
        toolbar_typical.setTitle(R.string.Cost_Invoice)
        //wybór waluty,kontrachenta,pracownika inicjalizacja
        getCurrencies()
        getEmployees()
        getContractors()
        if(index!=-1)
            getCostInvoice()
        currency.adapter=ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listOfCurrenciesName)
        currency.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                costInvoice.currency.id=currencies.results[position].id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        contractor.adapter=ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listOfContractorsName)
        contractor.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                costInvoice.contractor.id=contractors.results[position].id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        user.adapter=ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listOfEmployeesName)
        user.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                costInvoice.user.id=employees.results[position].id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        attachments.setOnClickListener(){
            var index=costInvoice.id
            openFragmentRestorePassword(index)
        }


    }
    /**
     * Pobranie informacji faktury kosztowej
     * @author Rafał Pasternak
     **/
    private fun getCostInvoice() {
            val call = emsApi.getCostInvoiceById(
                index
            )
            call.enqueue(object : Callback<CostInvoice> {
                override fun onResponse(call: Call<CostInvoice>, response: Response<CostInvoice>) =
                    if (response.isSuccessful) {
                        costInvoice = response.body()!!
                        if(costInvoice.contractor==null)
                            costInvoice.contractor= Contractor()
                        if(costInvoice.currency==null)
                            costInvoice.currency= Currency()
                        if(costInvoice.user==null)
                            costInvoice.user= User()
                        applyData()
                    } else {
                        val errorUtil = ee.parseError(response)
                        if (errorUtil != null) {
                            openDialog(errorUtil.message)
                        } else
                            openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                    }

                override fun onFailure(call: Call<CostInvoice>, t: Throwable) {
                    Log.e("APP", t.localizedMessage)
                    Log.e("APP", t.message.toString())
                }
            })
        }
    /**
     *Przepisanie informacji do pól
     * @author Rafał Pasternak
     **/
    private fun applyData() {
        foreignNumber.text = costInvoice.foreignNumber
        if(costInvoice.issueDate!=null)
            issueDate.text = convertLongToTime(costInvoice.issueDate)
        contractor.setSelection(listOfContractorsName.indexOf(costInvoice?.contractor?.name?.toString()))
        user.setSelection(listOfEmployeesName.indexOf(costInvoice?.user?.name?.toString()))
        paid.isChecked=costInvoice?.paid
        if(costInvoice.paymentDate!=null)
            paymentDate.text = convertLongToTime(costInvoice.paymentTerm)
        netTotal.text = costInvoice?.netTotal?.toString()
        vatTotal.text = costInvoice?.vatTotal?.toString()
        grossTotal.text=costInvoice?.grossTotal?.toString()
        sum.text=costInvoice?.sum?.toString()
        comment.text=costInvoice?.comments?.toString()
        currency.setSelection(listOfCurrenciesName.indexOf(costInvoice?.currency?.name?.toString()));

    }
    private fun PackData(){
        costInvoice.foreignNumber = foreignNumber?.text?.toString()
        if(issueDate.text!=null)
        costInvoice.issueDate = convertStingToLong(issueDate.text.toString())
        costInvoice?.paid=paid.isChecked
        if(costInvoice.paymentDate!=null)
            costInvoice.paymentDate = convertStingToLong(paymentDate.text.toString())
        costInvoice?.netTotal=netTotal.text?.toString()?.toDouble()
        costInvoice?.vatTotal=vatTotal.text?.toString()?.toDouble()
         costInvoice?.grossTotal = grossTotal.text?.toString()?.toDouble()
        costInvoice?.comments= comment.text?.toString()
        updateCostInvocie()

    }
    /**
     * Aktualizacja istniejącego rekordu
     * @author Rafał Pasternak
     **/
    private fun updateCostInvocie(){
        val call = emsApi.UpdateCostInvoice(
            index,costInvoice
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    Toast.makeText(applicationContext,"Zapisano",Toast.LENGTH_SHORT).show()
                    getCostInvoice()
                } else {

                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                openDialog(resources.getString(R.string.connectiontimeout))
                t.printStackTrace()
            }
        })
    }
    /**
     * Pobranie możliwych walut
     * @author Rafał Pasternak
     **/
    private fun getCurrencies() {
        val call = emsApi.getCurrencies()
        call.enqueue(object : Callback<Currencies> {
            override fun onResponse(call: Call<Currencies>, response: Response<Currencies>) =
                if (response.isSuccessful) {
                    currencies = response.body()!!
                    var i=0;
                    listOfCurrenciesName.clear()
                    while(i!=currencies.results.size){
                        listOfCurrenciesName.add(currencies.results[i].name.toString())
                        i++
                    }
                } else {
                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<Currencies>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
            }
        })
    }
    private fun getContractors() {
        val call = emsApi.getContractors()
        call.enqueue(object : Callback<Contractors> {
            override fun onResponse(call: Call<Contractors>, response: Response<Contractors>) =
                if (response.isSuccessful) {
                    contractors = response.body()!!
                    var i=0;
                    listOfContractorsName.clear()
                    while(i!=currencies.results.size){
                        listOfContractorsName.add(contractors.results[i].name)
                        i++
                    }
                } else {
                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<Contractors>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
            }
        })
    }
    private fun getEmployees() {
        val call = emsApi.getEmployees()
        call.enqueue(object : Callback<Employees> {
            override fun onResponse(call: Call<Employees>, response: Response<Employees>) =
                if (response.isSuccessful) {
                    employees = response.body()!!
                    var i=0;
                    listOfEmployeesName.clear()
                    while(i!=employees.results.size){
                        listOfEmployeesName.add(employees.results[i].fullname)
                        i++
                    }
                } else {
                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<Employees>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.typical_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if(id==R.id.action_save){
            PackData()
            //Toast.makeText(this,"Wszystkie",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Funkcja aktualizująca datę wystawienia
     * @author Rafał Pasternak
     **/
    fun updateIssueDate(calendar: Calendar){
        val dateFormat ="yyyy.MM.dd"
        val sdf=SimpleDateFormat(dateFormat,Locale.GERMANY)
        issueDate.setText(sdf.format(calendar.time))
    }
    /**
     * Funkcja aktualizująca datę płatności
     * @author Rafał Pasternak
     **/
    fun updatePaymentDate(calendar: Calendar){
        val dateFormat ="yyyy.MM.dd"
        val sdf=SimpleDateFormat(dateFormat,Locale.GERMANY)
        paymentDate.setText(sdf.format(calendar.time))
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
        val calendar=Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE,1)
        val utilDate= calendar.time as java.util.Date
        date= java.sql.Date(utilDate.getTime() )
        val format = SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }
    @SuppressLint("SimpleDateFormat")
    fun convertStingToLong(time: String): Long {
        val format = SimpleDateFormat("yyyy.MM.dd")
        val date = format.parse(time)
        val milis=date.time
        return milis
    }
    fun openFragmentRestorePassword(text: Int?) {
        val fragment = com.platform.Myfragments.attachments.AttachmentsFragment.newInstance(text)
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_right,
            R.anim.enter_from_right,
            R.anim.exit_to_right
        )
        transaction.addToBackStack(null)
        transaction.add(R.id.fragment_container, fragment, "RESTORE_PASSWORD_FRAGMENT").commit()
    }
}