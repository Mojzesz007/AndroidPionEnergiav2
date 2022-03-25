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
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.app.Fragment;
import com.platform.Myfragments.attachments.contractors.ContractorsFragment
import com.platform.Myfragments.attachments.users.UsersFragment
//import androidx.fragment.app.Fragment
import com.platform.pojo.contractors.Contractors
import com.platform.pojo.costInvoice.Contractor
import com.platform.pojo.costInvoice.Currency
import com.platform.pojo.costInvoice.User
import com.platform.pojo.employees.Employees
import com.platform.ui.costInvoices.CostInvoicesFragment.Companion.newInstance


@AndroidEntryPoint
class CostInvoiceActivity : AppCompatActivity(),
    ContractorsFragment.OnFragmentInteractionListener,
    UsersFragment.OnFragmentInteractionListener2{
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    private lateinit var binding: ActivityCostInvoiceBinding
    private lateinit var foreignNumber : TextView
    private lateinit var issueDate: TextView
    private lateinit var contractor: TextView
    private lateinit var user: TextView
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

    var contractors: Contractors= Contractors()
    val y : List<String> = listOf("PLN", "error", "error")
    val listOfCurrenciesName: MutableList<String> = y.toMutableList()

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
        currency=binding.CICurrencyS

        index = intent.getIntExtra("index",-1)
        val issueDateField="IssueDate"
        val paymentDateField="PaymentDate"
        var dateField=issueDateField
        val calendar = Calendar.getInstance()

        //wybór waluty,kontrachenta,pracownika inicjalizacja
        getCurrencies()
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

        currency.adapter=ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listOfCurrenciesName)
        currency.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                costInvoice?.currency?.id=currencies?.results[position]?.id
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        contractor.setOnClickListener(){
            var index=costInvoice.id
            openFragmentSelectContractor()
        }
        user.setOnClickListener(){
            var index=costInvoice.id
            openFragmentSelectCurrency()
            }

        attachments.setOnClickListener(){
            var index=costInvoice.id
            openFragmentAttachments(index,"com.platform.finanse.model.CostInvoice")
        }
        if(index!=-1)
            getCostInvoice()
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
        contractor.text=costInvoice.contractor.shortName
        user.setText(costInvoice?.user?.name?.toString()+" "+costInvoice.user.surname.toString())
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
        costInvoice.draft=false
        if(costInvoice.paymentDate!=null)
            costInvoice.paymentDate = convertStingToLong(paymentDate.text.toString())
        if(netTotal.text.isNotEmpty())
            costInvoice?.netTotal=netTotal?.text?.toString()?.toDouble()
        if(vatTotal.text.isNotEmpty())
        costInvoice?.vatTotal=vatTotal?.text?.toString()?.toDouble()
        if(grossTotal.text.isNotEmpty())
         costInvoice?.grossTotal = grossTotal?.text?.toString()?.toDouble()
        costInvoice?.comments= comment?.text?.toString()
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
                        if(errorUtil.message.isNotEmpty())
                            openDialog(errorUtil.message)
                        else
                            openDialog("Uzupełnij brakujące dane")
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.typical_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if(id==R.id.action_save){
            PackData()
        }
        if(id==R.id.action_delete){
            deleteCostInvocie()
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
    /**
     * Metoda otwiera fragment z Załącznikami
     * @author Rafał Pasternak
     **/
    fun openFragmentAttachments(text: Int?, modelPath: String?) {
        val fragment = com.platform.Myfragments.attachments.AttachmentsFragment.newInstance(text, modelPath)
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
     * Metoda otwiera fragment wyborem kontrachentów
     * @author Rafał Pasternak
     **/
    fun openFragmentSelectContractor() {
        val fragment = com.platform.Myfragments.attachments.contractors.ContractorsFragment!!.newInstance("")
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
    fun openFragmentSelectCurrency() {
        val fragment = com.platform.Myfragments.attachments.users.UsersFragment!!.newInstance("")
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

    override fun onBackPressed() {
        val fragment : Fragment? = fragmentManager.findFragmentById(R.id.fragment_container)
        if(fragment!=null)
        {
            val transaction = fragmentManager.beginTransaction()
            transaction.remove(fragment)
            transaction.commit()
        }
            else
                super.onBackPressed()
    }
    /**
     * Metoda usuwająca Faktórę
     * @author Rafał Pasternak
     **/
    private fun deleteCostInvocie(){
        val call = emsApi.deleateCostInvoice(
            index,
            "application/json, text/plain, */*",
            "gzip, deflate, br",
            "pl,en-US;q=0.7,en;q=0.3",
            "no-cache",
            "keep-alive",
            2,
            "application/json;charset=utf-8"
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    Toast.makeText(applicationContext,"Usunięto",Toast.LENGTH_SHORT).show()
                    val drawerIntent = Intent(applicationContext, NavigationDrawerActivity::class.java)
                    startActivity(drawerIntent)
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
    override fun onFragmentInteraction(sendBackText: String?, name: String?) {
       // binding.LAUsernameTI.setText(sendBackText)
        costInvoice.contractor.id=sendBackText.toString().toInt()
        contractor.text=name
        onBackPressed()
    }
    override fun onFragmentInteraction2(sendBackText: String?, name: String?) {
        // binding.LAUsernameTI.setText(sendBackText)
        costInvoice.user.id=sendBackText.toString().toInt()
        user.text=name
        onBackPressed()
    }
}