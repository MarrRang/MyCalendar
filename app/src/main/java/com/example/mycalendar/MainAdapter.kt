package com.example.mycalendar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup

class MainAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm){

    var data1 = calendarFragmentA()
    var data2 = calendarFragmentB()
    var data3 = calendarFragmentC()

    private var current : Int = 0

    override fun getItem(p0: Int): Fragment? {
        current = p0
        var frg = when(p0){
            0 -> data1
            1 -> data2
            2 -> data3
            else -> null
        }
        return frg
    }

    override fun getCount() = 3

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        //Log.e("FragmentPagerAdapter", "destroyItem position : $position")
    }

    fun getCurrentFragment() : Int{
        return current
    }
}
