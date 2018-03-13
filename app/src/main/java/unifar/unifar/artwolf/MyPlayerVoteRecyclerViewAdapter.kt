package unifar.unifar.artwolf

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton

import unifar.unifar.artwolf.PlayerVoteFragment.OnPlayerVoteFragmentFinishListener
import unifar.unifar.artwolf.dummy.DummyContent.DummyItem
import kotlin.CharSequence

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnPlayerVoteFragmentFinishListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyPlayerVoteRecyclerViewAdapter(private val playerNames: ArrayList<CharSequence>) : RecyclerView.Adapter<MyPlayerVoteRecyclerViewAdapter.ViewHolder>() {

    var lastSelectedPosition = -1
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_playervote, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.radioButton?.isChecked = (position == lastSelectedPosition)
        holder.radioButton?.text = playerNames[position]
    }

    override fun getItemCount(): Int {
        return playerNames.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        var radioButton: RadioButton? = mView.findViewById(R.id.player_vote_fragment_radio_button)
        init {
            radioButton?.setOnClickListener {
                lastSelectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }

    }
}
