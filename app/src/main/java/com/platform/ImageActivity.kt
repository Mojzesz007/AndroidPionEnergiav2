package com.platform


import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.platform.Myfragments.attachments.AttachmentsFragment
import com.platform.api.EmsApi
import com.platform.pojo.costInvoice.attachments.Attachments
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger
import javax.inject.Inject
import okhttp3.MultipartBody





@AndroidEntryPoint
class ImageActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var imageView: ImageView
    private lateinit var pickImage: Button
    private lateinit var takephoto: Button
    private lateinit var upload: Button

    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    private var fileUri: Uri? = null
    private var mediaPath: String? = null
    private var mImageFileLocation = ""
    private lateinit var pDialog: ProgressDialog
    private var postPath: String? = null
    private var index: Int = -1
    var parentEntityType: RequestBody = RequestBody.create(MultipartBody.FORM, "com.platform.finanse.model.CostInvoice")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_layout)
        index = intent.getIntExtra("index",-1)
        imageView = findViewById(R.id.preview) as ImageView
        pickImage = findViewById(R.id.pickImageFromGalery) as Button
        upload = findViewById(R.id.upload) as Button
        takephoto = findViewById(R.id.takePhoto) as Button
        pickImage.setOnClickListener(this)
        upload.setOnClickListener(this)
        takephoto.setOnClickListener(this)
        initDialog()
    }

    override fun onClick(v: View) {
        if(v.id==R.id.pickImageFromGalery){
            val galleryIntent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO)
        }
        if(v.id==R.id.takePhoto){
            captureImage()
        }
        if(v.id==R.id.upload){
            uploadFile()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Get the Image from data
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                    val cursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                    assert(cursor != null)
                    cursor!!.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    mediaPath = cursor.getString(columnIndex)
                    imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                    cursor.close()
                    postPath = mediaPath
                }
            } else if (requestCode == CAMERA_PIC_REQUEST) {
                if (Build.VERSION.SDK_INT > 21) {

                    Glide.with(this).load(mImageFileLocation).into(imageView)
                    postPath = mImageFileLocation
                } else {
                    Glide.with(this).load(fileUri).into(imageView)
                    postPath = fileUri!!.path
                }
            }
        } else if (resultCode != Activity.RESULT_CANCELED) {
            openDialog("Wystąpił bład spróbuj jeszcze raz","Error")
        }
    }

    protected fun initDialog() {

        pDialog = ProgressDialog(this)
        pDialog.setMessage("Przesyłanie pliku")
        pDialog.setCancelable(true)
    }


    protected fun showpDialog() {

        if (!pDialog.isShowing) pDialog.show()
    }

    protected fun hidepDialog() {

        if (pDialog.isShowing) pDialog.dismiss()
    }


    /**
     * Launching camera app to capture image
     */
    private fun captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            val callCameraApplicationIntent = Intent()
            callCameraApplicationIntent.action = MediaStore.ACTION_IMAGE_CAPTURE
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                Logger.getAnonymousLogger().info("Exception error in generating the file")
                e.printStackTrace()
            }
            val outputUri = photoFile?.let {
                FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    it
                )
            }
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            Logger.getAnonymousLogger().info("Calling the camera App by intent")
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            startActivityForResult(intent, CAMERA_PIC_REQUEST)
        }


    }

    @Throws(IOException::class)
    internal fun createImageFile(): File {
        Logger.getAnonymousLogger().info("Generating the image - method started")

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmSS").format(Date())
        val imageFileName = "IMAGE_" + timeStamp
        val storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app")
        Logger.getAnonymousLogger().info("Storage directory set")
        if (!storageDirectory.exists()) storageDirectory.mkdir()
        val image = File(storageDirectory, imageFileName + ".jpg")
        Logger.getAnonymousLogger().info("File name and path set")
        mImageFileLocation = image.absolutePath
        return image
    }


    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("file_uri", fileUri)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        fileUri = savedInstanceState.getParcelable("file_uri")
    }


    /**
     * Receiving activity result method will be called after closing the camera
     */

    /**
     * ------------ Helper Methods ----------------------
     */

    /**
     * Creating file uri to store image/video
     */
    fun getOutputMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }

    private fun uploadFile() {
        if (postPath == null || postPath == "") {
            openDialog("Wstaw zdjęcie","Error")
            return
        } else {
            showpDialog()
            val map = HashMap<String, RequestBody>()
            val file = File(postPath!!)
            val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
            map.put("file\"; filename=\"" + file.name + "\"", requestBody)
            val fileToUpload = MultipartBody.Part.createFormData("file", file.name, requestBody)
            val call = emsApi.addAttachment(
                index,
                parentEntityType,
                RequestBody.create(MultipartBody.FORM,"document"),
                false,
                RequestBody.create(MultipartBody.FORM,file.name),
                fileToUpload

            )
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) =
                    if (response.isSuccessful) {
                        hidepDialog()
                        openDialog("Przesłano :-)","Przesyłanie powiodło się")

                    } else {
                        val errorUtil = ee.parseError(response)
                        if (errorUtil != null) {
                            openDialog(errorUtil.message,"Error")
                            hidepDialog()
                        } else{
                            hidepDialog()
                            openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}","Error")
                        }
                    }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("APP", t.localizedMessage)
                    Log.e("APP", t.message.toString())
                }
            })
        }
    }

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_PICK_PHOTO = 2
        private val CAMERA_PIC_REQUEST = 1111

        private val TAG = ImageActivity::class.java.getSimpleName()

        private val CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100

        val MEDIA_TYPE_IMAGE = 1
        val IMAGE_DIRECTORY_NAME = "Android File Upload"

        /**
         * returning image / video
         */
        private fun getOutputMediaFile(type: Int): File? {

            val mediaStorageDir = File(
                Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME)
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(TAG, "Oops! Failed create "
                            + IMAGE_DIRECTORY_NAME + " directory")
                    return null
                }
            }
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(Date())
            val mediaFile: File
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = File(mediaStorageDir.path + File.separator
                        + "IMG_" + ".jpg")
            } else {
                return null
            }
            return mediaFile
        }
    }
    /**
     * Metoda do wyświetlenia komunikatu użytkownikowi
     * @author Rafał Pasternak
     **/
    fun openDialog(message: String,title:String?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title) //jako res string
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
            }
            .show()
    }
}
