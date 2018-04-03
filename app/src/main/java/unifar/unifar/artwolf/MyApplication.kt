package unifar.unifar.artwolf

import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds;
import android.R.raw
import android.app.Activity
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.provider.MediaStore
import android.util.Log


/**
 * Hold application context.
 */
class MyApplication : Application() {

    init {
    }

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this, getString(R.string.appId))
        context = this
    }





    companion object {

        val adRequest: AdRequest = AdRequest.Builder().addTestDevice("71FDD2458B24F37418B39566411942D2").build()
        lateinit var context: MyApplication
            private set

    }

}