package unifar.unifar.artwolf

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), CanvasFragment.OnFragmentInteractionListener, IGameDataContain {
    override var gameData: IGameData = GameData()
    override fun onFragmentInteraction(uri: Uri) {
        //empty for now
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.main_activity_container,CanvasFragment()).commit()
    }
}
