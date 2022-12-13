package com.example.alfagift.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.alfagift.R
import com.example.alfagift.databinding.DialogCustomMassageBinding

object MessageDialog {

    private var messageDialog: MessageDialogImpl? = null

    fun show(context: Context,
        title: String? = null,
        message: String?,
        labelButtonPositive: String? = null,
        labelButtonNegative: String? = null,
        isTwoBotton: Boolean = false,
        onClickButtonPositiveDialog: OnClickButtonDialog? = null,
        onClickButtonNegativeDialog: OnClickButtonDialog? = null,
        onClickCloseDialog: OnClickCloseDialog? = null) {
        dismiss()
        messageDialog = MessageDialogImpl(context)
        messageDialog?.setTitle(title)
        messageDialog?.setMessage(message)
        messageDialog?.setLabelButtonPositive(labelButtonPositive)
        messageDialog?.setLabelButtonNegative(labelButtonNegative)
        messageDialog?.isTwoButton(isTwoBotton)
        messageDialog?.onClickButtonPositiveDialog = onClickButtonPositiveDialog
        messageDialog?.onClickButtonNegativeDialog = onClickButtonNegativeDialog
        messageDialog?.onClickCloseDialog = onClickCloseDialog

        if (context is Activity && !context.isFinishing) messageDialog?.show()
    }

    /*fun showWithHtmlFormat(context: Context,
        title: String? = null,
        message: String?,
        labelButton: String? = null,
        smallButton: Boolean? = false,
        onCLickButtonDialog: OnClickButtonDialog? = null,
        onClickCloseDialog: OnClickCloseDialog? = null) {
        dismiss()
        messageDialog = MessageDialogImpl(context)
        messageDialog?.setTitle(title)
        messageDialog?.setMessageHtmlFormat(message)
        messageDialog?.setLabelButtonPositive(labelButton)
        messageDialog?.setLabelButtonNegative(labelButton)
        if (smallButton == true) {
            messageDialog?.smallButton()
        }
        messageDialog?.onClickButtonOkDialog = onCLickButtonDialog
        messageDialog?.onClickCloseDialog = onClickCloseDialog

        if (context is Activity && !context.isFinishing) messageDialog?.show()
    }*/

    fun dismiss() {
        messageDialog?.let {
            if (it.isShowing) it.dismiss()
            messageDialog = null
        }
    }

    private class MessageDialogImpl(val context: Context) {

        private val dialog: Dialog
        private var binding: DialogCustomMassageBinding

        var onClickButtonPositiveDialog: OnClickButtonDialog? = null
        var onClickButtonNegativeDialog: OnClickButtonDialog? = null
        var onClickCloseDialog: OnClickCloseDialog? = null

        init {
            dialog = object : Dialog(context, R.style.CustomDialog_Transparent) {
                override fun onBackPressed() {
                    //                    onProgressBackPressed?.invoke()
                }
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            binding = DialogCustomMassageBinding.inflate(LayoutInflater.from(context))
            binding.apply {
                dialog.setContentView(this.root)

                btnDialogPositive.setOnClickListener {
                    if (onClickButtonPositiveDialog != null) onClickButtonPositiveDialog?.invoke()
                    else dismiss()
                }

                btnDialogNegative.setOnClickListener {
                    if (onClickButtonNegativeDialog != null) onClickButtonNegativeDialog?.invoke()
                    else dismiss()
                }

                /*btnOk.setOnClickListener {
                    if (onClickButtonOkDialog != null) onClickButtonOkDialog?.invoke()
                }*/

                btnCancel.setOnClickListener {
                    if (onClickCloseDialog != null) onClickCloseDialog?.invoke()
                    else dismiss()
                }
            }
        }

        /** Show the progress dialog */
        fun show() {
            dialog.show()
        }

        fun setTitle(title: String?) {
            if (title != null) binding.txtTitle.text = title
            else binding.txtTitle.visibility = View.GONE
        }

        fun setMessage(message: String?) {
            if (message != null) binding.txtMessage.text = message
        }

        fun isTwoButton(result: Boolean) {
            if (!result) binding.btnDialogNegative.visibility = View.GONE
            else binding.btnDialogNegative.visibility = View.VISIBLE
        }

        /*fun setMessageHtmlFormat(message: String?) {
            if (message != null) binding.txtMessage.text = Html.fromHtml(message)
        }*/

        //        fun smallButton() {
        //                        binding.btnOk.isVisible = true
        //                        binding.btnDialog.isVisible = false
        //        }

        fun setLabelButtonNegative(label: String?) {
            if (label != null) binding.btnDialogNegative.text = label
        }

        fun setLabelButtonPositive(label: String?) {
            if (label != null) binding.btnDialogPositive.text = label
        }

        /** Dismisses the progress dialog */
        fun dismiss() {
            try {
                dialog.dismiss()
            } catch (e: Exception) {
//                Functions.printStackTrace(e)
            }
        }

        /** Determines if the progress dialog is currently showing */
        val isShowing: Boolean
            get() = dialog.isShowing

    }
}