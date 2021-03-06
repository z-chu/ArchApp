# ArchApp

组件化解决方案 

<img src="https://raw.githubusercontent.com/z-chu/ArchApp/master/ArchApp/Screenshot/ArchApp.png?raw=true" width="700"> 


## 思想框架
* 一个简单的组件化方案
* 项目结构 比如：网络层是各产业线内部聚合管理还都写一个 module
* 应该有哪些基础的 组件、module、做什么的
* 外部库版本的统一、配置的统一
* 只能用在项目的组件尽量放大仓 和可以通用在其他项目的组件 尽量抽出去
* 源码编译真的就比 aar 编译慢吗
* 组件化主要是为了解决什么问题。 
	1. 编译时间慢 
	2. 修改隔离 
	3. 访问域控制 
	4. 对修改关闭、对扩展开放
* 如何实现组件可单独运行




## 组件化到底是为了解决什么问题

3. 开发和调测一个业务功能的时候，需要整体编译APP，由于代码量越大，编译速度会相应地变慢，导致一个简单功能的的修改可能就要花费几分钟的时间编译整个APP，极大地影响开发效率。
2. 定位一个A业务的问题，可能需要在十个业务代码混合的模块里面寻找和跳转。
2. 新工程师需要了解各个业务的功能，避免代码的改动而影响其它的业务功能，势必无形中增加了项目维护的成本。（代码隔离）
4. 内部项目没有一个统一的快速开发框架，每个项目采用的技术实现方式都不一样，每个开发人员的编码风格也不一致，导致每开发一个新项目都要重新编码、重复造轮子，而且也造成了开发人员在项目之间的流动困难。
5. 有一些内部项目可以共用的功能模块，同样混杂于各个app工程里面，这部分内容实际上可以考虑抽取出来，封装成独立的公用组件，供内部各个项目使用。

## 怎么解决
* 业务模块单独编译，单独运行，而不是耗费大量时间全量编译整个 App
* 跨模块的调用应该优雅，无论两个模块在依赖树中处于什么样的位置，都可以很简单的互相调用
* 不要有太多的学习成本，沿用目前已有的开发方式，避免代码和具体的组件化框架绑定
* 组件化的过程可以是渐进的，立即拆分代码不是组件化的前置条件
* 轻量级，不要引入过多中间层次（例如序列化反序列化）导致不必要的性能开销以及维护复杂度


## 依赖注入


* 启动初始化 [android-startup](https://github.com/idisfkj/android-startup)
* 依赖注入本质是什么、单例模式其实就是一种依赖注入
* 依赖注入 koin
* 分析对比 [android-dependency-injection-performance](https://github.com/Sloy/android-dependency-injection-performance)
* [放弃 Dagger 拥抱 Koin](https://juejin.cn/post/6844904158324064269)



## 一个组件的组成
* 公开的接口、model
* 公开接口的实现 
* 空实现
* 启动入口


## Gradle 依赖管理

依赖库的版本管理

[再见吧 buildSrc, 拥抱 Composing builds 提升 Android 编译速度](https://juejin.cn/post/6844904176250519565)



到目前为止大概管理 Gradle 依赖提供了 4 种不同方法：

* 手动管理 ：在每个 module 中定义插件依赖库，每次升级依赖库时都需要手动更改（不建议使用）
* 使用 ext 的方式管理插件依赖库 ：这是 Google 推荐管理依赖的方法 [Android官方文档](https://developer.android.com/studio/build/gradle-tips#configure-project-wide-properties)
* Kotlin + buildSrc：自动补全和单击跳转，依赖更新时 将重新 构建整个项目
* Composing builds：自动补全和单击跳转，依赖更新时 不会重新 构建整个项目

## MVVM
<img src="https://raw.githubusercontent.com/z-chu/ArchApp/master/ArchApp/Screenshot/MVVM.png?raw=true" width="600"> 


## 建议 
* 不在使用 butterknife
* 不在使用 kotlin-android-extensions
* 数据库统一 为 Room

## 其他思考
* 二级 git 仓库
* debug 工具集
* 多渠道打包、公共配置(manifestPlaceholders、productFlavors) 的管理
* 组件之间,支持多app,多进程.
* 通过lint,限制用反射的方式调用组件（有的人懒，喜欢用反射去调其他组件的代码）
* 可以利用组件之间AndroidManifest合并

## 参考文章

 * [回归初心：极简 Android 组件化方案 — AppJoint](https://juejin.im/post/6844903687488274445)
 * [【译】迁移被废弃的Kotlin Android Extensions插件](https://weilu.blog.csdn.net/article/details/109557820)
 * [组件化之AutoService使用与源码解析](https://www.jianshu.com/p/086fe09188ea)

 
