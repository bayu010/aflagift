package com.example.alfagift.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import com.example.alfagift.R
import com.example.alfagift.databinding.DialogProgressBinding

object ProgressDialog {

    @SuppressLint("StaticFieldLeak")
    private var progressDialog: ProgressDialogImpl? = null

    fun show(context: Context, stringRes: Int = -1, onBackPress: OnProgressBackPressed? = null) {
        show(context, stringRes, onBackPress, true)
    }

    fun show(context: Context,
        stringRes: Int = -1,
        onBackPress: OnProgressBackPressed? = null,
        dismissPreviousDialog: Boolean) {
        if (dismissPreviousDialog) {
            dismiss()
            progressDialog = ProgressDialogImpl(context).apply {
                setText(stringRes)
                onProgressBackPressed = onBackPress
                if (context is Activity && !context.isFinishing) show()
            }
        } else {
            var isToBeShown = false
            if (progressDialog == null) {
                progressDialog = ProgressDialogImpl(context)
                isToBeShown = true
            }
            progressDialog?.let { progressDialog ->
                if (stringRes != -1) progressDialog.setText(stringRes)
                progressDialog.onProgressBackPressed = onBackPress

                if (isToBeShown) {
                    if (context is Activity && !context.isFinishing) progressDialog.show()
                }
            }
        }
    }

    fun dismiss() {
        progressDialog?.let {
            if (it.isShowing) it.dismiss()
            progressDialog = null
        }
    }

    fun setProgress(progress: Int) {
        progressDialog?.let {
            if (it.isShowing) it.setProgress(progress)
        }
    }

    fun setIndeterminate(isIndeterminate: Boolean) {
        progressDialog?.let {
            if (it.isShowing) it.setIndeterminate(isIndeterminate)
        }
    }

    fun setText(message: String) {
        progressDialog?.let {
            if (it.isShowing) it.setText(message)
        }
    }

    private class ProgressDialogImpl(val context: Context) {

        private val dialog: Dialog
        private var binding: DialogProgressBinding
        var onProgressBackPressed: OnProgressBackPressed? = null

        init {
            dialog = object : Dialog(context, R.style.AppTheme_Dialog_Transparent) {
                override fun onBackPressed() {
                    onProgressBackPressed?.invoke()
                }
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            binding = DialogProgressBinding.inflate(LayoutInflater.from(context))
            dialog.setContentView(binding.root)
        }

        fun setIndeterminate(isIndeterminate: Boolean) {
            binding.progressBar.isIndeterminate = isIndeterminate
        }

        fun setProgress(percent: Int) {
            if (binding.progressBar.isIndeterminate) binding.progressBar.isIndeterminate = false
            binding.progressBar.progress = percent
        }

        fun setText(stringRes: Int) {
            try {
                val text = context.resources.getString(stringRes)
                setText(text)
            } catch (e: Resources.NotFoundException) {
                setText(null)
            }
        }

        fun setText(message: String?) {
            if (!message.isNullOrEmpty()) binding.txtLoading.text = message
        }

        fun show() {
            dialog.show()
        }

        fun dismiss() {
            try {
                dialog.dismiss()
            } catch (e: Exception) {
//                log.printStackTrace(e)
            }
        }

        val isShowing: Boolean
            get() = dialog.isShowing
    }
}