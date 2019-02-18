package com.whitedev.bkf

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import com.whitedev.bkf.Constants.Companion.BASE_URL

class Utils {

    companion object {

        fun checkBaseUrl(activity: Activity): String {
            /* var baseUrl = BASE_URL

           activity.getSharedPreferences(PREFS_ID, AppCompatActivity.MODE_PRIVATE)?.getString(USER_BASE_URL, null)
                ?.let {
                    baseUrl = it
                }


            return baseUrl
            */

            return BASE_URL
        }
    }
}