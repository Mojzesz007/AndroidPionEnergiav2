package com.platform

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.platform.api.EmsApi
import com.platform.databinding.ActivityNavigationDrawerBinding
import com.platform.pojo.UserProfile
import com.platform.utils.ErrorUtil
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class NavigationDrawerActivity : AppCompatActivity() {
    @Inject
    lateinit var emsApi: EmsApi

    @Inject
    lateinit var ee : ErrorUtil

    private var mAppBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityNavigationDrawerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationDrawerBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setSupportActionBar(binding!!.appBarNavigationDrawer.toolbar)
        val drawer = binding!!.drawerLayout
        val navigationView = binding!!.navView
        val navheaderView = navigationView.inflateHeaderView(R.layout.nav_header_navigation_drawer)
        val username = navheaderView.findViewById<TextView>(R.id.ND_fullName_TV)

        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_contractors, R.id.nav_sell_invoices, R.id.nav_cost_invoices
        ) //lista fragmentow aktywnych w menu
            .setOpenableLayout(drawer)
            .build()
        val navController =
            Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        NavigationUI.setupWithNavController(navigationView, navController)
        //ClipData.Item item= R.id.action_settings;
        //uzupełnianie danymi
        authentication(username)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer)
        return (NavigationUI.navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }

    var responseCode: String =""
    /**
     * Metoda pobierająca obecnego użytkownika
     * @author Rafał Pasternak
     **/
    private fun authentication(username: TextView){
        val call = emsApi.getEntityByLogin(
        )
        call.enqueue(object : Callback<UserProfile> {
            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {

                if (response.isSuccessful) {
                    //uzupelnianie danych na widoku
                    val user = response.body()?.fullname
                    username.text = user

                } else {

                    val errorUtil = ee.parseError(response)
                    if (errorUtil != null) {
                        openDialog(errorUtil.message)

                    } else
                        openDialog("${resources.getString(R.string.FailedToConnect)} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                Log.e("APP", t.localizedMessage)
                Log.e("APP", t.message.toString())
                responseCode = t.message.toString()
                t.printStackTrace()
            }
        })
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

}