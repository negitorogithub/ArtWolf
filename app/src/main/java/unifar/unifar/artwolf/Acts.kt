package unifar.unifar.artwolf

import android.content.res.Resources

/**
 * All acts used in this game.
 */
enum class Acts constructor(private val resourceId: Int) {
    Artist(R.string.artist),
    Wolf(R.string.wolf);

    override fun toString(): String {
        return name
    }
    fun toString(res: Resources): String{
        return res.getString(resourceId)
    }
}