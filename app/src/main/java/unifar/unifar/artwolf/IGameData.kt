package unifar.unifar.artwolf

import java.io.Serializable


/**
 * Implement this to make sure all necessary parameters are declared.
 */
interface IGameData : Serializable {
    var isEditedTheme: Boolean
    var playerCount: Int
    var theme: String
    val genre: String
    val wolf: IPlayer
    var votedPlayers: MutableCollection<IPlayer>
    var allPlayers: MutableCollection<IPlayer>
}