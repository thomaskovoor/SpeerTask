package com.example.speerapplication.ui

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.FragmentActivity
import com.example.speerapplication.R


class CustomProgressBar(var activity: FragmentActivity?) {
        var dialog:Dialog? = null

        fun  showDialog(){
            dialog = Dialog(activity!!)
            dialog!!.setContentView(R.layout.loading_progress_bar)
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
            dialog!!.show()
            dialog!!.setCancelable(false)
        }

        fun dismissDialog(){
            if(dialog == null){
                dialog = Dialog(activity!!)
            }
            dialog!!.dismiss()
            dialog!!.setCanceledOnTouchOutside(true)
        }

        fun getVisibility(): Boolean {
            return dialog!!.isShowing
        }
    }
