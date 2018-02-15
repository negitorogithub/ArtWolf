package unifar.unifar.artwolf

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity :
        AppCompatActivity(),
        CanvasFragment.OnFragmentInteractionListener,
        PlayerNumberDecideFragment.PlayerNumberReceiver,
        IGameDataContain {

    private val mainActivityContainerResId = R.id.main_activity_container
    override var gameData: IGameData = GameData()
    private val fragmentManager = supportFragmentManager

    override fun onFragmentInteraction(uri: Uri) {
        //empty for now
    }


    override fun onValueDecided(playerNumber: Int) {
        gameData.playerCount = playerNumber
        fragmentManager.beginTransaction().replace(mainActivityContainerResId, CanvasFragment.newInstance()).commit()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager.beginTransaction().add(mainActivityContainerResId, PlayerNumberDecideFragment.newInstance()).commit()
    }
}
