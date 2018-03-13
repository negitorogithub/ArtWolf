package unifar.unifar.artwolf

/**
 * Use this to hold necessary information for player.
 */
interface IPlayer {
    var name: CharSequence
    var act: Acts
    var votedTo: IPlayer
    var votedCount: Int
}