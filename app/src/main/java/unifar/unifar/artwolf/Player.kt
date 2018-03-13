package unifar.unifar.artwolf

/**
 * This contain all data to play the game.
 */
class Player :(IPlayer){
    override lateinit var act: Acts
    override var name: CharSequence = ApplicationContextHolder.context.getString(R.string.player)
    override lateinit var votedTo: IPlayer
    override var votedCount: Int = 0

}