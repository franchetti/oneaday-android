package gq.emiliodallatorre.oneaday.iterator.experience

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import gq.emiliodallatorre.oneaday.iterator.R
import java.util.*

class DashboardFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var adviceList = ArrayList<AdviceModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Improve the look of dashboard.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        object : Thread() {
            override fun run() {
                try {
                    adviceList = DashboardLoader().load(context)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onStart() {
        super.onStart()

        (context as Activity).findViewById<ListView>(R.id.progressList).adapter = CustomAdapter(context as Activity, adviceList)
    }

    override fun onResume() {
        super.onResume()
        val listView: ListView = (context as Activity).findViewById(R.id.progressList)
        object : Thread() {
            override fun run() {
                try {
                    for (i in 0 until adviceList.size) {
                        if (!adviceList[i].bar) {
                            listView.post {
                                listView.smoothScrollToPositionFromTop(i, 4, 1000)
                            }
                            break
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
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
}