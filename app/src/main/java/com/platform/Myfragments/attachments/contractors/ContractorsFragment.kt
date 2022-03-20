package com.platform.Myfragments.attachments.contractors

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
import com.platform.R
import com.platform.adapters.ContractorsRecyclerAdapter
import com.platform.api.EmsApi
import com.platform.databinding.FragmentContractorsBinding
import com.platform.pojo.contractors.Contractors
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class ContractorsFragment : Fragment(), ContractorsRecyclerAdapter.OnItemClickListener {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil
    lateinit var sendBackText:String
    private lateinit var contractorsViewModel: ContractorsViewModel
    lateinit var progresbar : ProgressBar
    lateinit var nestedScrollView : NestedScrollView
    lateinit var swipeContainer: SwipeRefreshLayout
    private var mListener: ContractorsFragment.OnFragmentInteractionListener? = null

    var contractors: Contractors = Contractors()
    private var binding: FragmentContractorsBinding? = null
    var start=0 as Integer
    var max=15 as Integer


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        contractorsViewModel = ViewModelProvider(this).get(
            ContractorsViewModel::class.java
        )
        binding = FragmentContractorsBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        contractorsViewModel!!.text.observe(viewLifecycleOwner, Observer { })
        progresbar= binding!!.ConProgresBarPB
        nestedScrollView= binding!!.ConNestedScrollViewNS
        getContractors(null)
        /**
         * Metoda obsługująca paginację danych
         * @author Rafał Pasternak
         **/
        nestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(scrollY==nestedScrollView.getChildAt(0).measuredHeight-nestedScrollView.measuredHeight&&start<=contractors.totalCount) {
                start = max;
                progresbar!!.visibility=View.VISIBLE
                var step =max.toInt()+10
                max= step as Integer
                getContractors(null)
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
            contractors.results.clear()
                getContractors(null)
        }
        binding!!.CIContractorTV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                getContractors(binding!!.CIContractorTV.text?.toString())
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
     * Metoda pobierająca Kontrahentów
     * @author Rafał Pasternak
     * **/
    private fun getContractors(name :String?){
        if(name!=null){
            start=0 as Integer
            max=15 as Integer
        }
        if(start.equals(0)) {
            contractors.results?.clear()
        }
        val call = emsApi.getContractors(
            max,start,name
        )
        call.enqueue(object : Callback<Contractors> {
            override fun onResponse(call: Call<Contractors>, response: Response<Contractors>) =
                if (response.isSuccessful) {
                    if(name==null) {
                        if (contractors.results == null)
                            contractors = response.body()!!
                        else if (!response.body()!!.results.isEmpty())
                            contractors.results.addAll(response.body()!!.results)
                    }else{
                        contractors.results?.clear()
                        contractors = response.body()!!
                    }

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
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }

            override fun onFailure(call: Call<Contractors>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                responseCode = t.message.toString()
                openDialog(resources.getString(R.string.connectiontimeout))
                t.printStackTrace()
                progresbar?.visibility=View.GONE
                swipeContainer.setRefreshing(false)
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
            ContractorsRecyclerAdapter(
                it,
                contractors,this
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
        sendBackText = contractors.results[position].id.toString()
        val name =contractors.results[position].name.toString()
        sendBack(sendBackText,name)
    }
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(sendBackText: String?,name: String?)
    }
    fun sendBack(sendBackText: String?,name: String?) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(sendBackText,name)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is ContractorsFragment.OnFragmentInteractionListener) {
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
        fun newInstance(text: String?): ContractorsFragment {
            val fragment = ContractorsFragment()
            val args = Bundle()
            args.putString(TEXT, text)
            fragment.arguments = args
            return fragment
        }
    }
}