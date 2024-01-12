package com.example.speerapplication.ui

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.FragmentActivity
import com.example.speerapplication.R

/**
 * The `CustomProgressBar` class is used to create and manage a custom progress bar dialog in the application.
 * This class is used to show a loading spinner while the application is performing a network request or other long-running task.
 *
 * @property activity The activity where the progress bar will be shown. This is a `FragmentActivity`.
 * @property dialog The dialog that represents the progress bar. 
 *
 * @function showDialog This function shows the progress bar dialog. It first creates a new dialog with the activity as its context.
 * It then sets the content view of the dialog to `R.layout.loading_progress_bar`, which is the layout resource for the progress bar.
 * It sets the background of the dialog window to be transparent, and then shows the dialog. The dialog is not cancelable, meaning it can't be dismissed by pressing the back button.
 *
 * @function dismissDialog This function dismisses the progress bar dialog. If the dialog is null, it creates a new dialog with the activity as its context.
 * It then dismisses the dialog and sets it to be cancelable when touched outside.
 *
 * @function getVisibility This function returns a boolean indicating whether the progress bar dialog is currently being shown.
 */


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
