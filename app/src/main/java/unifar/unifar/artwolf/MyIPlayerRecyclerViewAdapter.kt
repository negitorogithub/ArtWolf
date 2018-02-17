package unifar.unifar.artwolf

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import unifar.unifar.artwolf.PlayerListFragment.OnPlayerInfoDecidedListener
import unifar.unifar.artwolf.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [IPlayer] and makes a call to the
 * specified [OnPlayerInfoDecidedListener].
 */
class MyIPlayerRecyclerViewAdapter(val IPlayers: MutableList<IPlayer>) : RecyclerView.Adapter<MyIPlayerRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return IPlayers.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_iplayer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = IPlayers[position]
        holder.myTextWatcher.updatePosition(position)
        holder.editNameView.text = SpannableStringBuilder(holder.mItem?.name_)
    }

inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

    val myTextWatcher = MyTextWatcher()
    var editNameView: EditText = mView.findViewById(R.id.iplayer_info_decide_editText)
    var mItem: IPlayer? = null
    init {
        editNameView.addTextChangedListener(myTextWatcher)
    }

}

inner class MyTextWatcher : TextWatcher{
    var position = -1
    fun updatePosition(position: Int) {
        this.position = position
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        IPlayers[position].name_ = s ?: "Default"
    }


}
}
