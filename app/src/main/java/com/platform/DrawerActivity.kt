package com.platform

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.platform.Adapter.ExpandableCustomAdapter
import com.platform.Helper.FragmentNavigatorManager
import com.platform.Interface.NavigationManager
import com.platform.Model.ChildDataModel
import com.platform.Model.ParentDataModel
import com.platform.data.CurrentUser
import com.platform.databinding.DrawerActivityBinding
import java.util.*


class DrawerActivity : AppCompatActivity() {
    private var mDrawerLayout: DrawerLayout? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mActivityTitle: String? = null
    private lateinit var items: Array<String>
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var navigationManager: NavigationManager? = null
    private var childDataModel: ChildDataModel? = null
    private var parentDataModel: ParentDataModel? = null
    private var tv: TextView? = null
    private var binding: DrawerActivityBinding? = null
    private var popupMenu: PopupMenu? = null
    private var headerData: ArrayList<ParentDataModel>? = null
    private var childData: HashMap<ParentDataModel, ArrayList<ChildDataModel>?>? = null
    private var contacts: ArrayList<ChildDataModel>? = null
    private var service: ArrayList<ChildDataModel>? = null
    private lateinit var currentUser: CurrentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DrawerActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initItems()
        val i = intent
        currentUser = i.getSerializableExtra("currentUser") as CurrentUser

        mDrawerLayout = binding!!.drawerLayout
        mActivityTitle = title.toString()
        expandableListView = binding!!.navList
        navigationManager = FragmentNavigatorManager.getmInstance(this)
        val listHeaderView = layoutInflater.inflate(R.layout.nav_header, null, false)
        expandableListView!!.addHeaderView(listHeaderView)

        genData()
        addDrawersItem()
        setupDrawer()
        if (savedInstanceState == null) selectFirstItemAsDefault()

        menuOptions()
        println(currentUser.name)
        println("tu slepcze")
        setUserData(currentUser)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle!!.onConfigurationChanged(newConfig)
    }

    private fun selectFirstItemAsDefault() {
        if (navigationManager != null) {
            val firstItem = headerData!![0]
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.title = resources.getString(R.string.app_name)
        }
    }

    private fun setupDrawer() {
        mDrawerToggle =
            object : ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)
                    supportActionBar!!.title = resources.getString(R.string.app_name)
                    invalidateOptionsMenu()
                }

                override fun onDrawerClosed(drawerView: View) {
                    super.onDrawerClosed(drawerView)
                    supportActionBar!!.title = mActivityTitle
                    invalidateOptionsMenu()
                }
            }
        (mDrawerToggle as ActionBarDrawerToggle).setDrawerIndicatorEnabled(true)
        mDrawerLayout!!.addDrawerListener(mDrawerToggle as ActionBarDrawerToggle)
    }

    private fun addDrawersItem() {
        adapter = ExpandableCustomAdapter(this, headerData, childData)
        expandableListView!!.setAdapter(adapter)

        expandableListView!!.setOnChildClickListener { parent: ExpandableListView?, v: View?, groupPosition: Int, childPosition: Int, id: Long ->
            val selectedItem = (childData!![headerData!![groupPosition]] as List<*>?)
                ?.get(childPosition).toString()
            supportActionBar!!.setTitle(selectedItem)
            if (items[0].equals(headerData!![groupPosition])) navigationManager!!.showFragment(
                selectedItem
            ) else throw IllegalArgumentException("Not supported fragment")
            mDrawerLayout!!.closeDrawer(GravityCompat.START)
            false
        }
    }

    private fun genData() {
        parentDataModel = ParentDataModel(resources.getString(R.string.contact), R.drawable.folder)
        headerData!!.add(parentDataModel!!)
        childDataModel = ChildDataModel(
            1,
            resources.getString(R.string.activities),
            R.drawable.phone
        )
        contacts!!.add(childDataModel!!)
        childDataModel = ChildDataModel(
            2,
            resources.getString(R.string.contractors),
            R.drawable.people
        )
        contacts!!.add(childDataModel!!)
        childDataModel = ChildDataModel(
            3,
            resources.getString(R.string.contactPerson),
            R.drawable.person
        )
        contacts!!.add(childDataModel!!)
        childDataModel = ChildDataModel(4, resources.getString(R.string.documents), R.drawable.file)
        contacts!!.add(childDataModel!!)
        childDataModel = ChildDataModel(
            5,
            resources.getString(R.string.calendar),
            R.drawable.calendar
        )
        contacts!!.add(childDataModel!!)
        childData!![headerData!![0]] = contacts
        parentDataModel = ParentDataModel(resources.getString(R.string.service), R.drawable.folder)
        headerData!!.add(parentDataModel!!)
        childDataModel = ChildDataModel(
            1,
            resources.getString(R.string.serviceOrder),
            R.drawable.service
        )
        service!!.add(childDataModel!!)
        childDataModel = ChildDataModel(
            2,
            resources.getString(R.string.statistics),
            R.drawable.analytics
        )
        service!!.add(childDataModel!!)
        childData!![headerData!![1]] = service
    }

    private fun initItems() {
        headerData = ArrayList()
        childData = HashMap()
        contacts = ArrayList()
        service = ArrayList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (mDrawerToggle!!.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(
            item
        )
    }

    private fun menuOptions() {
        tv = findViewById<View>(R.id.name) as TextView
        tv!!.setOnClickListener { v: View? ->
            popupMenu = PopupMenu(this@DrawerActivity, tv!!)
            popupMenu!!.menuInflater.inflate(R.menu.popup_menu, popupMenu!!.menu)
            popupMenu!!.setOnMenuItemClickListener { item: MenuItem? -> true }
            popupMenu!!.show()
            tv!!.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0, R.drawable.show_less, 0
            )

            try {
                var popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                popupMenu!!.show()
            }

            popupMenu!!.setOnDismissListener { menu: PopupMenu? ->
                tv!!.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0, R.drawable.show_more, 0
                )
            }
        }
    }

    private fun setUserData(currentUser: CurrentUser) {

        var textView: TextView = findViewById(R.id.name)
        var fullname: String = currentUser.name + " " + currentUser.surname
        textView.setText(fullname)

    }
}