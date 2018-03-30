package unifar.unifar.artwolf

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import com.google.android.gms.ads.AdView


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SettingsFragment.OnSettingsFragmentFinishListener] interface
 * to handle interaction events.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SettingsFragment : Fragment() {
    private var listener: OnSettingsFragmentFinishListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val banner = view.findViewById<AdView>(R.id.fragment_settings_banner)
        banner.loadAd(MyApplication.adRequest)
        val sharedPreferences = context.getSharedPreferences(Keys.IsUsingSEKey.toString(), Context.MODE_PRIVATE)
        val seSwitch = view.findViewById<Switch>(R.id.fragment_settings_se_switch)
        seSwitch.isChecked = sharedPreferences.getBoolean(Keys.IsUsingSEKey.toString(), true)
        seSwitch.setOnCheckedChangeListener({
            _, isChecked -> sharedPreferences.edit().putBoolean(Keys.IsUsingSEKey.toString(), isChecked).apply()
        })
        val bgmSwitch = view.findViewById<Switch>(R.id.fragment_settings_bgm_switch)
        bgmSwitch.isChecked = sharedPreferences.getBoolean(Keys.IsUsingBGMKey.toString(), true)
        bgmSwitch.setOnCheckedChangeListener({
            _, isChecked -> sharedPreferences.edit().putBoolean(Keys.IsUsingBGMKey.toString(), isChecked).apply()
        })
        view.findViewById<Button>(R.id.fragment_settings_finish_button).setOnClickListener { onButtonPressed() }
        return view
    }

    fun onButtonPressed() {
        listener?.onSettingsFragmentFinish()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSettingsFragmentFinishListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnSettingsFragmentFinishListener")
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
    interface OnSettingsFragmentFinishListener {
        fun onSettingsFragmentFinish()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment SettingsFragment.
         */
        @JvmStatic
        fun newInstance() =
                SettingsFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}
