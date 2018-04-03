package unifar.unifar.artwolf

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditThemeFragment.OnEditThemeFragmentFinishListener] interface
 * to handle interaction events.
 * Use the [EditThemeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditThemeFragment : Fragment() {

    private var mFinishListener: OnEditThemeFragmentFinishListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_edit_theme, container, false)
        val editText = view.findViewById<TextInputEditText>(R.id.fragment_edit_theme_edit_Text)
        val finishButton = view.findViewById<Button>(R.id.fragment_edit_theme_finish_button)
        finishButton.setOnClickListener{
            onFinishButtonPressed(editText.text)
            MainActivity.playSe(context)
        }
        return view
    }

    private fun onFinishButtonPressed(theme: CharSequence?) {
        if (theme.toString() == ""){
            Toast.makeText(activity, getString(R.string.please_fill_in_theme_field), Toast.LENGTH_SHORT).show()
        } else{
             mFinishListener?.onEditThemeFragmentFinish(theme.toString())
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnEditThemeFragmentFinishListener) {
            mFinishListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnEditThemeFragmentFinishListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mFinishListener = null
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
    interface OnEditThemeFragmentFinishListener {
        fun onEditThemeFragmentFinish(theme: String)
    }

    companion object {
        /***
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditThemeFragment.
         */
        fun newInstance(): EditThemeFragment {
            val fragment = EditThemeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
