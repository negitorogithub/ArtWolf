package unifar.unifar.artwolf

/**
 * Created by 三悟 on 2018/02/10.
 */
interface IPlayer {
    val name: String
    var votedTo: IPlayer
}