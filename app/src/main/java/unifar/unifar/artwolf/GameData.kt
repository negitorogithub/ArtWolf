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
        override var wolf: IPlayer = Player() ) : IGameData, Serializable{
    override fun resetGame(){
        allPlayers.map { it.act = Acts.Artist }
        allPlayers.map { it.votedCount = 0 }
        allPlayers.map { it.votedTo = Player() }

        theme = "default"
        genre = "default"
        wolf = Player()
    }
    override fun selectWolf(){

        val actsList = mutableListOf(Acts.Wolf)
        if (allPlayers.size >= 3){
            for (i in 2..allPlayers.size){
                actsList.add(Acts.Artist)
            }
        }
        actsList.shuffle()
        for (acts in actsList.withIndex()) {
            allPlayers.elementAt(acts.index).act = actsList.elementAt(acts.index)
        }
    }
}
