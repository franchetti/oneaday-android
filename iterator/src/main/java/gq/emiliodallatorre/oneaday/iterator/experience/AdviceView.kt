package gq.emiliodallatorre.oneaday.iterator.experience

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.R.id.scrollView
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.ViewTreeObserver
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import gq.emiliodallatorre.oneaday.iterator.BuildConfig
import gq.emiliodallatorre.oneaday.iterator.R

class AdviceView : AppCompatActivity() {
    lateinit var scrollView: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advice_view)

        // Inflate the textViews of the advice_view layout.
        findViewById<TextView>(R.id.advice_view_title).text = intent.getStringExtra("advice")
        findViewById<TextView>(R.id.advice_view_body).text = resources.getStringArray(R.array.advicesBody)[intent.getIntExtra("dayOfPath", 14) - 1]
        // TODO: Create advicesBody strings.
        findViewById<TextView>(R.id.advice_view_body).text = resources.getStringArray(R.array.advicesBody)[0]

        // This is the listener of the share button.
        findViewById<ImageButton>(R.id.advice_view_share).setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)

            // The share type is text/plain: a simple text which is sharable by almost every application.
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, this.applicationInfo.loadLabel(packageManager))
            i.putExtra(Intent.EXTRA_TEXT, getString(R.string.advice_view_share_body))
            startActivity(Intent.createChooser(i, getString(R.string.advice_view_share_select)))
        }

        // TODO: Add scroll listener.
        /* if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            scrollView = findViewById(R.id.advice_view_scroll)
            scrollView.
        } */

        val progressBar: ProgressBar = findViewById(R.id.advice_view_progress)
        progressBar.max = 100

        scrollView = findViewById(R.id.advice_view_scroll)
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollViewHeight = scrollView.getChildAt(0).bottom.toDouble() - scrollView.height
            val getScrollY = scrollView.scrollY
            val scrollPosition = (getScrollY / scrollViewHeight) * 100
            progressBar.progress = scrollPosition.toInt()
        }
    }
}
