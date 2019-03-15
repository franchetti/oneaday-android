package gq.emiliodallatorre.oneaday.iterator.experience

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bikomobile.donutprogress.DonutProgress
import gq.emiliodallatorre.oneaday.iterator.R
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Element
import java.io.BufferedInputStream
import java.lang.StringBuilder
import java.net.URL
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {return inflater.inflate(R.layout.fragment_main, container, false) }

    override fun onStart() {
        super.onStart()
        // TODO: Setup things for lastDay of path.
        val donutProgress: DonutProgress = (context as Activity).findViewById(R.id.progressBar)
        val dayOfPath: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - PreferenceManager.getDefaultSharedPreferences(context).getInt("startDay", 1)
        ((context as Activity).findViewById<TextView>(R.id.advice_title)).text = resources.getStringArray(R.array.advicesTitle)[dayOfPath]
        ((context as Activity).findViewById<TextView>(R.id.advice_subtitle)).text = resources.getStringArray(R.array.advicesSubtitle)[dayOfPath]
        donutProgress.text = dayOfPath.toString()
        donutProgress.max = 28 * 10
        val progressText: String = getString(R.string.main_day) + " " + (dayOfPath + 1) + " " + getString(R.string.main_of) + " 28"
        ((context as Activity).findViewById<TextView>(R.id.progressText)).text = progressText

        newsFeed.isSelected = true
        GlobalScope.launch {
            Looper.prepare()
            try {
                val s: String = parseFeed()
                (context as Activity).runOnUiThread {
                    newsFeed.text = s
                    newsFeed.isAllCaps = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
                newsFeed.text = getString(R.string.comm_error)
            }
        }

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

    private fun parseFeed(): String {
        val rssLink = "https://climatenewsnetwork.net/feed"
        val toBeParsed = 5
        val result = StringBuilder()
        val itemList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(BufferedInputStream(URL(rssLink).openConnection().inputStream)).getElementsByTagName("item")

        for (i in 0 until toBeParsed) {
            // Get element of RSS stream and append it to result.
            val element = itemList.item(i) as Element
            result.append(element.getElementsByTagName("title").item(0).firstChild.nodeValue)
            if(i != toBeParsed) result.append("  -  ")
        }

        return result.toString()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MainFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
