package unifar.unifar.artwolf


/**
 * Implement this to make sure all necessary parameters are declared.
 */
interface IGameData {
    var isEditedTheme: Boolean
    var playerCount: Int
    val theme: String
    val genre: String
    val wolf: IPlayer
    var votedPlayers: Collection<IPlayer>
    val allPlayers: Collection<IPlayer>
}