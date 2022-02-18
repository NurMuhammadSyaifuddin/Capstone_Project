package com.project.capstoneproject.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import com.project.capstoneproject.R
import com.project.capstoneproject.databinding.DialogSuccessCreateAccountBinding

class CustomDialog {
    private var dialog: Dialog? = null

    private fun isShowing(): Boolean = dialog?.isShowing ?: false

    private fun showDialog(
        context: Context,
        view: View,
        dismissListener: DialogInterface.OnDismissListener
    ) {
        if (isShowing()) return
        dialog = Dialog(context, R.style.AlertDialogTheme)
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCanceledOnTouchOutside(false)
            setOnDismissListener(dismissListener)
            setContentView(view)
            show()
        }
    }

    fun showSuccessCreateAccount(context: Context, listener: View.OnClickListener, dismissListener: DialogInterface.OnDismissListener = DialogInterface.OnDismissListener {  }){
        val binding = DialogSuccessCreateAccountBinding.inflate(LayoutInflater.from(context), null, false)
        showDialog(context, binding.root, dismissListener)
        binding.btnNext.setOnClickListener(listener)
    }
}