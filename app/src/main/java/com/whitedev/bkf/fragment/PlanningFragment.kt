package com.whitedev.bkf.fragment

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.whitedev.bkf.Constants
import com.whitedev.bkf.InterfaceApi
import com.whitedev.bkf.R
import com.whitedev.bkf.Utils
import com.whitedev.bkf.pojo.Auth
import com.whitedev.bkf.pojo.TableList
import kotlinx.android.synthetic.main.fragment_planning.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class PlanningFragment : Fragment() {

    private val randomBoolean: Boolean
        get() {
            val random = Random()
            return random.nextBoolean()
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_planning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //addDynamicTable()


        getListColumnAtelier()
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
            val service = retrofit.create(InterfaceApi::class.java)

            val call: Call<Auth> = service.getListColumnAtelier(tok)

            call.enqueue(object : Callback<Auth> {
                override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                    Handler().postDelayed({
                        response.body()?.let { body ->
                            if (body.status == Constants.SUCCESS) {
                                body.list?.let {
                                    listColumn = it
                                    addDynamicTable(listColumn)
                                }
                            }
                        }
                    }, 1500)
                }

                override fun onFailure(call: Call<Auth>, t: Throwable) {
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


    private fun addDynamicTable(columnList: List<TableList>) {
        //val table = TableLayout(activity)
        val table = table_lay

        //table.isStretchAllColumns = true
        //table.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        //table.dividerDrawable = this.resources.getDrawable(R.drawable.cell_background)

        //table.setBackground(borderDrawable(2));
        //table.setPadding(4,4,4,4);

        for (i in 0..9) {

            val tableRow = arrayOfNulls<TableRow>(10)
            tableRow[i] = TableRow(activity)

            tableRow[i]?.let {tableR ->
                tableR.gravity = Gravity.CENTER

                for (tablel in columnList) {

                    when (tablel.type) {
                        "text" -> {
                            val tv = TextView(activity)
                            //tv.gravity = Gravity.START

                            tv.setBackgroundColor(ContextCompat.getColor(context!!,android.R.color.white))

                           // if (tablel.display)
                                tv.text = tablel.name
                           // else tv.text = ""

                           // tv.setPadding(4, 20, 4, 20)
                           // tv.background = borderDrawable(2)

                            tv.layoutParams = TableRow.LayoutParams(
                                TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT)


                            tableR.addView(tv)
                        }

                        "checkbox" -> {
                            val cb = CheckBox(activity)
                            //cb.gravity = Gravity.CENTER
                            cb.isChecked = randomBoolean
                            cb.setPadding(0, 4, 0, 4)
                            //cb.background = borderDrawable(2)

                            cb.layoutParams = TableRow.LayoutParams(
                                TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT)

                            cb.setBackgroundColor(ContextCompat.getColor(context!!,android.R.color.white))

                            tableR.addView(cb)
                        }

                    }

                }

                tableR.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT)


                table.addView(tableR)
            }
        }

        //val container = container_planning
        //container.addView(table)
    }

    private fun borderDrawable(borderWidth: Int): GradientDrawable {
        val shapeDrawable = GradientDrawable()
        shapeDrawable.setStroke(borderWidth, ContextCompat.getColor(context!!, R.color.colorAccent))
        return shapeDrawable
    }

    private fun getName(i: Int): String {

        if (i == 2) {
            return "Recooooooooooooooord"
        } else if (i == 3) {
            return "Recooooooord"
        }

        return "Fran"
    }

    companion object {

        fun newInstance(): PlanningFragment {
            return PlanningFragment()
        }
    }
}