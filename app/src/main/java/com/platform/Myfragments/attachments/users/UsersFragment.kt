package com.platform.Myfragments.attachments.users

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.platform.Myfragments.attachments.users.UsersViewModel
import com.platform.R
import com.platform.adapters.ContractorsRecyclerAdapter
import com.platform.adapters.UsersRecyclerAdapter
import com.platform.api.EmsApi
import com.platform.databinding.FragmentUsersBinding
import com.platform.pojo.contractors.Contractors
import com.platform.pojo.employees.Employees
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class UsersFragment : Fragment(), UsersRecyclerAdapter.OnItemClickListener {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil
    lateinit var sendBackText:String
    private lateinit var usersViewModel: UsersViewModel
    lateinit var progresbar : ProgressBar
    lateinit var nestedScrollView : NestedScrollView
    lateinit var swipeContainer: SwipeRefreshLayout
    private var mListener: UsersFragment.OnFragmentInteractionListener2? = null
   private var name : String? = null
    private var surname: String? = null
    private var binding: FragmentUsersBinding? = null
    var start=0 as Integer
    var max=15 as Integer
    var employees: Employees= Employees()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        usersViewModel = ViewModelProvider(this).get(
            UsersViewModel::class.java
        )
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        usersViewModel!!.text.observe(viewLifecycleOwner, Observer { })
        progresbar= binding!!.ConProgresBarPB
        nestedScrollView= binding!!.ConNestedScrollViewNS
        getEmployees()
        /**
         * Metoda obsługująca paginację danych
         * @author Rafał Pasternak
         **/
        nestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(scrollY==nestedScrollView.getChildAt(0).measuredHeight-nestedScrollView.measuredHeight&&start<=employees.totalCount) {
                start = max;
                progresbar!!.visibility=View.VISIBLE
                var step =max.toInt()+10
                max= step as Integer
                getEmployees()
            }
        })
        /**
         *Funkcja króra przy pociągnięciu w dół odświeża dane
         * @author Rafał Pasternak
         **/
        swipeContainer= binding!!.ConSwipeRefreshSR
        swipeContainer.setOnRefreshListener {
            start=0 as Integer
            max=15 as Integer
            employees.results.clear()
                getEmployees()
        }
        binding!!.CINameTV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
               name= binding!!.CINameTV.text?.toString()
                getEmployees()
            }

        })
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
     * Metoda pobierająca użytkowiników
     * @author Rafał Pasternak
     * **/
    private fun getEmployees() {
        if(!name.isNullOrEmpty()||!surname.isNullOrEmpty()){
            start=0 as Integer
            max=15 as Integer
        }
        if(start.equals(0)) {
            employees.results?.clear()
        }
        val call = emsApi.getEmployees(
            max,start,name
        )
        call.enqueue(object : Callback<Employees> {
            override fun onResponse(call: Call<Employees>, response: Response<Employees>) =
                if (response.isSuccessful) {
                    employees = response.body()!!
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
                        progresbar?.visibility=View.GONE
                    swipeContainer.setRefreshing(false)
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<Employees>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
            }
        })
    }


    /**
     * Metoda do wyświetlenia komunikatu użytkownikowi
     * @author Rafał Pasternak
     **/
    fun openDialog(message: String) {
        /*activity?.let {
            MaterialAlertDialogBuilder(it.applicationContext)
                .setTitle(resources.getString(R.string.messageTitle)) //jako res string
                .setMessage(message)
                .setPositiveButton("OK") { _, _ ->
                }
                .show()
        }*/
    }
    /**
     * Metoda inicjalizująca Recycler Viewer dla umów
     * @author Rafał Pasternak
     * **/
    fun initRecyclerView() {
        val recycleAdapter= activity?.let {
            UsersRecyclerAdapter(
                it,
                employees,this
            )
        }
        val recyclerView = binding!!.ConRowsRV
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
    /**
     * Metoda nadpisująca metodę z interfejsu onItemClickListener
     * odsyła do wybranej przez użytkownika umowy
     * @author Rafał Pasternak
     * **/

    override fun onItemClick(position: Int) {
        sendBackText = employees.results[position].id.toString()
        val name =employees.results[position].name.toString()+" "+employees.results[position].surname.toString()
        sendBack(sendBackText,name)
    }
    interface OnFragmentInteractionListener2 {
        fun onFragmentInteraction2(sendBackText: String?,name: String?)
    }
    fun sendBack(sendBackText: String?,name: String?) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction2(sendBackText,name)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is UsersFragment.OnFragmentInteractionListener2) {
            context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
    companion object {
        private const val TEXT = "text"
        fun newInstance(text: String?): UsersFragment {
            val fragment = UsersFragment()
            val args = Bundle()
            args.putString(TEXT, text)
            fragment.arguments = args
            return fragment
        }
    }
}