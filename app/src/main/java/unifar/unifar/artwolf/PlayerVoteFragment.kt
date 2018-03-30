package unifar.unifar.artwolf

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlin.CharSequence

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class PlayerVoteFragment : Fragment() {
    // TODO: Customize parameters
    private var mColumnCount = 1
    private lateinit var playerNames: ArrayList<CharSequence>
    private var mListener: OnPlayerVoteFragmentFinishListener? = null
    private var mLastSelectedPosition = -1
    private var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
            playerNames = arguments.getCharSequenceArrayList(PLAYER_NAME_COLLECTION_KEY)
        }
        savedInstanceState?.let {mLastSelectedPosition = savedInstanceState.getInt(LAST_SELECTED_POSITION_KEY) }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_playervote_list, container, false)

        recyclerView = view.findViewById(R.id.fragment_player_vote_recycler_view)
        if (mColumnCount <= 1) {
            recyclerView?.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView?.layoutManager = GridLayoutManager(context, mColumnCount)
        }
        recyclerView?.adapter = MyPlayerVoteRecyclerViewAdapter(playerNames).apply {
            lastSelectedPosition = mLastSelectedPosition
        }
        val finishButton = view.findViewById<Button>(R.id.fragment_player_vote_finish_button)
        finishButton.setOnClickListener{
            if((recyclerView?.adapter as MyPlayerVoteRecyclerViewAdapter).lastSelectedPosition > -1) {
                mListener?.onPlayerVoteFragmentFinishListener((recyclerView?.adapter as MyPlayerVoteRecyclerViewAdapter).lastSelectedPosition)
            }else{
                Toast.makeText(activity, getString(R.string.you_must_choose_person), Toast.LENGTH_LONG).show()
            }

        }

        return view
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(LAST_SELECTED_POSITION_KEY,(recyclerView?.adapter as MyPlayerVoteRecyclerViewAdapter).lastSelectedPosition)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnPlayerVoteFragmentFinishListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnPlayerVoteFragmentFinishListener")
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
    interface OnPlayerVoteFragmentFinishListener {
        fun onPlayerVoteFragmentFinishListener(position: Int)
    }

    companion object {

        private const val ARG_COLUMN_COUNT = "column-count"
        private const val PLAYER_NAME_COLLECTION_KEY = "playerNameCollectionKey"
        private const val LAST_SELECTED_POSITION_KEY = "lastSelectedPositionKey"
        fun newInstance(columnCount: Int, playerNames : ArrayList<CharSequence>): PlayerVoteFragment {
            val fragment = PlayerVoteFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            args.putCharSequenceArrayList(PLAYER_NAME_COLLECTION_KEY, playerNames)
            fragment.arguments = args
            return fragment
        }
    }
}
