package com.example.mycalendar

import com.prolificinteractive.materialcalendarview.CalendarDay
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.time.LocalDate
import java.util.*

open class Todo(
    @PrimaryKey var id: Long = 0,
    var title: String = "",
    var date: String = "",
    var year : Int = 0,
    var month : Int = 0,
    var day : Int = 0
): RealmObject()
