package com.example.mycalendar

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.OrderedRealmCollection
import kotlinx.android.synthetic.main.item_todo.view.*
import android.widget.PopupMenu
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.CalendarDay
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.dialog_schedule.*
import android.view.MenuItem


class TodoAdapter(private val realmResult: OrderedRealmCollection<Todo>,
                  private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemPosition : Int = 0
    val realm = Realm.getDefaultInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false))

    override fun getItemCount(): Int = realmResult.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item_result = realmResult[position]
        itemPosition = position
        holder.itemView.schedule_text.text = item_result.title

        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v:View){
                val p = PopupMenu(context.applicationContext,v)
                p.menuInflater.inflate(R.menu.menu_popup,p.menu)
                p.setOnMenuItemClickListener{item ->
                    when (item.itemId){
                        R.id.menu_modify -> {
                            ModifyDialog.Builder(context)
                                .setId(item_result.id)
                                .setDate(CalendarDay.from(item_result.year, item_result.month, item_result.day))
                                .setEditText(item_result.title)
                                .show()
                            true
                        }
                        R.id.menu_delete -> {
                            realm.beginTransaction()

                            val deleteItem = realm.where<Todo>().equalTo("id", item_result.id).findFirst()!!
                            deleteItem.deleteFromRealm()

                            realm.commitTransaction()
                            Toast.makeText(context, "삭제되었습니다", Toast.LENGTH_SHORT).show()

                            true
                        }
                        else -> false
                    }
                }

                p.show()

            }
        })
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}
