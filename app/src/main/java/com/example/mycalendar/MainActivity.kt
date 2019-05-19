package com.example.mycalendar
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val adapter by lazy { MainAdapter(supportFragmentManager) }
    private final val FRAGMENT_MONTH = 0
    private final val FRAGMENT_WEEK = 1
    private final val FRAGMENT_DAY = 2

    private var selectedDay : CalendarDay = CalendarDay.today()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)

        btnMonth.setOnClickListener(this)
        btnWeek.setOnClickListener(this)
        btnDay.setOnClickListener(this)
        btnSchedule.setOnClickListener(this)

        calendarViewPager.adapter = MainActivity@adapter

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onClick(v: View?){
        when(v?.id){
            R.id.btnMonth -> {
                calendarViewPager.currentItem = FRAGMENT_MONTH
            }
            R.id.btnWeek -> {
                calendarViewPager.currentItem = FRAGMENT_WEEK
            }
            R.id.btnDay -> {
                calendarViewPager.currentItem = FRAGMENT_DAY
            }
            R.id.btnSchedule -> {
                if(selectedDay == null){
                    Toast.makeText(this, "날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
                }else
                {
                    ScheduleDialog.Builder(this)
                        .setDate(selectedDay)
                        .show()
                }
            }
        }
    }

    fun setSelectedDay(day : CalendarDay){
        selectedDay = day

    }

    fun context(): Context = this



}


