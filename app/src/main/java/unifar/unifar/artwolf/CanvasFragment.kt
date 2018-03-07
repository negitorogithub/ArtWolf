package unifar.unifar.artwolf

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CanvasFragment.OnCanvasFinishListener] interface
 * to handle interaction events.
 * Activities that contain this fragment must implement the
 * [IGameDataContain] interface
 * to reflect gameData to this fragment.
 * Use the [CanvasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CanvasFragment : Fragment(), IServeITrajectories, ICanvasFragmentWidgets{
    override var paintView: PaintView? = null
    override var undoWidget: View? = null
    override var redoWidget: View? = null
    override var nextColorWidget: View? = null

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private lateinit var iPlayers: MutableCollection<IPlayer>
    private var nextIPlayerIndex = 1
    private var colorKinds: Int = -1
    private var mListener: OnCanvasFinishListener? = null

    private var trajectories: Collection<ITrajectory>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment

        val thisView = inflater!!.inflate(R.layout.fragment_canvas, container, false)

        undoWidget = thisView.findViewById(R.id.undoButton)
        redoWidget = thisView.findViewById(R.id.redoButton)
        nextColorWidget = thisView.findViewById<Button?>(R.id.nextPlayerButton)
        paintView = thisView.findViewById(R.id.paintView)
        paintView?.colorKinds = colorKinds
        paintView?.currentPaint?.color?.let { nextColorWidget?.setBackgroundColor(it) }
        undoWidget?.setOnClickListener { _ -> paintView?.undo() }
        redoWidget?.setOnClickListener {_ -> paintView?.redo() }
        nextColorWidget?.setOnClickListener { thisWidget ->
            if (iPlayers.size -1 > nextIPlayerIndex) {
                nextIPlayerIndex++
            } else{
                nextIPlayerIndex = 0
            }
            (nextColorWidget as Button?)?.text = getString(R.string.change_to, iPlayers.elementAt(nextIPlayerIndex).name_)
            paintView?.changeColorToNext()
            paintView?.currentPaint?.color?.let { thisWidget.setBackgroundColor(it) }
        }
        val finishButton = thisView.findViewById<ImageView>(R.id.canvas_fragment_finish_button)
        finishButton.setOnClickListener{
            fragmentManager.beginTransaction().detach(this).commit()
            onFinishButtonPressed()
        }
        //初期設定
        (nextColorWidget as Button?)?.text = getString(R.string.change_to, iPlayers.elementAt(nextIPlayerIndex).name_)
        paintView?.currentPaint?.color?.let { nextColorWidget?.setBackgroundColor(it) }
        return thisView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onFinishButtonPressed() {
        if (mListener != null) {
            mListener!!.onCanvasFragmentFinish()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnCanvasFinishListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement playerNumberReceiver")
        }

        if (context is IGameDataContain){
            colorKinds = context.gameData.playerCount
            iPlayers = context.gameData.allPlayers
        } else {
            throw NotImplementedError(context.toString() + " must implement IGameDataContain")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onITrajectoriesHistoryIssued(trajectories: Collection<ITrajectory>) {
        this.trajectories = trajectories
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
    interface OnCanvasFinishListener {
        fun onCanvasFragmentFinish()
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CanvasFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): CanvasFragment {
            val fragment = CanvasFragment()
            val args = Bundle()
            //args.putString(ARG_PARAM1, param1)
            //args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
