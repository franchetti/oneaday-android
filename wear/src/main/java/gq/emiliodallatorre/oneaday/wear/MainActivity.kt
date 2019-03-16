package gq.emiliodallatorre.oneaday.wear

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.wearable.activity.WearableActivity
import android.widget.TextView
import com.bikomobile.donutprogress.DonutProgress
import java.util.*

class MainActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()
    }

    override fun onStart() {
        super.onStart()
        // TODO: Setup things for lastDay of path.
        val donutProgress: DonutProgress = this.findViewById(R.id.progressBar)/*
        val dayOfPath: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - PreferenceManager.getDefaultSharedPreferences(this).getInt("startDay", 1)
        (this.findViewById<TextView>(R.id.advice_title)).text = resources.getStringArray(R.array.advicesTitle)[dayOfPath]
        (this.findViewById<TextView>(R.id.advice_subtitle)).text = resources.getStringArray(R.array.advicesSubtitle)[dayOfPath]
        donutProgress.text = dayOfPath.toString()*/

        val dayOfPath: Int = 14
        
        donutProgress.max = 28 * 10

        val progressText: String = getString(R.string.main_day) + " " + (dayOfPath + 1) + " " + getString(R.string.main_of) + " 28"
        (this.findViewById<TextView>(R.id.progressText)).text = progressText

        object : Thread() {
            override fun run() {
                donutProgress.setProgressWithAnimation((dayOfPath * 10), 10)
            }
        }.start()
        Handler().postDelayed({
            // Here you can do something on the exact moment the animation finish.
            // TODO: Do something.
        }, (dayOfPath * 10 * 10).toLong())

    }
}
