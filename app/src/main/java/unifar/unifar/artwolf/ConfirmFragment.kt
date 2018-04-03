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
 * [ConfirmFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ConfirmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmFragment : Fragment() {

    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            name = arguments.getString(NAME_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.confirm_player, container, false)
        val nameTextView = view.findViewById<TextView>(R.id.fragment_confirm_player_name)
        nameTextView.text = getString(R.string.are_you, name)
        val finishButton = view.findViewById<Button>(R.id.fragment_confirm_player_finish_button)
        finishButton.setOnClickListener{
            onButtonPressed()
            MainActivity.playSe(context)}
        return view
    }


    private var onFinishListener: OnFragmentInteractionListener? = null

    private fun onButtonPressed() {
        if (onFinishListener != null) {
            onFinishListener!!.onConfirmFragmentFinish(tag)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            onFinishListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnCanvasFinishListener")
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
    interface OnFragmentInteractionListener {
        fun onConfirmFragmentFinish(tag: String)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val NAME_KEY = "NameKey"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param name The name to confirm in this fragment.
         * @return A new instance of fragment ConfirmFragment.
         */
        fun newInstance(name: String): ConfirmFragment {
            val fragment = ConfirmFragment()
            val args = Bundle()
            args.putString(NAME_KEY, name)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
