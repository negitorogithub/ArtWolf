package unifar.unifar.artwolf

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*


//TODO: CanvasFragmentのUndoRedoの適宜無効化
//TODO: タイトルフラグメント

class MainActivity :
        AppCompatActivity(),
        CanvasFragment.OnCanvasFinishListener,
        PlayerNumberDecideFragment.PlayerNumberReceiver,
        PlayerListFragment.OnPlayerInfoDecidedListener,
        ShowActsFragment.OnShowActFragmentFinishListener,
        ConfirmFragment.OnFragmentInteractionListener,
        PlayerVoteFragment.OnPlayerVoteFragmentFinishListener,
        ResultFragment.OnFragmentInteractionListener,
        RetryFragment.OnRetryFragmentRetryListener,
        RetryFragment.OnRetryFragmentFinishListener,
        EditThemeSelectFragment.OnEditThemeSelectFragmentEditListener,
        EditThemeSelectFragment.OnEditThemeSelectFragmentRandomListener,
        IGameDataContain {


    private val mainActivityContainerResId = R.id.main_activity_container
    override var gameData: IGameData = GameData()
    private val fragmentManager = supportFragmentManager

    companion object {
        private const val PLAYER_VOTE_TAG    = "playerVoteTag"
        private const val SHOW_ACT_TAG       = "showActTag"
        private const val GAME_DATA_KEY      = "gameDataKey"
        private const val SHOW_ACT_INDEX_KEY = "showActIndexKey"
        private const val PLAYER_VOTE_KEY    = "playerVoteKey"
    }

    init {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        setContentView(R.layout.activity_main)
        savedInstanceState ?: fragmentManager.beginTransaction().replace(mainActivityContainerResId, EditThemeSelectFragment.newInstance()).commit()
        savedInstanceState?.let { gameData = savedInstanceState.getSerializable(GAME_DATA_KEY) as IGameData}
        savedInstanceState?.let { showActIndex = savedInstanceState.getInt(SHOW_ACT_INDEX_KEY) }
        savedInstanceState?.let { playerVoteIndex = savedInstanceState.getInt(PLAYER_VOTE_KEY) }

    }



    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(GAME_DATA_KEY, gameData)
        outState?.putInt(SHOW_ACT_INDEX_KEY, showActIndex)
        outState?.putInt(PLAYER_VOTE_KEY, playerVoteIndex)
    }



    override fun onEditThemeSelectFragmentEdit() {



    }

    override fun onEditThemeSelectFragmentRandom() {
        gameData.theme = (resources.getStringArray(R.array.builtInThemes)).toList().shuffled()[0]
        fragmentManager.beginTransaction().replace(
                mainActivityContainerResId,
                PlayerNumberDecideFragment.newInstance()
        ).commit()
    }

    override fun onValueDecided(playerNumber: Int) {
        if (gameData.allPlayers.isEmpty()){
            for (i in 1..playerNumber){
                gameData.allPlayers.add(Player().apply { name = getString(R.string.player) + i.toString() })
            }
        }
        gameData.playerCount = gameData.allPlayers.size

        fragmentManager.beginTransaction().replace(
                mainActivityContainerResId,
                PlayerListFragment.newInstance(1, gameData.allPlayers.map { it.name.toString() }.toMutableList())
        ).commit()

    }

    private var showActIndex: Int = 0

    override fun onPlayerInfoDecided(allNames: Collection<CharSequence>) {
        gameData.allPlayers.forEachIndexed {
            index, iPlayer -> iPlayer.name = allNames.elementAt(index) }
        gameData.selectWolf()
        showActIndex = 0
        fragmentManager.beginTransaction().replace(
                mainActivityContainerResId,
                ConfirmFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name.toString()),
                SHOW_ACT_TAG
        ).commit()


    }

    private var playerVoteIndex = 0

    override fun onConfirmFragmentFinish(tag: String) {
        if (tag == ""){
            throw RuntimeException("Please start fragment with tag ")
        }
        if (tag == SHOW_ACT_TAG) {
            fragmentManager.beginTransaction().replace(
                    mainActivityContainerResId,
                    ShowActsFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name.toString(), gameData.allPlayers.elementAt(showActIndex).act, gameData.theme)).commit()
        }

        val playerNames = ArrayList<kotlin.CharSequence>()
        gameData.allPlayers.mapTo(playerNames) { it.name}
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
                    ConfirmFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name.toString()),
                    SHOW_ACT_TAG
            ).commit()
        }else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            fragmentManager.beginTransaction().replace(
                    mainActivityContainerResId,
                    CanvasFragment.newInstance(gameData.allPlayers.map { it.name}.toTypedArray())
            ).commit()
        }
    }


    override fun onCanvasFragmentFinish() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        val playerNames = ArrayList<kotlin.CharSequence>()
        gameData.allPlayers.mapTo(playerNames) { it.name}
        fragmentManager.beginTransaction().replace(
                mainActivityContainerResId,
                ConfirmFragment.newInstance(gameData.allPlayers.elementAt(playerVoteIndex).name.toString()),
                PLAYER_VOTE_TAG
        ).commit()
    }

    override fun onPlayerVoteFragmentFinishListener(position: Int) {
        gameData.allPlayers.elementAt(playerVoteIndex).votedTo = gameData.allPlayers.elementAt(position)
        playerVoteIndex++
        if (playerVoteIndex < gameData.allPlayers.size) {
            fragmentManager.beginTransaction().replace(
                    mainActivityContainerResId,
                    ConfirmFragment.newInstance(gameData.allPlayers.elementAt(playerVoteIndex).name.toString()),
                    PLAYER_VOTE_TAG
            ).commit()
        }else{
            val votedPlayers = ArrayList<IPlayer>()
            gameData.allPlayers.mapTo(votedPlayers){it.votedTo}
            for (votedPlayer in votedPlayers){
                votedPlayer.votedCount++
            }
            val mostVotedPlayerList = mutableListOf<IPlayer>(Player())
            for (player in gameData.allPlayers){
                if (player.votedCount > mostVotedPlayerList[0].votedCount){
                    mostVotedPlayerList.clear()
                    mostVotedPlayerList.add(player)
                }
                if (player.votedCount == mostVotedPlayerList[0].votedCount){
                    mostVotedPlayerList.add(player)
                }
            }
            val winnerAct = when (mostVotedPlayerList.map { it.act  }.contains(Acts.Artist)) {
                true  -> Acts.Wolf
                false -> Acts.Artist
            }

            fragmentManager.beginTransaction().replace(
                    mainActivityContainerResId,
                    ResultFragment.newInstance(winnerAct)
            ).commit()
        }
    }

    override fun onFragmentInteraction(act: Acts, isReversed: Boolean) {

        when(act){
            Acts.Artist -> if (isReversed)
                            fragmentManager.beginTransaction().replace(mainActivityContainerResId, ResultFragment.newInstance(Acts.Wolf)).commit()
                           else
                            fragmentManager.beginTransaction().replace(mainActivityContainerResId, RetryFragment.newInstance()).commit()
            Acts.Wolf ->    fragmentManager.beginTransaction().replace(mainActivityContainerResId, RetryFragment.newInstance()).commit()
        }
    }


    override fun onRetryFragmentRetry() {
        resetAllProgress()
        gameData.selectWolf()
        fragmentManager.beginTransaction().replace(
                mainActivityContainerResId,
                ConfirmFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name.toString()),
                SHOW_ACT_TAG
        ).commit()
    }

    override fun onRetryFragmentFinish() {
        finish()
    }
    private fun resetAllProgress(){
        gameData.resetGame()
        gameData.theme = (resources.getStringArray(R.array.builtInThemes)).toList().shuffled()[0]
        showActIndex = 0
        playerVoteIndex = 0
    }

}
