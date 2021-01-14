package plugin.vesions

object Modules{
    const val common = ":common"
    const val commonBase = ":common_base"
    const val remoteBase = ":remote_base"
    const val userSession = ":user_session"
}

object ResModules {
    const val languages = ":resources:languages"
    const val styles = ":resources:styles"
}
object DataModules{
    const val model = ":app_data_layer:model"

}

object FeatureLogin{

    const val module = ":feature_login:module"
    const val moduleNoOp = "::feature_login:module_no_op"
    const val moduleService = ":feature_login:module_service"

}