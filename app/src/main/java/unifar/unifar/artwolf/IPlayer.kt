package unifar.unifar.artwolf

/**
 * Use this to hold necessary information for player.
 */
interface IPlayer {
    var name_: CharSequence
    var votedTo: IPlayer?
}