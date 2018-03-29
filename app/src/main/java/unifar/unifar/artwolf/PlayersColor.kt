package unifar.unifar.artwolf

/**
 * This class provides colorResId palette for specified number.
 *
 * If the value is 0 or less,throws IllegalArgumentException.
 * If the value is more than the number of colorToUse, return all colorToUse.
 * @param playerNumber The number of the colors to generate.>0
 * @throws IllegalArgumentException
 */

class PlayersColor(private val playerNumber: Int) {
    enum class ColorsToUse(val colorResId: Int){
        Red(R.color.red_500),
        Blue(R.color.blue_500),
        Orange(R.color.orange_500),
        Green(R.color.green_500),
        Purple(R.color.purple_500),

        Pink(R.color.pink_500),
        DeepOrange(R.color.deep_orange_500),
        Cyan(R.color.cyan_500),
        Teal(R.color.teal_500),
        Indigo(R.color.indigo_500),
        DeepPurple(R.color.deep_purple_500),

        LightGreen(R.color.light_green_500),
        LightBlue(R.color.light_blue_500),
        Brown(R.color.brown_500)
    }
    /**
     * all colors of ColorsToUse Enum.
     */
    private val fullPalette = enumValues<ColorsToUse>()

    /**
     * playerNumber colors from Color to use Enum.
     */
    private val paletteForPlayers = mutableListOf<Int>()

    /**
     * the colorResId used now.
     */
    var currentColorId:Int = fullPalette[0].colorResId
    init {


        if (playerNumber <= 0) {
            throw IllegalArgumentException("playerNumber must be 1 or bigger.Now it is " + playerNumber)
        } else {
            fullPalette.forEach {
                if (paletteForPlayers.size < playerNumber) {
                    paletteForPlayers.add(it.colorResId)
                }else{
                    return@forEach
                }
            }
        }
    }

    fun nextColorResId(): Int{
        currentColorId = if (paletteForPlayers.indexOf(currentColorId)  < paletteForPlayers.size -1 )
            paletteForPlayers[paletteForPlayers.indexOf(currentColorId) + 1]
            else paletteForPlayers[0]
        return currentColorId
    }



}
