package gq.emiliodallatorre.oneaday.iterator.experience

import android.content.Context
import android.support.v7.preference.PreferenceManager
import gq.emiliodallatorre.oneaday.iterator.R
import java.util.*

class DashboardLoader {
    fun load(context: Context?): ArrayList<AdviceModel> {
        val currentMonth: Int = Calendar.getInstance().get(Calendar.MONTH)
        val currentDay: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val startDay: Int = PreferenceManager.getDefaultSharedPreferences(context).getInt("startDay", 1)
        val startMonth: Int = PreferenceManager.getDefaultSharedPreferences(context).getInt("startMonth", 1)
        val adviceList = ArrayList<AdviceModel>()
        val advicesArray: Array<String> = context!!.resources.getStringArray(R.array.advicesTitle)
        val advicesSubtitlesArray: Array<String> = context.resources.getStringArray(R.array.advicesSubtitle)

        var f = 0
        var g = 0
        for (i in 0 until advicesArray.size) {
            val adviceModel = AdviceModel()

            // The value f represent the number of days in the startMonth.
            when (startMonth + 1) {
                1 -> f = 31
                2 -> f = 28
                3 -> f = 31
                4 -> f = 30
                5 -> f = 31
                6 -> f = 30
                7 -> f = 31
                8 -> f = 31
                9 -> f = 30
                10 -> f = 31
                11 -> f = 30
                12 -> f = 31
            }

            // The value f represent the number of days in the following month.
            when (currentMonth + 1) {
                1 -> g = 31
                2 -> g = 28
                3 -> g = 31
                4 -> g = 30
                5 -> g = 31
                6 -> g = 30
                7 -> g = 31
                8 -> g = 31
                9 -> g = 30
                10 -> g = 31
                11 -> g = 30
                12 -> g = 31
            }

            // This is if we are in the month following the startMonth.
            if (startMonth < currentMonth) {
                if ((startDay + i) <= f) {
                    adviceModel.date = (startDay + i)
                    adviceModel.month = (startMonth)
                    adviceModel.bar = true
                }
                if ((startDay + i) > f) {
                    adviceModel.date = (startDay + i - f)
                    adviceModel.month = (startMonth + 1)
                    adviceModel.bar = (adviceModel.date!! < currentDay)
                }
            }

            // This is if we are in the month we started.
            if (startMonth == currentMonth) {
                if ((startDay + i) <= g) {
                    adviceModel.date = (startDay + i)
                    adviceModel.month = (startMonth)
                    adviceModel.bar = (adviceModel.date!! < currentDay)
                }
                if ((startDay + i) > f) {
                    adviceModel.date = (startDay + i - f)
                    adviceModel.month = (startMonth + 1)
                    adviceModel.bar = false
                }
            }

            // If it's the ending of the year, go to january.
            if(adviceModel.month == 12) adviceModel.month = 0

            adviceModel.title = advicesArray[i]
            adviceModel.subtitle = advicesSubtitlesArray[i]

            adviceModel.dayOfPath = i + 1
            adviceList.add(adviceModel)
        }
        return adviceList
    }
}