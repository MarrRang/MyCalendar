package com.example.mycalendar

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_calendar_day.*


class calendarFragmentC : Fragment() {
    // TODO: Rename and change types of parameters

    var day : String = ""
    private val realm = Realm.getDefaultInstance()
    private var selectedDate = CalendarDay.today()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar_day, container, false)
        //fragment_calendarView.setCurrentDate(CalendarDay.today(), true);

        return view
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnbackarrow.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                selectedDate = CalendarDay.from(selectedDate.date.minusDays(1))
                day = selectedDate.date.toString()
                text_day.setText(day)
                onDataChanged()
                Toast.makeText(context,"dddd",Toast.LENGTH_SHORT).show()
            }
        })

        btnrightarrow.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                selectedDate = CalendarDay.from(selectedDate.date.plusDays(1))
                day = selectedDate.date.toString()
                text_day.setText(day)
                onDataChanged()
            }
        })

        day = selectedDate.date.toString()
        text_day.setText(day)

        var realmResult = realm.where<Todo>()
            .equalTo("date", selectedDate.date.toString())
            .sort("id", Sort.DESCENDING)
            .findAll()

        val adapter = TodoAdapter(realmResult, (context as MainActivity))

        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }

        recycle_day.adapter = adapter
        recycle_day.layoutManager = LinearLayoutManager(context)

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


    public fun onDataChanged(){
        var realmResult = realm.where<Todo>()
            .equalTo("date", selectedDate.date.toString())
            .sort("id", Sort.DESCENDING)
            .findAll()

        val adapter = TodoAdapter(realmResult, context as MainActivity)

        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }

        recycle_day.adapter = adapter
        recycle_day.layoutManager = LinearLayoutManager(context)

        recycle_day.invalidate()

    }
}

