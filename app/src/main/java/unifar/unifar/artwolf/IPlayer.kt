package unifar.unifar.artwolf

/**
 * Use this to hold necessary information for player.
 */
interface IPlayer {
    val name_: String
    var votedTo: IPlayer
}