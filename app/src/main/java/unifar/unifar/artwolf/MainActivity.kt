package unifar.unifar.artwolf

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import hotchemi.android.rate.AppRate
import android.text.style.TtsSpan
import java.util.*
import hotchemi.android.rate.OnClickButtonListener
import hotchemi.android.rate.StoreType




class MainActivity :
        AppCompatActivity(),
        TitleFragment.OnTitleFragmentStartListener,
        CanvasFragment.OnCanvasFinishListener,
        PlayerNumberDecideFragment.PlayerNumberReceiver,
        PlayerListFragment.OnPlayerInfoDecidedListener,
        ShowActsFragment.OnShowActFragmentFinishListener,
        TallkTimeFragment.OnTalkTimeFragmentFinishListener,
        ConfirmFragment.OnFragmentInteractionListener,
        PlayerVoteFragment.OnPlayerVoteFragmentFinishListener,
        ResultFragment.OnFragmentInteractionListener,
        RetryFragment.OnRetryFragmentRetryListener,
        RetryFragment.OnRetryFragmentFinishListener,
        EditThemeSelectFragment.OnEditThemeSelectFragmentEditListener,
        EditThemeSelectFragment.OnEditThemeSelectFragmentRandomListener,
        EditThemeFragment.OnEditThemeFragmentFinishListener,
        PaintView.CanReDoListener,
        PaintView.CanUnDoListener,
        IGameDataContain {

    private val mainActivityContainerResId = R.id.main_activity_container
    override var gameData: IGameData = GameData()
    private val fragmentManager = supportFragmentManager

    // paintViewからの通知がこっちに飛んでくるため
    private lateinit var canvasFragment: CanvasFragment

    override fun onNotifyCanRedo(canRedo: Boolean) {
        canvasFragment.onNotifyCanRedo(canRedo)
    }

    override fun onNotifyCanUndo(canUndo: Boolean) {
        canvasFragment.onNotifyCanUndo(canUndo)
    }
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
        savedInstanceState ?: replaceFragment(TitleFragment.newInstance())
        savedInstanceState?.let { gameData = savedInstanceState.getSerializable(GAME_DATA_KEY) as IGameData}
        savedInstanceState?.let { showActIndex = savedInstanceState.getInt(SHOW_ACT_INDEX_KEY) }
        savedInstanceState?.let { playerVoteIndex = savedInstanceState.getInt(PLAYER_VOTE_KEY) }
        AppRate.with(this)
                .setInstallDays(5)
                .setLaunchTimes(4)
                .setRemindInterval(1)
                .monitor()

        AppRate.showRateDialogIfMeetsConditions(this)
    }



    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(GAME_DATA_KEY, gameData)
        outState?.putInt(SHOW_ACT_INDEX_KEY, showActIndex)
        outState?.putInt(PLAYER_VOTE_KEY, playerVoteIndex)
    }

    override fun onTitleFragmentStart() {
        replaceFragment(EditThemeSelectFragment.newInstance())
    }

    override fun onEditThemeSelectFragmentRandom() {
        gameData.theme = (resources.getStringArray(R.array.builtInThemes)).toList().shuffled()[0]
        if (gameData.allPlayers.map {it.act}.contains(Acts.Wolf)) {
            replaceFragment(
                    ConfirmFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name.toString()),
                    SHOW_ACT_TAG)
        }else{
            replaceFragment(PlayerNumberDecideFragment.newInstance())
        }
        }
    override fun onEditThemeSelectFragmentEdit() {
        replaceFragment(EditThemeFragment.newInstance())
    }
    override fun onEditThemeFragmentFinish(theme: String) {
        gameData.theme = theme
        if (gameData.allPlayers.map {it.act}.contains(Acts.Wolf)) {
            replaceFragment(
                    ConfirmFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name.toString()),
                    SHOW_ACT_TAG)
        }else{
            replaceFragment(PlayerNumberDecideFragment.newInstance())
        }
    }

    override fun onValueDecided(playerNumber: Int) {
        if (gameData.allPlayers.isEmpty()){
            for (i in 1..playerNumber){
                gameData.allPlayers.add(Player().apply { name = getString(R.string.player) + i.toString() })
            }
        }
        gameData.playerCount = gameData.allPlayers.size
        replaceFragment(PlayerListFragment.newInstance(1, gameData.allPlayers.map { it.name.toString() }.toMutableList()))
    }

    private var showActIndex: Int = 0

    override fun onPlayerInfoDecided(allNames: Collection<CharSequence>) {
        gameData.allPlayers.forEachIndexed {
            index, iPlayer -> iPlayer.name = allNames.elementAt(index) }
        gameData.selectWolf()
        replaceFragment(
                ConfirmFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name.toString()),
                SHOW_ACT_TAG)
    }

    private var playerVoteIndex = 0

    override fun onConfirmFragmentFinish(tag: String) {
        if (tag == ""){
            throw RuntimeException("Please start fragment with tag ")
        }
        if (tag == SHOW_ACT_TAG) {
            replaceFragment(ShowActsFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name.toString(), gameData.allPlayers.elementAt(showActIndex).act, gameData.theme))

        }

        val playerNames = ArrayList<kotlin.CharSequence>()
        gameData.allPlayers.mapTo(playerNames) {it.name}
        playerNames.removeAt(playerVoteIndex)
        if (tag == PLAYER_VOTE_TAG){
            replaceFragment(PlayerVoteFragment.newInstance(1, playerNames))
        }
    }

    override fun onShowActFragmentFinish() {
        showActIndex++
        if (showActIndex < gameData.allPlayers.size){
            replaceFragment(
                    ConfirmFragment.newInstance(gameData.allPlayers.elementAt(showActIndex).name.toString()),
                    SHOW_ACT_TAG)
        }else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            canvasFragment = CanvasFragment.newInstance(gameData.allPlayers.map { it.name}.toTypedArray())
            replaceFragment(canvasFragment)
        }
    }

    override fun onCanvasFragmentFinish() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        replaceFragment(TallkTimeFragment.newInstance())
    }


    override fun onTalkTimeFragmentFinish() {
        val playerNames = ArrayList<kotlin.CharSequence>()
        gameData.allPlayers.mapTo(playerNames) { it.name}
        replaceFragment(
                ConfirmFragment.newInstance(gameData.allPlayers.elementAt(playerVoteIndex).name.toString()),
                PLAYER_VOTE_TAG)
    }


    override fun onPlayerVoteFragmentFinishListener(position: Int) {
        gameData.allPlayers.elementAt(playerVoteIndex).votedTo = gameData.allPlayers.elementAt(position)
        playerVoteIndex++
        if (playerVoteIndex < gameData.allPlayers.size) {
            replaceFragment(
                    ConfirmFragment.newInstance(gameData.allPlayers.elementAt(playerVoteIndex).name.toString()),
                    PLAYER_VOTE_TAG)
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
            replaceFragment(ResultFragment.newInstance(winnerAct))
        }
    }

    override fun onFragmentInteraction(act: Acts, isReversed: Boolean) {

        when(act){
            Acts.Artist -> if (isReversed)
                            replaceFragment(ResultFragment.newInstance(Acts.Wolf))
                            else
                            replaceFragment(RetryFragment.newInstance())
            Acts.Wolf ->    replaceFragment(RetryFragment.newInstance())
        }
    }

    override fun onRetryFragmentRetry() {
        resetAllProgress()
        gameData.selectWolf()
        replaceFragment(EditThemeSelectFragment.newInstance())
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


    private fun replaceFragment(fragment: Fragment, tag: String = "replacedByMainActivity"){
        fragmentManager.beginTransaction().replace(
                mainActivityContainerResId,
                fragment,
                tag
        ).commit()
    }
}
