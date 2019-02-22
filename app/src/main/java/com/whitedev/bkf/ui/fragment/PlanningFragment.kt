package com.whitedev.bkf.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.evrencoskun.tableview.TableView
import com.whitedev.bkf.Constants
import com.whitedev.bkf.R
import com.whitedev.bkf.Utils
import com.whitedev.bkf.data.network.RestApi
import com.whitedev.bkf.data.network.pojo.TableList
import com.whitedev.bkf.model.ServiceResponse
import com.whitedev.bkf.ui.tableview.MyTableAdapter
import com.whitedev.bkf.ui.tableview.model.CellModel
import com.whitedev.bkf.ui.tableview.model.ColumnHeaderModel
import com.whitedev.bkf.ui.tableview.model.RowHeaderModel
import kotlinx.android.synthetic.main.fragment_planning.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlanningFragment : Fragment() {


    lateinit var tableView: TableView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_planning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getListColumnAtelier()

        tableView = table_view

        iv_back.setOnClickListener {

            for (i in 6 downTo 0)
                tableView.adapter.removeRow(i)
            getListColumnAtelier()
        }

        iv_next.setOnClickListener {
            for (i in 6 downTo 0)
                tableView.adapter.removeRow(i)
            getListColumnAtelier()
        }
    }

    private lateinit var token: String
    lateinit var listColumn: List<TableList>

    private fun getListColumnAtelier() {

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
                                body.list?.let {
                                    listColumn = it
                                    addDynamicTableTest(listColumn)
                                }
                            }
                        }
                    }, 1500)
                }

                override fun onFailure(call: Call<ServiceResponse>, t: Throwable) {
                    Toast.makeText(
                        context,
                        getString(R.string.planning_failure_msg),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        }
    }

    private fun addDynamicTableTest(listColumn: List<TableList>) {

        val adapter = MyTableAdapter(context)
        tableView.adapter = adapter

        val mRowHeaderList: MutableList<RowHeaderModel> = mutableListOf()
        val mColumnHeaderList: MutableList<ColumnHeaderModel> = mutableListOf()
        val mCellList: MutableList<MutableList<CellModel>> = mutableListOf()

        val mCList: MutableList<CellModel> = mutableListOf()
        val mCList2: MutableList<CellModel> = mutableListOf()
        val mCList3: MutableList<CellModel> = mutableListOf()
        val mCList4: MutableList<CellModel> = mutableListOf()
        val mCList5: MutableList<CellModel> = mutableListOf()
        val mCList6: MutableList<CellModel> = mutableListOf()
        val mCList7: MutableList<CellModel> = mutableListOf()


        for (list in listColumn) {
            mColumnHeaderList.add(ColumnHeaderModel(list.name))
        }


        for (i in 0..listColumn.size) {
            mRowHeaderList.add(RowHeaderModel(""))
            mCList.add(CellModel("cell"))
            mCList2.add(CellModel("cell"))
            mCList3.add(CellModel("cell"))
            mCList4.add(CellModel("cell"))
            mCList5.add(CellModel("cell"))
            mCList6.add(CellModel("cell"))
            mCList7.add(CellModel("cell"))
        }


        mCellList.add(mCList)
        mCellList.add(mCList2)
        mCellList.add(mCList3)
        mCellList.add(mCList4)
        mCellList.add(mCList5)
        mCellList.add(mCList6)
        mCellList.add(mCList7)

        tableView.rowHeaderWidth = 0
        adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList)
    }

    companion object {

        fun newInstance(): PlanningFragment {
            return PlanningFragment()
        }
    }
}