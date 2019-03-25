package com.whitedev.bkf.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.evrencoskun.tableview.TableView
import com.google.gson.Gson
import com.whitedev.bkf.*
import com.whitedev.bkf.data.network.RestApi
import com.whitedev.bkf.data.network.pojo.CheckBoxPojo
import com.whitedev.bkf.model.ServiceResponse
import com.whitedev.bkf.model.X
import com.whitedev.bkf.ui.tableview.MyTableAdapter
import com.whitedev.bkf.ui.tableview.MyTableViewListener
import com.whitedev.bkf.ui.tableview.model.CellModel
import com.whitedev.bkf.ui.tableview.model.ColumnHeaderModel
import com.whitedev.bkf.ui.tableview.model.RowHeaderModel
import kotlinx.android.synthetic.main.fragment_production.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

class ProductionFragment : Fragment() {

    private lateinit var token: String
    lateinit var tableView: TableView
    private var statusSwipe: Int = Constants.WEEK_0
    private var previousSize = 0
    private var maxWeek = 1

    private var listCb: MutableList<CheckBoxPojo?> = mutableListOf()

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    @Subscribe
    fun onCheckBoxEvent(event: CheckBoxSelectionEvent) {
        val cbObject = CheckBoxPojo(event.column, event.id, event.isCkd)

        var plop = true

        for (cb in listCb) {
            cb?.let {
                if (it.id!!.contains(event.id.toString()) && it.name == event.column) {
                    Log.e("test", "naming_it.id::" + it.id)
                    Log.e("test", "naming_event.id::" + event.id)
                    Log.e("test", "naming_it.name::" + it.name)
                    Log.e("test", "naming_event.column::" + event.column)
                    Log.e("test", "size1::" + listCb.size)

                    if (it.isChk != event.isCkd) {
                        listCb.remove(it)
                        Log.e("test", "size2::" + listCb.size)
                        listCb.add(cbObject)
                    }

                    plop = false
                    Log.e("test", "size3::" + listCb.size)
                }
            }
        }

        Log.e("test", "size4::" + listCb.size)
        if (plop)
            listCb.add(cbObject)
        Log.e("test", "size5::" + listCb.size)

        btn_send.isEnabled = true
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_production, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setActionBarTitle(getString(R.string.production))

        tableView = prod_table_view
        getList(0)
        prepareButtons()
    }

    private fun prepareButtons() {
        ll_prod_table_view.setOnTouchListener(object : OnSwipeTouchListener(context) {

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                backAction()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                nextAction()
            }
        })

        iv_prod_back.setOnClickListener {
            if (statusSwipe != Constants.WEEK_0)
                nextAction()
        }

        iv_prod_next.setOnClickListener {
            if (statusSwipe != Constants.WEEK_3)
                backAction()
        }

        btn_send.setOnClickListener {
            sendModifiedData()
        }
    }

    private fun sendModifiedData() {
        //todo ajouter l'envoi à JEREM
        Toast.makeText(activity, "Je viens d'envoyer " + listCb.size + " modifications", Toast.LENGTH_SHORT).show()
        listCb.clear()
        btn_send.isEnabled = false

    }

    private fun prepareSwipe() {
        when (statusSwipe) {
            Constants.WEEK_1 -> {
                iv_prod_back.visibility = View.VISIBLE

                if (maxWeek < 3)
                    iv_prod_next.visibility = View.GONE
                else
                    iv_prod_next.visibility = View.VISIBLE
            }

            Constants.WEEK_0 -> {
                iv_prod_back.visibility = View.GONE

                if (maxWeek < 2)
                    iv_prod_next.visibility = View.GONE
                else
                    iv_prod_next.visibility = View.VISIBLE
            }

            Constants.WEEK_2 -> {
                iv_prod_next.visibility = View.VISIBLE
                if (maxWeek < 4)
                    iv_prod_next.visibility = View.GONE
            }
            Constants.WEEK_3 -> {
                iv_prod_next.visibility = View.GONE
            }
        }
    }

    private fun backAction() {
        if (statusSwipe != Constants.WEEK_3) {
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
        if (statusSwipe != Constants.WEEK_0) {
            statusSwipe--
            prepareSwipe()

            for (i in previousSize - 1 downTo 0)
                tableView.adapter.removeRow(i)
            previousSize = 0

            listCb.clear()


            getList(statusSwipe)
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

            val call: Call<ServiceResponse> = service.getListDataAtelier(tok, Date().time)

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

            val msg = String.format(resources.getString(R.string.week_message), list[position].week)

            tv_prod_week.text = msg


            var colorString = list[position].color
            if (!colorString.contains("#")) {
                colorString = "#$colorString"
            }

            tv_prod_week.setTextColor(Color.parseColor(colorString))

            //prepare only column
            val dataCellList = list[position].data[0].datacell
            for (data in dataCellList) {
                var columnName = ""
                if (data.display)
                    columnName = data.name
                mColumnHeaderList.add(ColumnHeaderModel(columnName, data.data))
            }

            previousSize = list[position].data.size
            for (i in 0 until previousSize) {
                mRowHeaderList.add(RowHeaderModel("nada"))
                val mCList: MutableList<CellModel> = mutableListOf()

                for (j in 0 until dataCellList.size) {
                    mCList.add(
                        CellModel(
                            list[position].data[i].datacell[2].data + list[position].data[i].datacell[3].data,
                            list[position].data[i].datacell[j].data, list[position].data[i].datacell[j].name
                        )
                    )
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
        fun newInstance(): ProductionFragment {
            return ProductionFragment()
        }
    }

}

