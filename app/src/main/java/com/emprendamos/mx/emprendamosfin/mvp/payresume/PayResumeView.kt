package com.emprendamos.mx.emprendamosfin.mvp.payresume

import android.app.AlertDialog
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.emprendamos.mx.emprendamosfin.R
import com.emprendamos.mx.emprendamosfin.extensions.editableZero
import com.emprendamos.mx.emprendamosfin.ui.interfaces.OnPaymentAddedListener
import kotlinx.android.synthetic.main.fragment_pay_register.*
import kotlinx.android.synthetic.main.fragment_pay_resume.view.*
import kotlinx.android.synthetic.main.fragment_paysequence.*
import java.lang.ref.WeakReference

class PayResumeView(act : View) {

    val view = WeakReference(act).get()!!

    fun init(listener: OnPaymentAddedListener?, code: String?, group: String?, cicle: String?, date: String?, type: String?, observations: String?, theorical_payment: Double, real_payment: Double, refund: Double, aport: Double, fee: Double, saving: Double, sequenceId: Long) {
        view.txtcode.text = code
        view.txtgroup.text = group
        view.txtcicle.text = String.format("%02d", cicle?.toLong() ?: 0)
        view.txtdate.text = date ?: ""
        view.txttype.text = type
        view.txtobservations.setText(observations)
        view.txtobservations.isEnabled = false

        view.tiet_theorical_payment.setText(editableZero(theorical_payment))
        view.tiet_real_payment.setText(editableZero(real_payment))
        view.tiet_return.setText(editableZero(refund))
        view.tiet_contribution.setText(editableZero(aport))
        view.tiet_fee.setText(editableZero(fee))
        view.tiet_saving.setText(editableZero(saving))
        view.tiet_total.setText(editableZero(real_payment + aport))
        view.btn_save_payment.setOnClickListener {
            listener?.onSequenceEnded(sequenceId)
        }

        view.btn_show_description_res.setOnClickListener {
            var dialogBuilder = AlertDialog.Builder(view.context)
            val dialogView = LayoutInflater.from(view.context).inflate(R.layout.fragment_description, null)
            val dialog = dialogBuilder.create()
            dialogView.findViewById<EditText>(R.id.txtDescriptionViewI).apply {
                setText(view.txtobservations.text.toString())
                setTypeface(Typeface.DEFAULT_BOLD)
                isFocusable = false
                isClickable = false
                setTextIsSelectable(false)
            }
            dialogView.findViewById<Button>(R.id.btnAceptar).setOnClickListener {
                dialog.dismiss()
            }
            dialog.setView(dialogView)
            dialog.show()
        }
    }

    fun showDialog(window: Window) {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}