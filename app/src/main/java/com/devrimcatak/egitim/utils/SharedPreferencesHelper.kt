package com.devrimcatak.egitim.utils

import android.content.Context
import android.content.SharedPreferences
import com.devrimcatak.egitim.utils.Constants.APP_NAME


/**
 * Created by @Devrim Çatak on 29.05.2022.
 */
class SharedPreferencesHelper {
    private var pref : SharedPreferences? = null

    private fun customPrefs(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun setToken(context: Context, token : String){
        pref = customPrefs(context,APP_NAME)
        pref.let {
            val prefsEditor = pref?.edit()
            prefsEditor?.putString("token", token)
            prefsEditor?.apply()
        }
    }

    fun getToken(context: Context): String? {
        pref = customPrefs(context,APP_NAME)
        return pref!!.getString("token"," ")
    }

    fun setSelectedCourse(context: Context, id : Int){
        pref = customPrefs(context,APP_NAME)
        pref.let {
            val prefsEditor = pref?.edit()
            prefsEditor?.putInt("selectedCourse", id)
            prefsEditor?.apply()
        }
    }

    fun getSelectedCourse(context: Context): Int {
        pref = customPrefs(context,APP_NAME)
        return pref!!.getInt("selectedCourse", 0)
    }

    fun setLoginStatus(context: Context, status : Boolean){
        pref = customPrefs(context,APP_NAME)
        pref.let {
            val prefsEditor = pref?.edit()
            prefsEditor?.putBoolean("loginStatus", status)
            prefsEditor?.apply()
        }
    }

    fun getLoginStatus(context: Context): Boolean {
        pref = customPrefs(context,APP_NAME)
        return pref!!.getBoolean("loginStatus", false)
    }

    fun clearValues(context: Context){
        pref = customPrefs(context,APP_NAME)
        pref.let {
            val prefsEditor = pref?.edit()
            prefsEditor?.clear()
            prefsEditor?.commit()
        }
    }

}