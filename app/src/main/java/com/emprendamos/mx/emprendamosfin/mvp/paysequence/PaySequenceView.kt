package com.emprendamos.mx.emprendamosfin.mvp.paysequence

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Typeface
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.emprendamos.mx.emprendamosfin.R
import com.emprendamos.mx.emprendamosfin.data.database.repository.entities.SequencePayControl
import com.emprendamos.mx.emprendamosfin.data.model.Types
import com.emprendamos.mx.emprendamosfin.ui.adapters.SequencesByGroupListAdapter
import com.emprendamos.mx.emprendamosfin.extensions.getTypePosition
import com.emprendamos.mx.emprendamosfin.ui.PayEditFragment
import com.emprendamos.mx.emprendamosfin.ui.interfaces.OnSelectedItemInterface
import kotlinx.android.synthetic.main.fragment_pay_register.*
import kotlinx.android.synthetic.main.fragment_paysequence.*
import kotlinx.android.synthetic.main.fragment_paysequence.back_layout
import kotlinx.android.synthetic.main.fragment_paysequence.recyclerview
import kotlinx.android.synthetic.main.fragment_paysequence.spinner_type
import kotlinx.android.synthetic.main.fragment_paysequence.txtcicle
import kotlinx.android.synthetic.main.fragment_paysequence.txtcode
import kotlinx.android.synthetic.main.fragment_paysequence.txtdate
import kotlinx.android.synthetic.main.fragment_paysequence.txtgroup
import kotlinx.android.synthetic.main.fragment_paysequence.txtobservations
import java.lang.ref.WeakReference

class PaySequenceView(act : FragmentActivity) {

    val activity = WeakReference(act).get()!!

    fun init(code: Int?, cicle: Short?, group_name: String?, date: String?, type: String?, observations: String?){
        activity.txtcode.text = activity!!.getString(R.string.cFormatoCodigoGC,code)
        activity.txtgroup.text = group_name
        activity.txtcicle.text = String.format("%02d", cicle?.toLong() ?: 0)

        activity.txtdate.text = date ?: ""

        val types = arrayOf(Types.NOT_ASIGNED, Types.S, Types.P)
        val adapter = ArrayAdapter(
                activity!!,
                R.layout.item_spinner,
                types
        )
        activity.txtobservations.isEnabled = false
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activity.spinner_type!!.adapter = adapter

        if(type != null) {
            activity.spinner_type.setSelection(getTypePosition(type!!))
        } else {
            activity.spinner_type.setSelection(0)
        }

        activity.spinner_type.isEnabled = false
        activity.txtdate.isEnabled = false

        activity.txtobservations.text = Editable.Factory.getInstance().newEditable(observations)

        activity.back_layout.visibility = View.GONE
        activity.recyclerview.layoutManager = LinearLayoutManager(activity)


        activity.btn_show_description_seq.setOnClickListener {
            var dialogBuilder = AlertDialog.Builder(activity)
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.fragment_description, null)
            val dialog = dialogBuilder.create()
            dialogView.findViewById<EditText>(R.id.txtDescriptionViewI).apply {
                setText(activity.txtobservations.text.toString())
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

    fun setGroups(sequences : ArrayList<SequencePayControl>, date: Long, listener : OnSelectedItemInterface) {
        activity.recyclerview.adapter = SequencesByGroupListAdapter(sequences, date, listener)
    }

    fun navigateToFragment(code: Int?, cicle: Short?, group_name: String?, date: String?, type: String?, paycontrol_id : Long, sequence : Short) {
        activity.supportFragmentManager?.beginTransaction()?.replace(R.id.container, PayEditFragment.newInstance(code!!, cicle!!, group_name!!, paycontrol_id, date!!, activity.spinner_type.selectedItem.toString(), activity.txtobservations.text.toString(), sequence))?.commit()

    }

}