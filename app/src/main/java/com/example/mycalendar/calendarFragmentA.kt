package com.example.mycalendar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_calendar_week.*
import java.time.LocalDate
import java.util.*


class calendarFragmentA : Fragment(), OnDateSelectedListener {
    // TODO: Rename and change types of parameters

    private val realm = Realm.getDefaultInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val initialDate = CalendarDay.today()
        fragment_calendarView.selectedDate = CalendarDay.today()
        fragment_calendarView.setSelectedDate(initialDate.date)
        fragment_calendarView.setOnDateChangedListener(this)

        fragment_calendarView.state().edit().commit()

        val realmResult: RealmResults<Todo> = realm.where<Todo>()
            .findAll()
            .sort("date", Sort.DESCENDING)

        onDateTodoChanged()

        realmResult.addChangeListener { _ -> onDateTodoChanged() }

    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {

        if(selected)
            (activity as MainActivity).setSelectedDay(date)

        onDateTodoChanged()
    }

    public fun onDateTodoChanged()
    {
        var calendarDays = mutableListOf<CalendarDay>()

        val realmResult: RealmResults<Todo> = realm.where<Todo>()
            .findAll()
            .sort("date", Sort.DESCENDING)

        var realmList = realmResult.toMutableList()

        for (i in 0 until realmList.size){
            calendarDays.add(CalendarDay.from(realmList[i].year, realmList[i].month, realmList[i].day))
        }

        fragment_calendarView.addDecorator(EventDecorator(Color.RED, calendarDays))
    }


}

