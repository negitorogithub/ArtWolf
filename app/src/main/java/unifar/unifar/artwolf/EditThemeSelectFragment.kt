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
 * [EditThemeSelectFragment.OnEditThemeSelectFragmentEditListener] interface
 * to handle interaction events.
 * Use the [EditThemeSelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditThemeSelectFragment : Fragment() {
 
    private var editThemeSelectFragmentEditListener: OnEditThemeSelectFragmentEditListener? = null
    private var editThemeSelectFragmentRandomListener: OnEditThemeSelectFragmentRandomListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_edit_theme_select, container, false)
        val editButton = view.findViewById<Button>(R.id.edit_theme_select_edit)
        editButton.setOnClickListener { onEditButtonPressed() }
        val randomButton = view.findViewById<Button>(R.id.edit_theme_select_random)
        randomButton.setOnClickListener{ onRondomButtonPressed()}
        return view
    }

    private fun onEditButtonPressed() {
        if (editThemeSelectFragmentEditListener != null) {
            editThemeSelectFragmentEditListener!!.onEditThemeSelectFragmentEdit()
        }
    }

    private fun onRondomButtonPressed() {
        if (editThemeSelectFragmentRandomListener != null) {
            editThemeSelectFragmentRandomListener!!.onEditThemeSelectFragmentRandom()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnEditThemeSelectFragmentEditListener) {
            editThemeSelectFragmentEditListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnEditThemeSelectFragmentEditListener")
        }


        if (context is OnEditThemeSelectFragmentRandomListener) {
            editThemeSelectFragmentRandomListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnEditThemeSelectFragmentRandomListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        editThemeSelectFragmentEditListener = null
        editThemeSelectFragmentRandomListener = null

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
    interface OnEditThemeSelectFragmentEditListener {
        fun onEditThemeSelectFragmentEdit()
    }

    interface OnEditThemeSelectFragmentRandomListener {
        fun onEditThemeSelectFragmentRandom()
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditThemeSelectFragment.
         */
        fun newInstance(): EditThemeSelectFragment {
            val fragment = EditThemeSelectFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
