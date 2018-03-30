package unifar.unifar.artwolf

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.gms.ads.AdView


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TitleFragment.OnTitleFragmentStartListener] interface
 * to handle interaction events.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TitleFragment : Fragment() {
    private var startListener: OnTitleFragmentStartListener? = null
    private var settingsListener: OnTitleFragmentSettingsListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_title, container, false)
        val startButton = view.findViewById<Button>(R.id.title_fragment_start_button)
        startButton.setOnClickListener { onStartButtonPressed() }
        val settingsButton = view.findViewById<Button>(R.id.title_fragment_settings_button)
        settingsButton.setOnClickListener { onSettingsButtonPressed() }
        val banner = view.findViewById<AdView>(R.id.title_fragment_banner)
        banner.loadAd(MyApplication.adRequest)
        return view
    }

    private fun onStartButtonPressed() {
        startListener?.onTitleFragmentStart()
    }


    private fun onSettingsButtonPressed() {
        settingsListener?.onTitleFragmentSettings()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnTitleFragmentStartListener) {
            startListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnTitleFragmentStartListener")
        }

        if (context is OnTitleFragmentSettingsListener) {
            settingsListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnTitleFragmentSettingsListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        startListener = null
        settingsListener = null
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
    interface OnTitleFragmentStartListener {
        fun onTitleFragmentStart()
    }

    interface OnTitleFragmentSettingsListener {
        fun onTitleFragmentSettings()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TitleFragment.
         */
        @JvmStatic
        fun newInstance() =
                TitleFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}

