package gq.emiliodallatorre.oneaday.iterator.experience

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import gq.emiliodallatorre.oneaday.iterator.R
import java.util.*

class CustomAdapter internal constructor(private val context: Context, private val adviceList: ArrayList<AdviceModel>) : BaseAdapter() {
    // Initialize an inflater for some cases.
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return adviceList.size
    }

    override fun getItem(i: Int): Any {
        return adviceList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        // TODO: Use recyclerView.
        val holder: ViewHolder
        var retView: View? = view

        // Retrieve the ViewHolder.
        if (retView == null) {
            retView = inflater.inflate(R.layout.custom_adapter, null) as ConstraintLayout
            holder = ViewHolder(retView)
            retView.tag = holder
        } else {
            retView = view
            holder = retView!!.tag as ViewHolder
        }

        val adviceModel: AdviceModel = adviceList[i]

        // Set text in the TextViews.
        holder.dashboardAdvice.text = adviceModel.title
        holder.dashboardDate.text = adviceModel.date.toString()
        holder.dashboardMonth.text = (context).resources.getStringArray(R.array.months)[adviceModel.month]

        // Set if the text has to be strike-through.
        if (adviceModel.bar) holder.dashboardAdvice.paintFlags = holder.dashboardAdvice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else holder.dashboardAdvice.paintFlags = holder.dashboardAdvice.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

        // Set a listener for click events.
        retView.setOnClickListener {
            val intent = Intent(context, AdviceView::class.java)
            intent.putExtra("advice", adviceModel.title)
            intent.putExtra("dayOfPath", adviceModel.dayOfPath)
            intent.putExtra("date", adviceModel.date)
            intent.putExtra("month", (context).resources.getStringArray(R.array.months)[adviceModel.month])
            context.startActivity(intent)
        }

        // Log advices for debugging scopes.
        Log.d((context as Activity).localClassName, adviceModel.title + " $i ++++++++")

        return retView
    }
}