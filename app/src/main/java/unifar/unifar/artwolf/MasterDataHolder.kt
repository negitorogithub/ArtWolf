package unifar.unifar.artwolf

import java.util.ArrayList

/**
 * Holds builtin theme.
 */
class MasterDataHolder : IMasterDataHolder{
    override val allTheme: Array<out String> = ApplicationContextHolder.context.resources.getStringArray(R.array.builtInThemes)!! //To change initializer of created properties use File | Settings | File Templates.
}
