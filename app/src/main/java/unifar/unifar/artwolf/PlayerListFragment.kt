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

import unifar.unifar.artwolf.dummy.DummyContent
import unifar.unifar.artwolf.dummy.DummyContent.DummyItem

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [PlayerListFragment.OnPlayerInfoDecidedListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class PlayerListFragment : Fragment() {
    // TODO: Customize parameters
    private var mColumnCount = 1
    private var mListener: OnPlayerInfoDecidedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_iplayer_list, container, false)

        // Set the adapter

        val context = view.context
        val recyclerView = view.findViewById<RecyclerView>(R.id.iplayer_list)
        if (mColumnCount <= 1) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, mColumnCount)
        }
        if (context is IGameDataContain) {
            recyclerView.adapter = MyIPlayerRecyclerViewAdapter(context.gameData.allPlayers.toMutableList())
        } else {
            throw NotImplementedError("To use this fragment, you must implement IGameDataContain")
        }

        val players = (recyclerView.adapter as MyIPlayerRecyclerViewAdapter).IPlayers
        val finishButton = view.findViewById<Button>(R.id.iplayer_list_finish_button)
        finishButton.setOnClickListener {
            fragmentManager.beginTransaction().remove(this).commit()
            mListener?.onPlayerInfoDecided(players)
        }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnPlayerInfoDecidedListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnPlayerInfoDecidedListener")
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
    interface OnPlayerInfoDecidedListener {
        // TODO: Update argument type and name
        fun onPlayerInfoDecided(IPlayers : MutableCollection<IPlayer>)
    }

    companion object {

        // TODO: Customize parameter argument names
        private const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): PlayerListFragment {
            val fragment = PlayerListFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
