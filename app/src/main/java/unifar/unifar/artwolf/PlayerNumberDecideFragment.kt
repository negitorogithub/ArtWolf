package unifar.unifar.artwolf

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PlayerNumberDecideFragment.playerNumberReceiver] interface
 * to handle interaction events.
 */
class PlayerNumberDecideFragment : Fragment() {
    private var playerNumberReceiver: PlayerNumberReceiver? = null
    private var playerNumber:Int = Companion.MIN_PLAYER_NUMBER
    private lateinit var finishButton: View
    private lateinit var playerNumberPicker: NumberPicker

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_player_number_decide, container, false)
        finishButton = view.findViewById(R.id.decisingFinishButton)
        finishButton.setOnClickListener { _ ->
            playerNumberReceiver?.onValueDecided(playerNumber)
            fragmentManager.beginTransaction().remove(this).commit()
        }

        playerNumberPicker = view.findViewById(R.id.playerNumberPicker)
        playerNumberPicker.minValue = MIN_PLAYER_NUMBER
        playerNumberPicker.maxValue = MAX_PLAYER_NUMBER
        playerNumberPicker.setOnValueChangedListener { _, _, newVal -> playerNumber = newVal }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is PlayerNumberReceiver) {
            playerNumberReceiver = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement playerNumberReceiver")
        }
    }

    override fun onDetach() {
        super.onDetach()
        playerNumberReceiver = null
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
    interface PlayerNumberReceiver {
        fun onValueDecided(playerNumber: Int)
    }

    companion object {
        //三人居ないとゲームが成立しない

        private const val MIN_PLAYER_NUMBER = 3
        //色に余裕を持って15
        private const val MAX_PLAYER_NUMBER = 15

        fun newInstance(): PlayerNumberDecideFragment {
            val fragment = PlayerNumberDecideFragment()
            val args = Bundle()
            //args.putString(ARG_PARAM1, param1)
            //args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
