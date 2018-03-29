package unifar.unifar.artwolf

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TallkTimeFragment.OnTalkTimeFragmentFinishListener] interface
 * to handle interaction events.
 * Use the [TallkTimeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TallkTimeFragment : Fragment() {
    private var listener: OnTalkTimeFragmentFinishListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tallk_time, container, false)
        val finishButton = view.findViewById<Button>(R.id.talk_time_fragment_finish_button)
        finishButton.setOnClickListener {
            onButtonPressed()
            fragmentManager.beginTransaction().remove(this).commit()
        }
        return view
    }

    private fun onButtonPressed() {
        listener?.onTalkTimeFragmentFinish()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnTalkTimeFragmentFinishListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnTalkTimeFragmentFinishListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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
    interface OnTalkTimeFragmentFinishListener {
        fun onTalkTimeFragmentFinish()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TallkTimeFragment.
         */
        @JvmStatic
        fun newInstance() =
                TallkTimeFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}

