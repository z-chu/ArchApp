@file:Suppress("unused")

package plugin.vesions

object Modules {
    const val common = ":common"
    const val commonBase = ":common_base"
    const val userSession = ":user_session"
    const val moduleServiceMockKit = ":module_service_mock_kit"
    const val moduleService = ":module_service"

    object User {
        const val module = ":user:module"
        const val moduleNoOp = ":user:module_no_op"
        const val moduleService = ":user:module_service"
    }

    object Login {
        const val module = ":login:module"
        const val moduleNoOp = "::login:module_no_op"
        const val moduleService = ":login:module_service"
    }


    object Gank {
        const val module = ":gank:module"
        const val moduleNoOp = ":gank:module_no_op"
        const val moduleService = ":gank:module_service"
    }

    object Picture {
        const val module = ":picture:module"
        const val moduleNoOp = ":picture:module_no_op"
        const val moduleService = ":picture:module_service"
    }


    object Resources {
        const val languages = ":resources:languages"
        const val styles = ":resources:styles"
    }

    object Data {
        const val model = ":data:model"
        const val remote = ":data:remote"
        const val repository = ":data:repository"
        const val local = ":data:local"

    }


}


