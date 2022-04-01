package com.platform.Myfragments.attachments

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
import com.platform.CostInvoiceActivity
import com.platform.ImageActivity
import com.platform.R
import com.platform.adapters.CostInvoicesAttachmentsAdapter
import com.platform.api.EmsApi
import com.platform.databinding.FragmentAttachmentsBinding
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
class AttachmentsFragment : Fragment(), CostInvoicesAttachmentsAdapter.OnItemClickListener {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil
    var index: Int =-1
    var modelPath: String =""
    lateinit var section :String
    var attachments: Attachments=Attachments()
    var downloadid: Long = 0
    private lateinit var attachmentsViewModel: AttachmentsViewModel
    lateinit var progresbar : ProgressBar
    lateinit var nestedScrollView : NestedScrollView
    lateinit var swipeContainer: SwipeRefreshLayout
    lateinit var addButton:FloatingActionButton

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
        addButton=binding!!.AAddButtonFAB

        /**
         *Funkcja króra przy pociągnięciu w dół odświeża dane
         * @author Rafał Pasternak
         **/
        swipeContainer= binding!!.ASwipeRefreshSR
        swipeContainer.setOnRefreshListener {
            attachments?.attachments?.clear()
            getAttachmentAttachments()
        }
        /**
         * Funkcja obsługująca dodawanie załączników
         *@author Rafał Pasternak
         **/
        addButton.setOnClickListener(){
            Toast.makeText(activity,"Dodawanie załącznika",Toast.LENGTH_SHORT).show()
            val addImageIntent = Intent(context, ImageActivity::class.java)
            addImageIntent.putExtra("index",index)
            startActivity(addImageIntent)
        }
        return root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        if (arguments != null) {
            index = requireArguments().getString(TEXT.toString()).toString().toInt()
            modelPath =requireArguments().getString(MODELPATH).toString()

            section =requireArguments().getString(SECTION).toString()
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
                    progresbar?.visibility=View.GONE
                } else {
                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                        progresbar?.visibility=View.GONE
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                    progresbar?.visibility=View.GONE
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
        if(type==0){
            downloadAttachment(position)
            Toast.makeText(activity, "Pobieranie załącznika", Toast.LENGTH_SHORT).show()
        }
        else if(type==1) {
            removeAttachment(attachments.attachments[position].id)
            Toast.makeText(activity, "Usuń", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TEXT = -1
        private const val MODELPATH = "ModelPath"
        private const val SECTION = "Section"

        fun newInstance(text: Int?,modelPath: String?,section:String?): AttachmentsFragment {
            val fragment = AttachmentsFragment()
            val args = Bundle()
            args.putString(TEXT.toString(), text.toString())
            args.putString(SECTION.toString(), section.toString())
            args.putString(MODELPATH, modelPath.toString())
            fragment.arguments = args
            return fragment
        }
    }
    /**
     *Metoda usuwająca załącznik
     * @return data w formacie ludzkim
     **/
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