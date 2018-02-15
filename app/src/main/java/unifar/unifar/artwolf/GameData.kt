package unifar.unifar.artwolf

/**
 * Use this to implement all necessary parameters used in the game.
 * At least one parameter must be specified.
 */

class GameData(
        override var isEditedTheme: Boolean = false,
        override var allPlayers: Collection<IPlayer> = mutableSetOf(),
        override var votedPlayers: Collection<IPlayer> = mutableSetOf(),
        override var playerCount: Int = allPlayers.size,
        override var theme: String = "default",
        override var genre: String = "default",
        override var wolf: IPlayer = Player() ) : IGameData{
}
