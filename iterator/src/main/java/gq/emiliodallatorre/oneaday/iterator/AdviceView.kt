package gq.emiliodallatorre.oneaday.iterator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class AdviceView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advice_view)
        // TODO: Implement a view for the advice, and add according links.
        print(intent.getStringExtra("dayOfPath"))
        findViewById<TextView>(R.id.advice_view_title).text = intent.getStringExtra("advice")
        findViewById<TextView>(R.id.advice_view_body).text = resources.getStringArray(R.array.advicesBody)[Integer.parseInt(intent.getStringExtra("dayOfPath"))]
        findViewById<TextView>(R.id.advice_view_body).text = resources.getStringArray(R.array.advicesBody)[0]
    }
}
