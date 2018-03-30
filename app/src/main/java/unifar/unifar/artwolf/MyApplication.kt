package unifar.unifar.artwolf

import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds;
import android.R.raw
import android.media.MediaPlayer




/**
 * Hold application context.
 */
class MyApplication : Application() {

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