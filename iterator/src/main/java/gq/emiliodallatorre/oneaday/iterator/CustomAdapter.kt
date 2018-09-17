package gq.emiliodallatorre.oneaday.iterator

import android.app.Activity
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

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

        val dashboardadvice: TextView = rootView.findViewById(R.id.dashboard_advice) as TextView
        val dashboarddate: TextView = rootView.findViewById(R.id.dashboard_date) as TextView
        val dashboardmonth: TextView = rootView.findViewById(R.id.dashboard_month) as TextView

        val adviceModel: AdviceModel = adviceList[i]

        dashboardadvice.text = adviceModel.title
        dashboarddate.text = adviceModel.date
        dashboardmonth.text = "sept"

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