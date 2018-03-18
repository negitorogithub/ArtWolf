package unifar.unifar.artwolf

import java.io.Serializable

/**
 * This contain all data to play the game.
 */
class Player : IPlayer, Serializable{
    override lateinit var act: Acts
    override var name: CharSequence = MyApplication.context.getString(R.string.player)
    override lateinit var votedTo: IPlayer
    override var votedCount: Int = 0

}