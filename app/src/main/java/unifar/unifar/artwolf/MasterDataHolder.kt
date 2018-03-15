package unifar.unifar.artwolf

/**
 * Holds builtin theme.
 */
class MasterDataHolder : IMasterDataHolder{
    override val allTheme: Array<out String> = MyApplication.context.resources.getStringArray(R.array.builtInThemes)!! //To change initializer of created properties use File | Settings | File Templates.
}
