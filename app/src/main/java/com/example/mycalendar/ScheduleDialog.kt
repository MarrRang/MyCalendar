package com.example.mycalendar

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.dialog_schedule.*
import java.time.LocalDate


class ScheduleDialog(context : Context) : Dialog(context), View.OnClickListener {

    var date : CalendarDay = CalendarDay.today()
    private val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_schedule)

        dialog_btnCancle.setOnClickListener(this)
        dialog_btnRegist.setOnClickListener(this)

        dialog_text.setText(date.date.toString())
    }

    class Builder(context: Context){
        private val dialog = ScheduleDialog(context)
        fun setDate(date:CalendarDay): Builder {
            dialog.date = date
            return this
        }

        fun show(): ScheduleDialog {
            dialog.show()
            return dialog
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.dialog_btnCancle -> dismiss()
            R.id.dialog_btnRegist -> {
                realm.beginTransaction()

                val newItem = realm.createObject<Todo>(nextId())
                newItem.title = dialog_edit.text.toString()
                newItem.date = date.date.toString()
                newItem.year = date.year
                newItem.month = date.month
                newItem.day = date.day

                realm.commitTransaction()
                Toast.makeText(context, "등록되었습니다", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    private fun nextId(): Int {
        val maxId = realm.where<Todo>().max("id")
        if(maxId != null) {
            return maxId.toInt() + 1
        }
        return 0
    }

}
