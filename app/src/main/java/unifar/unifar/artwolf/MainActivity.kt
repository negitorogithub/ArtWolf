package unifar.unifar.artwolf

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity :
        AppCompatActivity(),
        CanvasFragment.OnFragmentInteractionListener,
        PlayerNumberDecideFragment.PlayerNumberReceiver,
        PlayerListFragment.OnPlayerInfoDecidedListener,
        IGameDataContain {

    private val mainActivityContainerResId = R.id.main_activity_container
    override var gameData: IGameData = GameData()
    private val fragmentManager = supportFragmentManager

    override fun onFragmentInteraction(uri: Uri) {
        //empty for now
    }


    override fun onValueDecided(playerNumber: Int) {
        if (gameData.allPlayers.isEmpty()){
            for (i in 1..playerNumber){
                gameData.allPlayers.add(Player())
            }
        }
        gameData.playerCount = gameData.allPlayers.size
        fragmentManager.beginTransaction().replace(mainActivityContainerResId, PlayerListFragment.newInstance(1)).commit()

    }

    override fun onPlayerInfoDecided(IPlayers: MutableCollection<IPlayer>) {
        gameData.allPlayers = IPlayers
        fragmentManager.beginTransaction().replace(mainActivityContainerResId, CanvasFragment.newInstance()).commit()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: fragmentManager.beginTransaction().replace(mainActivityContainerResId, PlayerNumberDecideFragment.newInstance()).commit()
    }
}
