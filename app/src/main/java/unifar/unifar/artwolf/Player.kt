package unifar.unifar.artwolf

/**
 * This contain all data to play the game.
 */
class Player : IPlayer {
    override lateinit var act: Acts
    override var name_: CharSequence = "Player"
    override var votedTo: IPlayer? = null
}