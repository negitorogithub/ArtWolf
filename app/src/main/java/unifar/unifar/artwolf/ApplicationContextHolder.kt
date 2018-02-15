package unifar.unifar.artwolf

import android.app.Application
import android.content.Context

/**
 * Hold application context.
 */
class ApplicationContextHolder : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: ApplicationContextHolder
            private set
    }
}