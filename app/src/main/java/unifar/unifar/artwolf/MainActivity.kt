package unifar.unifar.artwolf

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity :
        AppCompatActivity(),
        CanvasFragment.OnCanvasFinishListener,
        PlayerNumberDecideFragment.PlayerNumberReceiver,
        PlayerListFragment.OnPlayerInfoDecidedListener,
        ShowActsFragment.OnShowActFragmentFinishListener,
        ConfirmFragment.OnFragmentInteractionListener,
        PlayerVoteFragment.OnPlayerVoteFragmentFinishListener,
        ResultFragment.OnFragmentInteractionListener,
        IGameDataContain {



    private val mainActivityContainerResId = R.id.main_activity_container
    override var gameData: IGameData = GameData()
    private val fragmentManager = supportFragmentManager

    companion object {
        private const val PLAYER_VOTE_TAG = "playerVoteTag"
        private const val SHOW_ACT_TAG = "showActTag"
    }

    init {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: fragmentManager.beginTransaction().replace(mainActivityContainerResId, PlayerNumberDecideFragment.newInstance()).commit()
        gameData.theme = (resources.getStringArray(R.array.builtInThemes)).toList().shuffled()[0]
    }

    override fun onValueDecided(playerNumber: Int) {
        if (gameData.allPlayers.isEmpty()){
            for (i in 1..playerNumber){
                gameData.allPlayers.add(Player())
            }
        }
        gameData.playerCount = gameData.allPlayers.size

        fragmentManager.beginTransaction().replace(
                mainActivityContainerResId,
                PlayerListFragment.newInstance(1)
        ).commit()

    }

    private var showActIndex: Int = 0

    override fun onPlayerInfoDecided(IPlayers: MutableCollection<IPlayer>) {
        gameData.allPlayers = IPlayers
        val actsList = mutableListOf(Acts.Wolf)
        if (gameData.allPlayers.size >= 3){
            for (i in 2..gameData.allPlayers.size){
                actsList.add(Acts.Artist)
            }
        }
        actsList.shuffle()
        for (acts in actsList.withIndex()) {
            gameData.allPlayers.elementAt(acts.index).act = actsList.elementAt(acts.index)
        }
        showActIndex = 0

        fragmentManager.beginTransaction().replace(
                mainActivityContainerResId,
                ConfirmFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name_.toString()),
                SHOW_ACT_TAG
        ).commit()


    }

    private var playerVoteIndex = 0

    override fun onConfirmFragmentFinish(tag: String) {
        if (tag == SHOW_ACT_TAG) {
            fragmentManager.beginTransaction().replace(
                    mainActivityContainerResId,
                    ShowActsFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name_.toString(), gameData.allPlayers.elementAt(showActIndex).act, gameData.theme)).commit()
        }
        val playerNames = ArrayList<CharSequence>()
        gameData.allPlayers.mapTo(playerNames) { it.name_}
        if (tag == PLAYER_VOTE_TAG){
            fragmentManager.beginTransaction().replace(
                    mainActivityContainerResId,
                    PlayerVoteFragment.newInstance(1, playerNames)
            ).commit()
        }
    }

    override fun onShowActFragmentFinish() {
        showActIndex++
        if (showActIndex < gameData.allPlayers.size){
            fragmentManager.beginTransaction().replace(
                    mainActivityContainerResId,
                    ConfirmFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name_.toString()),
                    SHOW_ACT_TAG
            ).commit()
        }else{
            fragmentManager.beginTransaction().replace(mainActivityContainerResId, CanvasFragment.newInstance()).commit()
        }
    }


    override fun onCanvasFragmentFinish() {

        val playerNames = ArrayList<CharSequence>()
        gameData.allPlayers.mapTo(playerNames) { it.name_}
        fragmentManager.beginTransaction().replace(
                mainActivityContainerResId,
                ConfirmFragment.newInstance(gameData.allPlayers.elementAt(playerVoteIndex).name_.toString()),
                PLAYER_VOTE_TAG
        ).commit()
    }

    override fun onPlayerVoteFragmentFinishListener(position: Int) {
        gameData.allPlayers.elementAt(playerVoteIndex).votedTo = gameData.allPlayers.elementAt(position)
        playerVoteIndex++
        if (playerVoteIndex < gameData.allPlayers.size) {
            fragmentManager.beginTransaction().replace(
                    mainActivityContainerResId,
                    ConfirmFragment.newInstance(gameData.allPlayers.elementAt(playerVoteIndex).name_.toString()),
                    PLAYER_VOTE_TAG
            ).commit()
        }else{
            val votedPlayers = ArrayList<IPlayer>()
            gameData.allPlayers.mapTo(votedPlayers){it.votedTo}
            for (votedPlayer in votedPlayers){
                votedPlayer.votedCount++
            }
            val mostVotedPlayer = gameData.allPlayers.maxBy { it.votedCount }
            var winnerAct :Acts = Acts.Artist
            when (mostVotedPlayer?.act) {
                Acts.Artist -> winnerAct = Acts.Wolf
                Acts.Wolf -> winnerAct = Acts.Artist
            }

            fragmentManager.beginTransaction().replace(
                    mainActivityContainerResId,
                    ResultFragment.newInstance(winnerAct)
            ).commit()
        }
    }

    override fun onFragmentInteraction(act: Acts, isReversed: Boolean) {

    }

}
