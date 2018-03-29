package unifar.unifar.artwolf

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RetryFragment.OnRetryFragmentRetryListener] interface
 * to handle interaction events.
 * Use the [RetryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RetryFragment : Fragment() {


    private var retryFragmentRetryListener: OnRetryFragmentRetryListener? = null
    private var retryFragmentFinishListener: OnRetryFragmentFinishListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_retry, container, false)
        val banner = view.findViewById<AdView>(R.id.fragment_retry_banner)
        banner.loadAd(MyApplication.adRequest)
        val finishButton = view.findViewById<Button>(R.id.fragment_retry_finish_button)
        finishButton.setOnClickListener{
            onFinishButtonPressed()
        }
        val retryButton = view.findViewById<Button>(R.id.fragment_retry_retry_button)
        retryButton.setOnClickListener {
            onRetryButtonPressed()
        }

        return view
    }


    private fun onRetryButtonPressed() {
        if (retryFragmentRetryListener != null) {
            retryFragmentRetryListener!!.onRetryFragmentRetry()
        }
    }

    private fun onFinishButtonPressed(){
        if (retryFragmentFinishListener != null) {
            retryFragmentFinishListener!!.onRetryFragmentFinish()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnRetryFragmentRetryListener) {
            retryFragmentRetryListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnRetryFragmentRetryListener")
        }

        if (context is OnRetryFragmentFinishListener) {
            retryFragmentFinishListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnRetryFragmentFinishListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        retryFragmentRetryListener = null
        retryFragmentFinishListener = null

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
    interface OnRetryFragmentRetryListener {
        fun onRetryFragmentRetry()
    }

    interface OnRetryFragmentFinishListener {
        fun onRetryFragmentFinish()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RetryFragment.
         */
        fun newInstance(): RetryFragment {
            val fragment = RetryFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
