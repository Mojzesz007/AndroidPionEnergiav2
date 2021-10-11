package com.platform

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.platform.RestorePasswordFragment.OnFragmentInteractionListener
import com.platform.api.EmsApi
import com.platform.data.CurrentUser
import com.platform.data.response.UserDataList
import com.platform.databinding.ActivityLoginBinding
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), OnFragmentInteractionListener {

    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    @Inject
    lateinit var encryptedPreferencesProvider: EncryptedPreferencesProvider

    private lateinit var binding: ActivityLoginBinding

    private lateinit var anim :ImageView

    private lateinit var currentUser: CurrentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        anim=binding.LALogoIV
        readCredentials()

        binding.LAForgotTV.setOnClickListener { t: View? ->
            openFragmentRestorePassword(binding.LAUsernameTI.text.toString())
        }
        binding.LALoginB.setOnClickListener { t: View? ->
            rotate()
            authentication()
        }
    }

    fun openFragmentRestorePassword(text: String?) {
        val fragment = RestorePasswordFragment.newInstance(text)
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

    override fun onFragmentInteraction(sendBackText: String?) {
        binding.LAUsernameTI.setText(sendBackText)
        onBackPressed()
    }

    fun saveCredentialsToEncryptedStorage() {
        if (binding.LARememberS.isChecked) {
            saveCredentials()
        } else {
            removeCredentials()
        }
    }

    private fun saveCredentials() {
        encryptedPreferencesProvider.saveToEncryptedStorage(
            "Username",
            binding.LAUsernameTI.text.toString()
        )
        encryptedPreferencesProvider.saveToEncryptedStorage(
            "Password",
            binding.LAPasswordTI.text.toString()
        )
    }

    private fun readCredentials() {
        val username=encryptedPreferencesProvider.readCredentials("Username")
            if (username != null) {
                binding.LAUsernameTI.setText(username)
                binding.LAPasswordTI.setText(encryptedPreferencesProvider.readCredentials("Password"))
                binding.LARememberS.isChecked = true
            }

    }

    private fun removeCredentials() {
        encryptedPreferencesProvider.saveToEncryptedStorage("Username", null)
        encryptedPreferencesProvider.saveToEncryptedStorage("Password", null)
    }

    var responseCode: String =""
    private fun authentication(){
        val call = emsApi.login(
            binding.LAUsernameTI.text.toString(),
            binding.LAPasswordTI.text.toString()
        )
        //val call: Call<ResponseBody> = Ems.getCompanyDetails()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    doSampleRequests()

                } else {

                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)
                        anim.clearAnimation()
                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                    anim.clearAnimation()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                responseCode = t.message.toString()
                openDialog(resources.getString(R.string.connectiontimeout))
                t.printStackTrace()
                anim.clearAnimation()
            }
        })
    }

    fun doSampleRequests(){
        val getServiceOrders = emsApi.getServiceOrders()
        getServiceOrders.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                println("poprawne zalogowanie")//odsyłka do nowego Activity i pobrac uzytkownika
                saveCredentialsToEncryptedStorage()
                println(response.message())


                anim.clearAnimation()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                println("blad xyz")
                t.printStackTrace()
                anim.clearAnimation()
            }
        })

        /*val getServiceOrders2 = emsApi.getServiceOrders()
        getServiceOrders2.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val errorCode = response.toString()
                println(errorCode)
                anim.clearAnimation()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                println("zepsulo sie tu 2")
                t.printStackTrace()
                anim.clearAnimation()
            }
        })*/
        val getUser = emsApi.getUser()
        getUser.enqueue(object : Callback<UserDataList> {
            override fun onResponse(call: Call<UserDataList>, response: Response<UserDataList>) {
                val errorCode = response.toString()
                println(errorCode)
                println("uszesz")
                println(response.message())
                println("rezultat: " + (response.body()?.results?.get(0)))
                //getUser.request();
                //anim?.clearAnimation();

                for (i in 0 until (response.body()!!.results.size)) {
                    println("login wg rb: " + response.body()!!.results[i].login);
                    println("login wg bindingu: " + binding.LAUsernameTI.text.toString())
                    if (binding.LAUsernameTI.text.toString()
                            .equals(response.body()!!.results[i].login)
                    ) {
                        currentUser = CurrentUser(
                            response.body()!!.results[i].login,
                            response.body()!!.results[i].name,
                            response.body()!!.results[i].surname,
                            response.body()!!.results[i].role
                        )
                    }
                }
                loginActivity()
            }

            override fun onFailure(call: Call<UserDataList>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                println("zepsulo sie przy uszesze")
                t.printStackTrace()
                //anim?.clearAnimation();
            }
        })
    }

    fun rotate() {
        val animation =AnimationUtils.loadAnimation(this, R.anim.loadinganim)
        if (anim != null) {
            anim.startAnimation(animation)
        }
        //anim?.clearAnimation();
    }
    fun openDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.messageTitle)) //jako res string
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
            }
            .show()
    }

    fun loginActivity() {
        val loginIntent = Intent(this, DrawerActivity::class.java)
        loginIntent.putExtra("currentUser", currentUser)
        startActivity(loginIntent)



    }
}