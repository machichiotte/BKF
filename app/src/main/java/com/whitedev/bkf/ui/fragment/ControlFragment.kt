package com.whitedev.bkf.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import com.whitedev.bkf.MainActivity
import com.whitedev.bkf.R
import com.whitedev.bkf.SpinnerItemSelectedListener
import com.whitedev.bkf.model.control.ControlData
import kotlinx.android.synthetic.main.fragment_control.*
import java.io.ByteArrayOutputStream

class ControlFragment : Fragment() {

    private var isControlValid = false
    private var isOrderValid = false

    private val REQUEST_IMAGE_CAPTURE = 1
    private val bitmapList: MutableList<Bitmap> = mutableListOf()
    private val encodedList = mutableListOf<String>()

    companion object {
        fun newInstance(): ControlFragment {
            return ControlFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setActionBarTitle(getString(R.string.control))

        handleSpinner()
        handleAddPicture()
        handleControlValidation()
        handleOrderValidation()
        handleSaveButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras.get("data") as Bitmap

            val image = ImageView(context)
            image.layoutParams = android.view.ViewGroup.LayoutParams(360, 270)
            image.maxHeight = 360
            image.maxWidth = 480
            image.setImageBitmap(imageBitmap)
            image.setPadding(8, 0, 8, 0)

            image.setOnClickListener {
                (it.parent as LinearLayout).removeView(it)
                handleAddButtonVisibility()
            }

            // Adds the view to the layout
            ll_pic_list.addView(image)

            handleAddButtonVisibility()

            bitmapList.add(imageBitmap)
        }
    }

    private fun handleControlValidation() {
        cb_control.setOnCheckedChangeListener { _, isCkd ->
            isControlValid = isCkd
        }
    }

    private fun handleOrderValidation() {
        tv_validation.setOnClickListener {

            //todo ajouter appel webservice pour check la commande
            isOrderValid = true

            //mock
            if (isOrderValid) {
                ll_landmark.visibility = View.GONE
                ll_order.visibility = View.GONE
                tv_validation.visibility = View.GONE
                ll_result.visibility = View.VISIBLE

                tv_client.text = "test text client"
                tv_description.text = "test text client"
            } else
                tv_validation.setBackgroundColor(Color.MAGENTA)
        }
    }

    private fun handleSaveButton() {
        tv_save_control.setOnClickListener {
            if (checkFields()) {
                encodedList.clear()

                for (bitm in bitmapList) {
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                    val byteArray = byteArrayOutputStream.toByteArray()
                    val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)

                    encodedList.add(encoded)
                }

                sendControlData(encodedList)
            }
        }
    }

    private fun sendControlData(encodedList: MutableList<String>) {
        val controlObj = ControlData(
            StringBuilder().append(edt_order.text).append(spinner_landmark.selectedItem.toString()).toString(),
            edt_comment.text.toString(),
            encodedList
        )

        //todo envoyez l'objet quand celui-ci sera complet


        resetAfterSendingData()
    }

    private fun checkFields(): Boolean {
        return isControlValid && isOrderValid && bitmapList.size > 0 && edt_comment.text.isNotEmpty()
    }

    private fun handleAddPicture() {
        iv_add_pic.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun handleAddButtonVisibility() {
        if (ll_pic_list.childCount == 5)
            iv_add_pic.visibility = View.GONE
        else
            iv_add_pic.visibility = View.VISIBLE
    }

    private fun handleSpinner() {
        val spinner: Spinner = spinner_landmark
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            activity,
            R.array.control_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = SpinnerItemSelectedListener()
    }

    private fun resetAfterSendingData() {
        ll_landmark.visibility = View.VISIBLE
        ll_order.visibility = View.VISIBLE
        tv_validation.visibility = View.VISIBLE
        ll_result.visibility = View.GONE

        edt_order.text.clear()
        edt_comment.text.clear()

        tv_validation.setBackgroundColor(Color.BLUE)

    }
}