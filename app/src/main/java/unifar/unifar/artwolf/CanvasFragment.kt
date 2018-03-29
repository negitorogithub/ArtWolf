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
class CanvasFragment : Fragment(), IServeITrajectories, ICanvasFragmentWidgets, PaintView.CanReDoListener, PaintView.CanUnDoListener{


    override var paintView: PaintView? = null
    override var undoWidget: View? = null
    override var redoWidget: View? = null
    override var nextColorWidget: View? = null

    private var mParam2: String? = null

    private var playerNames: List<CharSequence> = listOf("")
    private var nextIPlayerIndex = 1
    private var currentIPlayerIndex = 0

    private var colorKinds: Int = -1
    private var mListener: OnCanvasFinishListener? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                if (arguments != null) {
                    playerNames = arguments.getCharSequenceArray(IPLAYERS_KEY).toList()
                }
        savedInstanceState?.let { nextIPlayerIndex = savedInstanceState.getInt(NEXT_PLAYER_INDEX_KEY) }
        savedInstanceState?.let { colorKinds = savedInstanceState.getInt(COLOR_KINDS_KEY) }
        savedInstanceState?.let { playerNames = savedInstanceState.getCharSequenceArray(IPLAYERS_KEY).toList() }
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
        for (i in 1..
        when(nextIPlayerIndex == 0){
            true -> colorKinds-1
            false-> nextIPlayerIndex - 1
        }){
            paintView?.changeColorToNext()
        }


        paintView?.currentPaint?.color?.let { nextColorWidget?.setBackgroundColor(it) }
        undoWidget?.setOnClickListener { _ -> paintView?.undo() }
        redoWidget?.setOnClickListener {_ -> paintView?.redo() }
        nextColorWidget?.setOnClickListener { thisWidget ->
            if (playerNames.size -1 > nextIPlayerIndex) {
                nextIPlayerIndex++
            } else{
                nextIPlayerIndex = 0
            }

            if (currentIPlayerIndex < playerNames.size - 1 ) {
                currentIPlayerIndex++
            } else{
                currentIPlayerIndex = 0
            }
            (nextColorWidget as Button?)?.text = getString(R.string.is_drawing, playerNames.elementAt(currentIPlayerIndex))
            paintView?.changeColorToNext()
            paintView?.currentPaint?.color?.let { thisWidget.setBackgroundColor(it) }
        }
        val finishButton = thisView.findViewById<ImageView>(R.id.canvas_fragment_finish_button)
        finishButton.setOnClickListener{
            fragmentManager.beginTransaction().detach(this).commit()
            onFinishButtonPressed()
        }
        //初期設定
        (nextColorWidget as Button?)?.text = getString(R.string.is_drawing, playerNames.elementAt(currentIPlayerIndex))
        paintView?.currentPaint?.color?.let { nextColorWidget?.setBackgroundColor(it) }
        onNotifyCanRedo(false)
        onNotifyCanUndo(false)
        return thisView
    }

    //変更をリアルタイムで可視性に反映
    override fun onNotifyCanRedo(canRedo: Boolean) {
        if (canRedo){
            redoWidget?.visibility = View.VISIBLE
        }
        else{
            redoWidget?.visibility = View.INVISIBLE
        }

    }

    override fun onNotifyCanUndo(canUndo: Boolean) {
        if (canUndo){
            undoWidget?.visibility = View.VISIBLE
        }
        else{
            undoWidget?.visibility = View.INVISIBLE
        }
    }
    private fun onFinishButtonPressed() {
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
        } else {
            throw NotImplementedError(context.toString() + " must implement IGameDataContain")
        }
    }



    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(NEXT_PLAYER_INDEX_KEY, nextIPlayerIndex)
        outState?.putInt(COLOR_KINDS_KEY, colorKinds)
        outState?.putCharSequenceArray(IPLAYERS_KEY, playerNames.toTypedArray())
    }

    override fun onDetach() {
        super.onDetach()

        mListener = null
    }

    private lateinit var trajectories: Collection<ITrajectory>

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
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val NEXT_PLAYER_INDEX_KEY = "nextPlayerIndexKey"
        private const val COLOR_KINDS_KEY = "colorKindsKey"
        private const val IPLAYERS_KEY = "iPlayersKey"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CanvasFragment.
         */
        fun newInstance(playerNames: Array<CharSequence>): CanvasFragment {
            val fragment = CanvasFragment()
            val args = Bundle()
            args.putCharSequenceArray(IPLAYERS_KEY, playerNames)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
