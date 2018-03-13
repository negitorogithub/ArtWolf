package unifar.unifar.artwolf

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ShowActsFragment.OnShowActFragmentFinishListener] interface
 * to handle interaction events.
 * Use the [ShowActsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowActsFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var name: String? = null
    private var act: String? = null
    private var theme: String? = null

    private var onFinishListener: OnShowActFragmentFinishListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            name = arguments.getString(PLAYER_NAME_KEY)
            act = arguments.getString(ACT_KEY)
            theme = arguments.getString(THEME_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_show_acts, container, false)
        val nameView = view.findViewById<TextView>(R.id.nameTextView)
        nameView.text = getString(R.string.you_are, act)
        val finishButton = view.findViewById<Button>(R.id.fragment_show_act_finish_button)
        finishButton.setOnClickListener { onFinishButtonPressed() }
        val detailTextView = view.findViewById<TextView>(R.id.act_detail_and_theme_text)
        when (act) {
            Acts.Artist.toString(resources) ->  {
                detailTextView.text = getString(R.string.artist_detail, theme)
            }
            Acts.Wolf.toString(resources) ->  {
                detailTextView.text = getString(R.string.wolf_detail)
            }
        }
        return view
    }

    private fun onFinishButtonPressed() {
        if (onFinishListener != null) {
            onFinishListener!!.onShowActFragmentFinish()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnShowActFragmentFinishListener) {
            onFinishListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnShowActFragmentFinishListener")
        }

    }

    override fun onDetach() {
        super.onDetach()
        onFinishListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnShowActFragmentFinishListener {
        // TODO: Update argument type and name
        fun onShowActFragmentFinish()
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val PLAYER_NAME_KEY = "playerKey"
        private const val ACT_KEY = "actKey"
        private const val THEME_KEY = "themeKey"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param playerName String to show who he is.
         * @param act Parameter 2.
         * @return A new instance of fragment ShowActsFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(playerName: String, act: Acts, theme: String): ShowActsFragment {
            val fragment = ShowActsFragment()
            val args = Bundle()
            args.putString(PLAYER_NAME_KEY, playerName)
            args.putString(ACT_KEY, act.toString(ApplicationContextHolder.context.resources))
            args.putString(THEME_KEY, theme)

            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
