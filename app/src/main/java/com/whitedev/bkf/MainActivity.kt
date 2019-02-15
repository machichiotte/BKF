package com.whitedev.bkf

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.whitedev.bkf.fragment.ControlFragment
import com.whitedev.bkf.fragment.DocumentationFragment
import com.whitedev.bkf.fragment.PlanningFragment
import com.whitedev.bkf.fragment.ProductionFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var fragmentPlanning: Fragment? = null
    private var fragmentProd: Fragment? = null
    private var fragmentControl: Fragment? = null
    private var fragmentDoc: Fragment? = null

    private val FRAGMENT_PLANNING = 0
    private val FRAGMENT_PROD = 1
    private val FRAGMENT_CONTROL = 2
    private val FRAGMENT_DOC = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_planning -> {
                this.showFragment(FRAGMENT_PLANNING)
            }
            R.id.nav_production -> {
                this.showFragment(FRAGMENT_PROD)
            }
            R.id.nav_control -> {
                this.showFragment(FRAGMENT_CONTROL)
            }
            R.id.nav_documentation -> {
                this.showFragment(FRAGMENT_DOC)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    // ---------------------
    // FRAGMENTS
    // ---------------------

    // 5 - Show fragment according an Identifier

    private fun showFragment(fragmentIdentifier: Int) {
        when (fragmentIdentifier) {
            FRAGMENT_PLANNING -> this.showPlanningFragment()
            FRAGMENT_PROD -> this.showProductionFragment()
            FRAGMENT_CONTROL -> this.showControlFragment()
            FRAGMENT_DOC -> this.showDocumentationFragment()
            else -> {
            }
        }
    }

    // 4 - Create each fragment page and show it

    private fun showPlanningFragment() {
        if (this.fragmentPlanning == null)
            this.fragmentPlanning = PlanningFragment.newInstance()

        fragmentPlanning?.let {
            this.startTransactionFragment(it)

        }
    }

    private fun showProductionFragment() {
        if (this.fragmentProd == null)
            this.fragmentProd = ProductionFragment.newInstance()

        fragmentProd?.let {
            this.startTransactionFragment(it)

        }
    }

    private fun showControlFragment() {
        if (this.fragmentControl == null)
            this.fragmentControl = ControlFragment.newInstance()

        fragmentControl?.let {
            this.startTransactionFragment(it)

        }
    }

    private fun showDocumentationFragment() {
        if (this.fragmentDoc == null)
            this.fragmentDoc = DocumentationFragment.newInstance()

        fragmentDoc?.let {
            this.startTransactionFragment(it)

        }
    }


    // 3 - Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private fun startTransactionFragment(fragment: Fragment) {

        if (!fragment.isVisible) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment).commit()
        }
    }
}
