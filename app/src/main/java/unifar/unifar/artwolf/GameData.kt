package unifar.unifar.artwolf

import java.io.Serializable
import java.security.SecureRandom

/**
 * Use this to implement all necessary parameters used in the game.
 */

class GameData (
        override var isEditedTheme: Boolean = false,
        override var allPlayers: MutableCollection<IPlayer> = mutableListOf(),
        override var votedPlayers: MutableCollection<IPlayer> = mutableListOf(),
        override var playerCount: Int = allPlayers.size,
        override var theme: String = "default",
        override var genre: String = "default",
        override var wolf: IPlayer = Player() ) : IGameData, Serializable
