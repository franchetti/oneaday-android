package gq.emiliodallatorre.oneaday.iterator

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import java.util.*


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
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val adviceList = ArrayList<AdviceModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val currentMonth: Int = Calendar.getInstance().get(Calendar.MONTH)
        val currentDay: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val startDay: Int = PreferenceManager.getDefaultSharedPreferences(context).getInt("startDay", 1)
        val startMonth: Int = PreferenceManager.getDefaultSharedPreferences(context).getInt("startMonth", 1)

        var f = 0
        var g = 0
        for (i in 0 until 27) {
            val adviceModel = AdviceModel()
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
                10 -> g= 31
                11 -> g = 30
                12 -> g = 31
            }

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

            adviceModel.title = resources.getStringArray(R.array.advicesTitle)[i]
            adviceModel.subtitle = resources.getStringArray(R.array.advicesSubtitle)[i]
            adviceList.add(adviceModel)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onStart() {
        super.onStart()

        activity!!.findViewById<ListView>(R.id.progressList).adapter = CustomAdapter(activity!!, adviceList)
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