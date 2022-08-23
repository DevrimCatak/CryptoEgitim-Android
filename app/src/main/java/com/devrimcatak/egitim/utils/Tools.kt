package com.devrimcatak.egitim.utils

import android.content.Context
import android.widget.Toast


/**
 * Created by @Devrim Çatak on 30.05.2022.
 */
class Tools {

    fun showToast(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


}