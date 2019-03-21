package com.whitedev.bkf.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
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
import com.whitedev.bkf.data.network.pojo.CheckBoxPojo
import com.whitedev.bkf.model.ServiceResponse
import com.whitedev.bkf.model.X
import com.whitedev.bkf.model.atelier.Atelier
import com.whitedev.bkf.model.atelier.ServiceResponseAtelier
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

    private lateinit var token: String
    private lateinit var tableView: TableView
    private var statusSwipe: Int = WEEK_0
    private var previousSize = 0
    private var maxWeek = 1
    private var listCb: MutableList<CheckBoxPojo?> = mutableListOf()
    private var listAtelier: List<Atelier> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_planning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setActionBarTitle("Planning")

        tableView = table_view
        prepareButtons()

        getListAtelier()
        getList(0)
    }

    private fun prepareButtons() {
        ll_table_view.setOnTouchListener(object : OnSwipeTouchListener(context) {

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
            if (statusSwipe != WEEK_0)
                nextAction()
        }

        iv_next.setOnClickListener {
            if (statusSwipe != WEEK_3)
                backAction()
        }
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

            listCb.clear()
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

            listCb.clear()

            getList(statusSwipe)
        }
    }

    private fun handleSpinner(list: MutableList<String?>) {
        val spinner: Spinner = planning_spinner
        val myAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list)

        myAdapter.also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                getList(statusSwipe)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }
    }

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
        //getListDataAtelier(position)
    }

    private fun getListDataAtelier(position: Int) {
        token = "FQFD5165DQSVCD1QSV651DSFV65FD" //fixme delete this mock

        token.let { tok ->
            val retrofit = Retrofit.Builder()
                .baseUrl(Utils.checkBaseUrl(this.activity!!))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(RestApi::class.java)

            val call: Call<ServiceResponse> = service.getListDataAtelier(tok, System.currentTimeMillis() / 1000)

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
                    Log.e("test", "failure::getListDataAtelier")
                }
            })

        }
    }

    private fun getListAtelier() {
        token = "FQFD5165DQSVCD1QSV651DSFV65FD" //fixme delete this mock

        token.let { tok ->
            val retrofit = Retrofit.Builder()
                .baseUrl(Utils.checkBaseUrl(this.activity!!))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(RestApi::class.java)

            val call: Call<ServiceResponseAtelier> = service.getListAtelier(tok, System.currentTimeMillis() / 1000)

            call.enqueue(object : Callback<ServiceResponseAtelier> {
                override fun onResponse(
                    call: Call<ServiceResponseAtelier>,
                    response: Response<ServiceResponseAtelier>
                ) {
                    Handler().postDelayed({
                        response.body()?.let { body ->
                            if (body.status == Constants.SUCCESS) {
                                val listStr: MutableList<String?> = mutableListOf()

                                listAtelier = body.list
                                Log.e("test", "listAtelier::" + listAtelier.size)

                                for (element in listAtelier)
                                    listStr.add(element.name)

                                handleSpinner(listStr)
                            }
                        }
                    }, 1500)
                }

                override fun onFailure(call: Call<ServiceResponseAtelier>, t: Throwable) {
                    Log.e("test", "failure::getListAtelier")
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

            tv_week.text = String.format(resources.getString(R.string.week_message), list[position].week)

            list[position].color.let {
                var colorString = it
                if (!colorString.contains("#")) {
                    colorString = "#$colorString"
                }
                tv_week.setTextColor(Color.parseColor(colorString))
            }

            //prepare only column

            list[position].data.let {
                list[position].data[0].datacell.let {
                    val dataCellList = list[position].data[0].datacell
                    for (data in dataCellList) {
                        var columnName = ""
                        if (data.display)
                            columnName = data.name
                        mColumnHeaderList.add(ColumnHeaderModel(columnName, data.data))
                    }

                    previousSize = list[position].data.size
                    for (i in 0 until previousSize) {

                        mRowHeaderList.add(RowHeaderModel(""))
                        val mCList: MutableList<CellModel> = mutableListOf()

                        var isInTab = 0

                        for (j in 0 until dataCellList.size) {
                            val listId = arrayListOf<String>()

                            //prepare listId
                            for (atelier in listAtelier) {
                                if (planning_spinner.selectedItem != null && atelier.name == planning_spinner.selectedItem.toString())
                                    for (idd in atelier.id)
                                        listId.add(idd)
                            }

                            for (id in listId) {

                                if (list[position].data[i].datacell[j].name == id && list[position].data[i].datacell[j].data == null)
                                    isInTab++
                            }

                            mCList.add(
                                CellModel(
                                    list[position].data[i].datacell[2].data + list[position].data[i].datacell[3].data,
                                    list[position].data[i].datacell[j].data, list[position].data[i].datacell[j].name
                                )
                            )


                        }


                        if (isInTab == 0)
                            mCellList.add(mCList)
                    }
                }

            }
        }

        tableView.rowHeaderWidth = 0
        tableView.tableViewListener = MyTableViewListener()
        tableView.isIgnoreSelectionColors = true
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