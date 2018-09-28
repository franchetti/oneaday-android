package gq.emiliodallatorre.oneaday.iterator

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*

// TODO: Use recyclerView.
class CustomAdapter internal constructor(private val context: Context, private val adviceList: ArrayList<AdviceModel>): BaseAdapter() {

    override fun getCount(): Int {
        return adviceList.size
    }

    override fun getItem(i: Int): Any {
        return adviceList[i]
    }

    override fun getItemId(i: Int): Long {
        return getItem(i).hashCode().toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val rootView: ConstraintLayout = (context as Activity).layoutInflater.inflate(R.layout.custom_adapter, null) as ConstraintLayout
        val adviceModel: AdviceModel = adviceList[i]

        val dashboardAdvice = (rootView.findViewById(R.id.dashboard_advice) as TextView)
        val dashboardDate: TextView = rootView.findViewById(R.id.dashboard_date) as TextView
        val dashboardMonth: TextView = rootView.findViewById(R.id.dashboard_month) as TextView

        dashboardAdvice.text = adviceModel.title
        dashboardDate.text = adviceModel.date.toString()
        dashboardMonth.text = (context).resources.getStringArray(R.array.months)[adviceModel.month!!]

        if(adviceModel.bar!!) {
            dashboardAdvice.paintFlags = dashboardAdvice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        /*
        rootView.setOnClickListener {
            val link = model.link
            val intent = Intent(context, ArticleView::class.java)
            intent.putExtra("link", link)
            intent.putExtra("title", model.title)
            intent.putExtra("creator", creator)
            context.startActivity(intent)
        }
        */

        // rootView.setOnTouchListener { view, motionEvent -> false }

        Log.d(context.localClassName, adviceModel.title)
        return rootView
    }
}