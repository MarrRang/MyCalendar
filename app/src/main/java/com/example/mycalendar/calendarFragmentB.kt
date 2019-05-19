package com.example.mycalendar

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_calendar_week.*

class calendarFragmentB : Fragment(), OnDateSelectedListener {
    // TODO: Rename and change types of parameters

    private val realm = Realm.getDefaultInstance()
    private var selectedDate = CalendarDay.today()

    var realmResult : RealmResults<Todo> = realm.where<Todo>().findAll()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar_week, container, false)
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragment_calendarView_week.state().edit().commit()
        fragment_calendarView_week.setOnDateChangedListener(this)

        realmResult = realm.where<Todo>()
            .equalTo("date", selectedDate.date.toString())
            .sort("id", Sort.DESCENDING)
            .findAll()

        val adapter = TodoAdapter(realmResult, (context as MainActivity))

        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }

        recycle_week.adapter = adapter
        recycle_week.layoutManager = LinearLayoutManager(context)

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        selectedDate = date
        (activity as MainActivity).setSelectedDay(date)

        onDataChanged()

    }

    public fun onDataChanged(){
        realmResult = realm.where<Todo>()
            .equalTo("date", selectedDate.date.toString())
            .sort("id", Sort.DESCENDING)
            .findAll()

        val adapter = TodoAdapter(realmResult, context as MainActivity)

        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }

        recycle_week.adapter = adapter
        recycle_week.layoutManager = LinearLayoutManager(context)

        recycle_week.invalidate()

    }

}

