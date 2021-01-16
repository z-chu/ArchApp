package plugin.vesions

object Modules {
    const val common = ":common"
    const val commonBase = ":common_base"
    const val remoteBase = ":remote_base"
    const val userSession = ":user_session"

    object User {
        const val module = ":user:module"
    }
}

object ResModules {
    const val languages = ":resources:languages"
    const val styles = ":resources:styles"
}

object DataModules {
    const val model = ":data:model"

}

object FeatureLogin {

    const val module = ":login:module"
    const val moduleNoOp = "::login:module_no_op"
    const val moduleService = ":login:module_service"

}