package com.platform.Myfragments.attachments.viewOnlyAttachments

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
import com.platform.R

import com.platform.adapters.ViewOnlyAttachmentsAdapter
import com.platform.api.EmsApi
import com.platform.databinding.FragmentAttachmentsBinding
import com.platform.databinding.FragmentViewOnlyAttachmentsBinding
import com.platform.pojo.costInvoice.attachments.Attachments
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
class  ViewOnlyAttachmentsFragment : Fragment(), ViewOnlyAttachmentsAdapter.OnItemClickListener {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil
    var index: Int =-1
    var modelPath: String =""
    var section: String=""
    var attachments: Attachments=Attachments()
    var downloadid: Long = 0
    private lateinit var viewOnlyattachmentsViewModel: ViewOnlyAttachmentsViewModel
    lateinit var progresbar : ProgressBar
    lateinit var nestedScrollView : NestedScrollView
    lateinit var swipeContainer: SwipeRefreshLayout

    private var binding: FragmentViewOnlyAttachmentsBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewOnlyattachmentsViewModel = ViewModelProvider(this).get(
           ViewOnlyAttachmentsViewModel::class.java
        )
        binding = FragmentViewOnlyAttachmentsBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        viewOnlyattachmentsViewModel!!.text.observe(viewLifecycleOwner, Observer { })
        progresbar= binding!!.VOAProgresBarPB
        nestedScrollView= binding!!.VOANestedScrollViewNS


        /**
         *Funkcja króra przy pociągnięciu w dół odświeża dane
         * @author Rafał Pasternak
         **/
        swipeContainer= binding!!.VOASwipeRefreshSR
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
            modelPath= requireArguments().getString(MODELPATH.toString()).toString()
            section= requireArguments().getString(SECTION.toString()).toString()
            println(index) }
        getAttachmentAttachments()
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
    private fun getAttachmentAttachments() {
        val call = emsApi.getAttachments(
            index,modelPath,section
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
                        swipeContainer.setRefreshing(false)
                        openDialog(errorUtil.message)
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                    swipeContainer.setRefreshing(false)
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
            ViewOnlyAttachmentsAdapter(
                it,
                attachments,this
            )
        }
        val recyclerView = binding!!.VOARowsRV
        recyclerView.adapter = recycleAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
    /**
     * Metoda nadpisująca metodę z interfejsu onItemClickListener
     * odsyła do wybranej przez użytkownika umowy
     * @author Rafał Pasternak
     * **/
    override fun onItemClick(position: Int) {
            downloadAttachment(position)
            Toast.makeText(activity, "Pobieranie załącznika", Toast.LENGTH_SHORT).show()
        }


    companion object {
        private const val TEXT = -1
        private const val MODELPATH = "ModelPath"
        private const val SECTION = "Section"
        fun newInstance(text: Int?, modelPath: String?,section :String?): ViewOnlyAttachmentsFragment {
            val fragment = ViewOnlyAttachmentsFragment()
            val args = Bundle()
            args.putString(TEXT.toString(), text.toString())
            args.putString(MODELPATH.toString(),modelPath.toString())
            args.putString(SECTION.toString(),section.toString())
            fragment.arguments = args
            return fragment
        }
    }

    /**
     *Metoda pobierająca załącznik
     * @return data w formacie ludzkim
     **/
    fun downloadAttachment(position: Int){
        val call: Call<ResponseBody> = emsApi.downloadAttachment(attachments.attachments[position].id)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file")
                    val fileName=attachments.attachments[position].filename.toString()
                    val path = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS
                    ).toString()
                    val pathWhereYouWantToSaveFile = path+"/"+fileName

                    val writtenToDisk: String = saveFile(response.body(),pathWhereYouWantToSaveFile)
                    Toast.makeText(activity, "Pobrano", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Nie udało się pobrać załącznika", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.e(TAG, "error")
            }
        })

    }
    /**
     *Metoda zapisująca załącznik do pamięci wewnętrznej urządzenia
     * @return data w formacie ludzkim
     **/
    fun saveFile(body: ResponseBody?, pathWhereYouWantToSaveFile: String):String{
        if (body==null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()
            val fos = FileOutputStream(pathWhereYouWantToSaveFile)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return pathWhereYouWantToSaveFile
        }catch (e:Exception){
            Log.e("saveFile",e.toString())
        }
        finally {
            input?.close()
        }
        return ""
    }
}