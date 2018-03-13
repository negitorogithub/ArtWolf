package unifar.unifar.artwolf

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import unifar.unifar.artwolf.ApplicationContextHolder.Companion.context

import unifar.unifar.artwolf.PlayerListFragment.OnPlayerInfoDecidedListener
import kotlin.CharSequence

/**
 * [RecyclerView.Adapter] that can display a [CharSequence] and makes a call to the
 * specified [OnPlayerInfoDecidedListener].
 */
class MyIPlayerRecyclerViewAdapter(val allNames: MutableList<CharSequence>) : RecyclerView.Adapter<MyIPlayerRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return allNames.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_iplayer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = allNames[position]
        holder.myTextWatcher.updatePosition(position)
        holder.editNameView.text = SpannableStringBuilder(holder.mItem.toString())
    }

inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

    val myTextWatcher = MyTextWatcher()
    var editNameView: EditText = mView.findViewById(R.id.iplayer_info_decide_editText)
    var mItem: CharSequence? = null
    init {
        editNameView.addTextChangedListener(myTextWatcher)
    }

}

inner class MyTextWatcher : TextWatcher{
    private var position = -1
    fun updatePosition(position: Int) {
        this.position = position
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        allNames[position] = s ?: "Default"
        Log.d("artWolf", allNames[position].toString())
    }


}
}

