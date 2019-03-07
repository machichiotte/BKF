package com.whitedev.bkf.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.evrencoskun.tableview.TableView
import com.google.gson.Gson
import com.whitedev.bkf.*
import com.whitedev.bkf.Constants.Companion.WEEK_0
import com.whitedev.bkf.Constants.Companion.WEEK_1
import com.whitedev.bkf.Constants.Companion.WEEK_2
import com.whitedev.bkf.Constants.Companion.WEEK_3
import com.whitedev.bkf.data.network.RestApi
import com.whitedev.bkf.model.ServiceResponse
import com.whitedev.bkf.model.X
import com.whitedev.bkf.ui.tableview.MyTableAdapter
import com.whitedev.bkf.ui.tableview.MyTableViewListener
import com.whitedev.bkf.ui.tableview.model.CellModel
import com.whitedev.bkf.ui.tableview.model.ColumnHeaderModel
import com.whitedev.bkf.ui.tableview.model.RowHeaderModel
import kotlinx.android.synthetic.main.fragment_planning.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.Charset

class PlanningFragment : Fragment() {

    lateinit var tableView: TableView
    var statusSwipe: Int = 1
    private var previousSize = 0
    private var maxWeek = 1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_planning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tableView = table_view
        statusSwipe = WEEK_0

        getList(0)

        ll_table_view.setOnTouchListener(object : OnSwipeTouchListener(this.activity) {

            override fun onSwipeLeft() {
                super.onSwipeLeft()

                backAction()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                nextAction()

            }
        })

        iv_back.setOnClickListener {
            nextAction()
        }

        iv_next.setOnClickListener {
            backAction()
        }

        handleSpinner()
    }

    private fun prepareSwipe() {
        when (statusSwipe) {
            WEEK_1 -> {
                iv_back.visibility = View.VISIBLE

                if (maxWeek < 3)
                    iv_next.visibility = View.GONE
                else
                    iv_next.visibility = View.VISIBLE
            }

            WEEK_0 -> {
                iv_back.visibility = View.GONE

                if (maxWeek < 2)
                    iv_next.visibility = View.GONE
                else
                    iv_next.visibility = View.VISIBLE
            }

            WEEK_2 -> {
                iv_next.visibility = View.VISIBLE
                if (maxWeek < 4)
                    iv_next.visibility = View.GONE
            }
            WEEK_3 -> {
                iv_next.visibility = View.GONE
            }
        }
    }

    private fun backAction() {
        if (statusSwipe != WEEK_3) {
            statusSwipe++
            prepareSwipe()

            for (i in previousSize - 1 downTo 0)
                tableView.adapter.removeRow(i)
            previousSize = 0
            getList(statusSwipe)
        }
    }

    private fun nextAction() {
        if (statusSwipe != WEEK_0) {
            statusSwipe--
            prepareSwipe()

            for (i in previousSize - 1 downTo 0)
                tableView.adapter.removeRow(i)
            previousSize = 0
            getList(statusSwipe)
        }
    }

    private fun handleSpinner() {
        val spinner: Spinner = planning_spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            activity,
            R.array.planning_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = SpinnerItemSelectedListener()
    }

    private lateinit var token: String

    private fun loadHardcodedMenu(position: Int) {
        var json: String?
        try {
            val inputS = activity?.assets?.open("data.json")
            inputS?.let {
                val size = it.available()
                val buffer = ByteArray(size)
                it.read(buffer)
                it.close()
                json = String(buffer, Charset.forName("UTF-8"))

                val response = Gson().fromJson(json, ServiceResponse::class.java)
                prepareTableViewForPosition(response.list, position)
            }

        } catch (ex: IOException) {
            ex.printStackTrace()
        }

    }

    private fun getList(position: Int) {

        loadHardcodedMenu(position)
        //getListColumnAtelier(position)
    }

    private fun getListColumnAtelier(position: Int) {
        token = "FQFD5165DQSVCD1QSV651DSFV65FD" //fixme delete this mock

        token.let { tok ->
            val retrofit = Retrofit.Builder()
                .baseUrl(Utils.checkBaseUrl(this.activity!!))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(RestApi::class.java)

            val call: Call<ServiceResponse> = service.getListColumnAtelier(tok)

            call.enqueue(object : Callback<ServiceResponse> {
                override fun onResponse(call: Call<ServiceResponse>, response: Response<ServiceResponse>) {
                    Handler().postDelayed({
                        response.body()?.let { body ->
                            if (body.status == Constants.SUCCESS) {
                                prepareTableViewForPosition(body.list, position)
                            }
                        }
                    }, 1500)
                }

                override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
                    Log.e("test", "failure")
                }
            })

        }
    }

    private fun prepareTableViewForPosition(list: List<X>, position: Int) {

        val adapter = MyTableAdapter(context)
        tableView.adapter = adapter

        val mRowHeaderList: MutableList<RowHeaderModel> = mutableListOf()
        val mColumnHeaderList: MutableList<ColumnHeaderModel> = mutableListOf()
        val mCellList: MutableList<MutableList<CellModel>> = mutableListOf()
        if (list.size >= position) {
            maxWeek = list.size
            prepareSwipe()

            tv_week.text = "Semaine " + list[position].week

            var colorString = list[position].color
            if (!colorString.contains("#")) {
                colorString = "#$colorString"
            }

            tv_week.setTextColor(Color.parseColor(colorString))

            //prepare only column
            val dataCellList = list[position].data[0].datacell
            for (data in dataCellList) {
                mColumnHeaderList.add(ColumnHeaderModel(data.name))
            }

            previousSize = list[position].data.size
            for (i in 0 until previousSize) {
                mRowHeaderList.add(RowHeaderModel("nada"))
                val mCList: MutableList<CellModel> = mutableListOf()

                for (j in 0 until dataCellList.size) {
                    mCList.add(CellModel("id:pos:" + position + "pos2:" + j, list[position].data[i].datacell[j].data))
                }

                mCellList.add(mCList)
            }
        }

        tableView.rowHeaderWidth = 0
        tableView.tableViewListener = MyTableViewListener()
        //tableView.isIgnoreSelectionColors = true
        tableView.descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
        tableView.isFocusableInTouchMode = false

        adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList)
    }

    companion object {
        fun newInstance(): PlanningFragment {
            return PlanningFragment()
        }
    }
}