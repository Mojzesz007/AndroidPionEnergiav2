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
import com.platform.databinding.ActivityLoginBinding
import com.platform.pojo.UserProfile
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


/**
 * @author Rafał Pasternak
 * klasa obsługująca funkcjonalność logowania się
 * do systemu EMS
 * **/

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


    private lateinit var userProfile: UserProfile

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
    /**
     * funkcja otwierajaca fragment przywracania hasła
     * @author Rafał Pasternak
     **/
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
    /**
     * funkcja przesyłająca dane pomiędzy stroną logowania a stroną odzyskiwania hasła
     * @author Rafał Pasternak
     **/
    override fun onFragmentInteraction(sendBackText: String?) {
        binding.LAUsernameTI.setText(sendBackText)
        onBackPressed()
    }

    /**
     * funkcja zarządzająca danymi zapisanymi na urządzeniu
     * @author Rafał Pasternak
     **/
    fun saveCredentialsToEncryptedStorage() {
        if (binding.LARememberS.isChecked) {
            saveCredentials()
        } else {
            removeCredentials()
        }
    }
    /**
     * funkcja zapisująca zaszyfrowane dane logowania na urządzeniu
     * @author Rafał Pasternak
     **/
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
    /**
     * funkcja odczytująca zaszyfrowane dane logowania na urządzeniu
     * @author Rafał Pasternak
     **/
    private fun readCredentials() {
        val username=encryptedPreferencesProvider.readCredentials("Username")
            if (username != null) {
                binding.LAUsernameTI.setText(username)
                binding.LAPasswordTI.setText(encryptedPreferencesProvider.readCredentials("Password"))
                binding.LARememberS.isChecked = true
            }

    }
    /**
     * funkcja usuwająca zaszyfrowane dane logowania na urządzeniu
     * @author Rafał Pasternak
     **/
    private fun removeCredentials() {
        encryptedPreferencesProvider.saveToEncryptedStorage("Username", null)
        encryptedPreferencesProvider.saveToEncryptedStorage("Password", null)
    }

    var responseCode: String =""
    /**
     * funkcja logująca użytkownika do systemu
     * obsługująca błędne logowanie
     * @author Rafał Pasternak
     **/
    private fun authentication(){
        val call = emsApi.login(
            binding.LAUsernameTI.text.toString(),
            binding.LAPasswordTI.text.toString()
        )
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
    /**
     * funkcja wywoływana w przypadku nawiązania połączenia ze stroną EMS
     * @author Rafał Pasternak
     **/
    fun doSampleRequests(){
        val getServiceOrders = emsApi.getServiceOrders()
        getServiceOrders.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                saveCredentialsToEncryptedStorage()
                loginActivity()
                anim.clearAnimation()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                t.printStackTrace()
                anim.clearAnimation()
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
     *Metoda odsyłająca uzytkownika do głównego menu
     * @author Rafał Pasternak
     **/
    fun loginActivity() {
        val loginIntent = Intent(this, NavigationDrawerActivity::class.java)
        startActivity(loginIntent)
    }
}