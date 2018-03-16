package unifar.unifar.artwolf

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ResultFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment(){

    private lateinit var act: Acts
    private var theme: String = "Default"
    private var isReversed = false
    private var mListener: OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            act = arguments.getSerializable(ACT_KEY) as Acts
        }
        savedInstanceState?.let{isReversed = savedInstanceState.getBoolean(IS_REVERSED_KEY)}
        savedInstanceState?.let{theme = savedInstanceState.getString(THEME_KEY)}

    }



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_result, container, false)

        val resultActTextView = view.findViewById<TextView>(R.id.resultFragmentActText)
        resultActTextView.text = getString(R.string.winnerAct, act.toString(resources))
        val isReversedCheckBox = view.findViewById<CheckBox>(R.id.resultFragmentCheckBox).apply { isChecked = isReversed }
        val showThemeTextView = view.findViewById<TextView>(R.id.resultFragmentThemeLatterTextView)
        showThemeTextView.text = getString(R.string.themeShow, theme)


        when(act){
            Acts.Artist -> view.findViewById<ConstraintLayout>(R.id.resultFragmentArtistWonConstraintLayout).visibility = View.VISIBLE
            Acts.Wolf   -> view.findViewById<ConstraintLayout>(R.id.resultFragmentWolfWonConstraintLayout).visibility   = View.VISIBLE
        }

        val finishButton = view.findViewById<Button>(R.id.resultFragmentFinishButton)
        finishButton.setOnClickListener{
            onButtonPressed(isReversedCheckBox.isChecked)
        }
        return view
    }

    private fun onButtonPressed(isReversed: Boolean) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(act, isReversed)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(IS_REVERSED_KEY, isReversed)
        outState?.putString(THEME_KEY, theme)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnRetryFragmentRetryListener")
        }

        if (context is IGameDataContain) {
            theme = context.gameData.theme
        } else {
            throw RuntimeException(context.toString() + " must implement IGameDataContain")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
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
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(act: Acts, isReversed: Boolean)
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ACT_KEY = "actKey"
        private const val IS_REVERSED_KEY = "isReversedKey"
        private const val THEME_KEY = "themeKey"


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param act Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultFragment.
         */
        fun newInstance(act: Acts): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putSerializable(ACT_KEY, act)
            fragment.arguments = args
            return fragment
        }
    }
}
