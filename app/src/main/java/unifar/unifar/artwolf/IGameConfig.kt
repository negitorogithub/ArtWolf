package unifar.unifar.artwolf


/**
 * Created by 三悟 on 2018/02/10.
 */
interface IGameConfig {
    var isEditedTheme: Boolean
    val playerCount: Int
    val theme: String
    val genre: String
    val wolf: IPlayer
}