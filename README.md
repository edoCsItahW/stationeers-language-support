# IntelliJ 平台插件模板

[![官方 JetBrains 项目](https://jb.gg/badges/official.svg)][jb:github]
[![Twitter 关注](https://img.shields.io/badge/follow-%40JBPlatform-1DA1F2?logo=twitter)](https://x.com/JBPlatform)
[![构建](https://github.com/JetBrains/intellij-platform-plugin-template/workflows/Build/badge.svg)][gh:build]

![IntelliJ 平台插件模板][file:intellij-platform-plugin-template-dark]
![IntelliJ 平台插件模板][file:intellij-platform-plugin-template-light]

> [!注意]
> 点击 <kbd>使用此模板</kbd> 按钮，然后在 IntelliJ IDEA 中克隆它。

<!-- 插件描述 -->
**IntelliJ 平台插件模板** 是一个仓库，提供了一个纯粹的模板，以便更轻松地创建新的插件项目（查看 [从模板创建仓库][gh:template] 文章）。

此模板的主要目标是通过预配置项目脚手架和持续集成、链接到适当的文档页面以及保持一切井然有序，来加快新老开发人员的插件开发设置阶段。

[gh:template]: https://docs.github.com/zh/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template
<!-- 插件描述结束 -->

如果你仍然不太确定这是什么，请阅读我们的介绍：[什么是 IntelliJ 平台？][docs:intro]

> [!注意]
> 点击顶部的 <kbd>关注</kbd> 按钮，以便在包含新功能和修复的发布时收到通知。

### 目录

在本 README 中，我们将重点介绍模板项目创建的以下元素：

- [入门指南](#入门指南)
- [Gradle 配置](#gradle-配置)
- [插件模板结构](#插件模板结构)
- [插件配置文件](#插件配置文件)
- [示例代码](#示例代码)：
  - 监听器 – 项目生命周期监听器
  - 服务 – 项目级和应用级服务
- [测试](#测试)
  - [功能测试](#功能测试)
  - [代码覆盖率](#代码覆盖率)
  - [UI 测试](#ui-测试)
- [Qodana 集成](#qodana-集成)
- [预定义的 运行/调试 配置](#预定义的运行调试配置)
- [基于 GitHub Actions 的持续集成](#持续集成)
  - [使用 Dependabot 进行依赖管理](#依赖管理)
  - [使用 Gradle Changelog 插件维护更新日志](#更新日志维护)
  - [使用 GitHub Releases 的发布流程](#发布流程)
  - [使用您的私有证书进行插件签名](#插件签名)
  - [使用 IntelliJ Platform Gradle 插件发布插件](#发布插件)
- [常见问题](#常见问题)
- [有用链接](#有用链接)


## 入门指南

在深入插件开发及其相关内容之前，值得先提一下使用 GitHub 模板的好处。
通过使用当前模板创建一个新项目，你可以从一个没有历史记录或与此仓库引用关联的项目开始。
这使得你可以轻松创建一个新仓库，而无需复制粘贴先前的内容、克隆仓库或手动清除历史记录。

你需要做的就是点击 <kbd>使用此模板</kbd> 按钮（你必须使用 GitHub 帐户登录）。

![使用此模板][file:use-this-template.png]

使用模板创建空白项目后，[模板清理][file:template_cleanup.yml] 工作流将被触发，以覆盖或删除任何特定于模板的配置，例如插件名称、当前更新日志等。
完成后，打开新创建项目的 _设置 | Actions | 常规_ 页面，并启用选项 _允许 GitHub Actions 创建和批准拉取请求_。

现在项目已准备就绪，可以克隆到本地环境并使用 [IntelliJ IDEA][jb:download-ij] 打开。

从 GitHub 获取新项目最方便的方式是欢迎屏幕上可用的 <kbd>从版本控制系统获取</kbd> 操作，你可以在那里按名称筛选你的 GitHub 仓库。

![从版本控制系统获取][file:get-from-version-control]

在 IntelliJ IDEA 中打开项目后的下一步，是在 [项目结构设置][docs:project-structure-settings] 中为 Java 设置合适的 <kbd>SDK</kbd>，版本为 `17`。

![项目结构 — SDK][file:project-structure-sdk.png]

最后一步，你必须手动检查 [`gradle.properties`][file:gradle.properties] 文件中描述的配置变量，并*可选地*将 `com.github.username.repository` 包中的源代码移动到最适合你的包中。
然后你就可以开始实现你的想法了。

> [!注意]
> 要在插件中使用 Java，请创建 `/src/main/java` 目录。


## Gradle 配置

插件开发的推荐方法涉及使用安装了 [intellij-platform-gradle-plugin][gh:intellij-platform-gradle-plugin] 的 [Gradle][gradle] 设置。
IntelliJ Platform Gradle 插件使得可以运行附带你的插件的 IDE，并将你的插件发布到 JetBrains 市场。

> [!注意]
> 请务必始终升级到最新版本的 IntelliJ Platform Gradle 插件。

使用 IntelliJ 平台插件模板构建的项目包含一个已经设置好的 Gradle 配置。
请随时阅读 [使用 Gradle][docs:using-gradle] 文章，以更好地了解你的构建并学习如何自定义它。

当前配置最重要的部分是：
- 与 [intellij-platform-gradle-plugin][gh:intellij-platform-gradle-plugin] 集成，以获得更顺畅的开发体验。
- 使用 [Gradle Kotlin DSL][gradle:kotlin-dsl] 编写的配置。
- 支持 Kotlin 和 Java 实现。
- 与 [gradle-changelog-plugin][gh:gradle-changelog-plugin] 集成，该插件基于 `CHANGELOG.md` 文件自动修补更新说明。
- 使用令牌进行 [插件发布][docs:publishing]。

有关 Kotlin 集成的更多详细信息，请参阅 IntelliJ 平台插件 SDK 文档中的 [面向插件开发者的 Kotlin][docs:kotlin]。

### Gradle 属性

项目特定的配置文件 [`gradle.properties`][file:gradle.properties] 包含：

| 属性名               | 描述                                                                                                 |
|----------------------|-----------------------------------------------------------------------------------------------------|
| `pluginGroup`        | 包名 - *使用*模板后，这将被设置为 `com.github.username.repo`。                                         |
| `pluginName`         | JetBrains 市场中显示的插件名称。                                                                     |
| `pluginRepositoryUrl`| 用于由 [Gradle Changelog Plugin][gh:gradle-changelog-plugin] 生成 URL 的仓库 URL                      |
| `pluginVersion`      | 插件的当前版本，格式为 [语义化版本][semver]。                                                        |
| `pluginSinceBuild`   | `<idea-version>` 标签的 `since-build` 属性。                                                          |
| `platformVersion`    | 用于构建插件的 IntelliJ Platform IDE 版本。                                                           |
| `platformPlugins`    | 插件仓库中插件依赖项的逗号分隔列表。                                                                 |
| `platformBundledPlugins` | 捆绑的 IDE 插件的依赖项逗号分隔列表。                                                               |
| `platformBundledModules` | 捆绑的 IDE 模块的依赖项逗号分隔列表。                                                               |
| `gradleVersion`      | 用于插件开发的 Gradle 版本。                                                                         |

列出的属性定义了插件本身或配置了 [intellij-platform-gradle-plugin][gh:intellij-platform-gradle-plugin] – 查看其文档以获取更多详细信息。

此外，通过 [`gradle.properties`][file:gradle.properties] 文件配置了额外的行为，例如：

| 属性名                                     | 值     | 描述                                                                                        |
|-------------------------------------------|--------|--------------------------------------------------------------------------------------------|
| `kotlin.stdlib.default.dependency`        | `false`| 选择退出捆绑 [Kotlin 标准库][docs:kotlin-stdlib] 的标志                                    |
| `org.gradle.configuration-cache`          | `true` | 启用 [Gradle 配置缓存][gradle:configuration-cache]                                          |
| `org.gradle.caching`                      | `true` | 启用 [Gradle 构建缓存][gradle:build-cache]                                                  |

### 环境变量

Gradle 配置中使用的一些值不应存储在文件中，以避免将它们发布到版本控制系统。

为了避免这种情况，引入了环境变量，可以在 IDE 内的 *运行/调试配置* 中提供，或者在持续集成（CI）上提供 – 例如对于 GitHub：`⚙️ 设置 > 秘密和变量 > Actions`。

当前项目使用的环境变量与 [插件签名](#插件签名) 和 [发布插件](#发布插件) 相关。

| 环境变量名           | 描述                                                                                                   |
|----------------------|-------------------------------------------------------------------------------------------------------|
| `PRIVATE_KEY`        | 证书私钥，应包含：`-----BEGIN RSA PRIVATE KEY----- ... -----END RSA PRIVATE KEY-----`                  |
| `PRIVATE_KEY_PASSWORD` | 用于加密证书文件的密码。                                                                              |
| `CERTIFICATE_CHAIN`  | 证书链，应包含：`-----BEGIN CERTIFICATE----- ... -----END CERTIFICATE----`                             |
| `PUBLISH_TOKEN`      | 在你的 JetBrains Marketplace 个人资料仪表板中生成的发布令牌。                                          |

有关如何生成适当值的更多详细信息，请查看上面提到的相关部分。

要配置 GitHub 秘密环境变量，请转到项目仓库的 `⚙️ 设置 > 秘密和变量 > Actions` 部分：

![设置 > 秘密][file:settings-secrets.png]

## 插件模板结构

生成的 IntelliJ 平台插件模板仓库包含以下内容结构：

```
.
├── .github/                GitHub Actions 工作流和 Dependabot 配置文件
├── .run/                   预定义的运行/调试配置
├── build/                  输出构建目录
├── gradle
│   ├── wrapper/            Gradle 包装器
│   └── libs.versions.toml  Gradle 版本目录
├── src                     插件源码
│   ├── main
│   │   ├── kotlin/         Kotlin 生产源码
│   │   └── resources/      资源 - plugin.xml, 图标, 消息
│   └── test
│       ├── kotlin/         Kotlin 测试源码
│       └── testData/       测试使用的测试数据
├── .gitignore              Git 忽略规则
├── build.gradle.kts        Gradle 配置
├── CHANGELOG.md            完整的变更历史
├── gradle.properties       Gradle 配置属性
├── gradlew                 *nix Gradle 包装器脚本
├── gradlew.bat             Windows Gradle 包装器脚本
├── LICENSE                 许可证，默认为 MIT
├── qodana.yml              Qodana 配置文件
├── README.md               README
└── settings.gradle.kts     Gradle 项目设置
```

除了配置文件之外，最重要的部分是 `src` 目录，它包含了我们的实现和插件的清单 – [plugin.xml][file:plugin.xml]。

> [!注意]
> 要在插件中使用 Java，请创建 `/src/main/java` 目录。


## 插件配置文件

插件配置文件是一个位于 `src/main/resources/META-INF` 目录中的 [plugin.xml][file:plugin.xml] 文件。
它提供了关于插件、其依赖项、扩展和监听器的一般信息。

```xml
<idea-plugin>
  <id>org.jetbrains.plugins.template</id>
  <name>Template</name>
  <vendor>JetBrains</vendor>

  <depends>com.intellij.modules.platform</depends>

  <resource-bundle>messages.MyBundle</resource-bundle>

  <extensions defaultExtensionNs="com.intellij">
    <toolWindow factoryClass="..." id="..."/>
  </extensions>

  <applicationListeners>
    <listener class="..." topic="..."/>
  </applicationListeners>
</idea-plugin>
```

你可以在我们的文档 [插件配置文件][docs:plugin.xml] 部分阅读更多关于此文件的信息。


## 示例代码

准备好的模板提供了尽可能少的代码，因为对于通用脚手架来说，不可能满足所有类型插件（语言支持、构建工具、VCS 相关工具）的所有特定要求。
因此，模板仅包含以下文件：

```
.
├── listeners
│   └── MyApplicationActivationListener.kt  应用激活监听器 – 检测 IDE 框架何时激活
├── services
│   └── MyProjectService.kt                 项目级服务
├── toolWindow
│   └── MyToolWindowFactory.kt              工具窗口工厂 – 创建工具窗口内容
└── MyBundle.kt                             提供对资源消息访问的 Bundle 类
```

这些文件位于 `src/main/kotlin`。
这个位置指示了使用的语言。
所以，如果你决定改用 Java（或除了 Kotlin 之外还用 Java），这些源码应位于 `src/main/java` 目录中。

> [!提示]
> 可以在你的插件中使用 [IntelliJ 平台图标](https://jb.gg/new-ui-icons)。

要开始实际实现，你可以查看我们的 [IntelliJ 平台 SDK 开发者指南][docs]，其中包含对插件开发关键领域的介绍以及专门的教程。

> [!警告]
> 记得删除所有不需要的示例代码文件及其在 `plugin.xml` 中的对应注册条目。

对于那些最重视示例代码的人来说，还有可用的 [IntelliJ SDK 代码示例][gh:code-samples] 和 [IntelliJ Platform Explorer][jb:ipe] – 一个用于在开源 IntelliJ 平台插件的现有实现中浏览扩展点的搜索工具。

## 测试

[测试插件][docs:testing-plugins]是插件开发的重要组成部分，以确保在 IDE 版本和插件重构之间一切按预期工作。
IntelliJ 平台插件模板项目集成了两种测试方法 – 功能测试和 UI 测试。

### 功能测试

大部分 IntelliJ 平台代码库测试是模型级别的，在无头环境中使用实际的 IDE 实例运行。
测试通常将功能作为一个整体进行测试，而不是像单元测试那样测试构成其实现的单个函数。

在 `src/test/kotlin` 中，你会找到一个基本的 `MyPluginTest` 测试，它利用 `BasePlatformTestCase` 并针对 XML 文件运行一些检查，以指示动态创建文件或从 `src/test/testData/rename` 测试资源读取文件等示例操作。

> [!注意]
> 使用预定义的*运行测试*配置或调用 `./gradlew check` Gradle 任务来运行你的测试。

### 代码覆盖率

[Kover][gh:kover] – 一个用于 Kotlin 代码覆盖率代理的 Gradle 插件：IntelliJ 和 JaCoCo – 已集成到项目中以提供代码覆盖率功能。
代码覆盖率使得可以测量和跟踪插件源码的测试程度。
代码覆盖率在运行 `check` Gradle 任务时执行。
最终的测试报告被发送到 [CodeCov][codecov] 以更好地可视化结果。

### UI 测试

如果你的插件提供了复杂的用户界面，你应该考虑用测试以及它们使用的功能来覆盖它们。

[IntelliJ UI 测试机器人][gh:intellij-ui-test-robot]允许你在正在运行的 JetBrains IDE 实例中编写和执行 UI 测试。
你可以使用 [XPath 查询语言][xpath] 在当前可用的 IDE 视图中查找组件。
一旦启动了带有 `robot-server` 的 IDE，你可以打开 `http://localhost:8082` 页面，该页面以 HTML 格式呈现当前可用的 IDEA UI 组件层次结构，并使用一个简单的 `XPath` 生成器，这有助于测试你的插件界面。

> [!注意]
> 通过调用 `./gradlew runIdeForUiTests` 和 `./gradlew check` Gradle 任务来运行 UI 测试的 IDE。

查看 UI 测试示例项目，你可以将其作为在插件中设置 UI 测试的参考：[intellij-ui-test-robot/ui-test-example][gh:ui-test-example]。

```kotlin
class MyUITest {

  @Test
  fun openAboutFromWelcomeScreen() {
    val robot = RemoteRobot("http://127.0.0.1:8082")
    robot.find<ComponentFixture>(byXpath("//div[@myactionlink = 'gearHover.svg']")).click()
    // ...
  }
}
```

![UI 测试][file:ui-testing.png]

提供了一个专门的 [运行 UI 测试](.github/workflows/run-ui-tests.yml) 工作流，用于手动触发以针对三种不同的操作系统运行 UI 测试：macOS、Windows 和 Linux。
由于其可选性质，此工作流未设置为自动工作流，但这可以通过更改 `on` 触发事件轻松实现，就像在 [构建](.github/workflows/build.yml) 工作流文件中那样。

## Qodana 集成

为了提高项目价值，IntelliJ 平台插件模板集成了 [Qodana][jb:qodana]，这是一个代码质量监控平台，允许你检查实现状况并找到任何可能需要改进的问题。

Qodana 为你带来了你在 JetBrains IDE 中喜爱的所有智能功能到你的 CI/CD 流水线中，并生成一个包含当前检查状态的 HTML 报告。

Qodana 检查在项目的两个级别上可用：

- 使用 [Qodana IntelliJ GitHub Action][jb:qodana-github-action]，在 [构建](.github/workflows/build.yml) 工作流内自动运行，
- 使用 [Gradle Qodana Plugin][gh:gradle-qodana-plugin]，因此你可以在本地环境或除 GitHub Actions 之外的任何 CI 上使用它。

Qodana 检查通过 Gradle 构建文件中的 `qodana { ... }` 部分和 [`qodana.yml`][file:qodana.yml] YAML 配置文件进行配置。

> [!注意]
> Qodana 要求 Docker 已安装并在你的环境中可用。

要运行检查，你可以使用预定义的*运行 Qodana* 配置，这将在 `http://localhost:8080` 上提供完整报告，或者使用 `./gradlew runInspections` 命令直接调用 Gradle 任务。

最终报告位于 `./build/reports/inspections/` 目录中。

![Qodana][file:qodana.png]


## 预定义的运行/调试配置

在默认的项目结构中，提供了一个包含预定义*运行/调试配置*的 `.run` 目录，这些配置暴露了相应的 Gradle 任务：

![运行/调试配置][file:run-debug-configurations.png]

| 配置名           | 描述                                                                                                                                                                 |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 运行插件         | 运行 [`:runIde`][gh:intellij-platform-gradle-plugin-runIde] IntelliJ Platform Gradle 插件任务。使用*调试*图标进行插件调试。                                              |
| 运行测试         | 运行 [`:test`][gradle:lifecycle-tasks] Gradle 任务。                                                                                                                   |
| 运行验证         | 运行 [`:verifyPlugin`][gh:intellij-platform-gradle-plugin-verifyPlugin] IntelliJ Platform Gradle 插件任务，以检查插件与指定的 IntelliJ IDE 的兼容性。                    |

> [!注意]
> 你可以在 `idea.log` 选项卡中找到正在运行任务的日志。
>
> ![运行/调试配置日志][file:run-logs.png]


## 持续集成

持续集成依赖于 [GitHub Actions][gh:actions]，这是一组工作流，可以自动化你的测试和发布过程。
得益于这种自动化，你可以将测试和验证阶段委托给持续集成（CI），而专注于开发（以及编写更多测试）。

> [!注意]
> 为确保“发布插件”作业中的“创建拉取请求”步骤正常运行，请确保通过导航到 `⚙️ 设置 > Actions > 常规 > 工作流权限` 来启用操作的“读写权限”。

在 `.github/workflows` 目录中，你可以找到以下 GitHub Actions 工作流的定义：

- [构建](.github/workflows/build.yml)
  - 在 `push` 和 `pull_request` 事件上触发。
  - 运行 *Gradle Wrapper Validation Action* 以验证包装器的校验和。
  - 运行 `verifyPlugin` 和 `test` Gradle 任务。
  - 使用 `buildPlugin` Gradle 任务构建插件，并为工作流中的后续作业提供工件。
  - 使用 *IntelliJ Plugin Verifier* 工具验证插件。
  - 在 GitHub Releases 页面上准备一个草稿发布以供手动验证。
- [发布](.github/workflows/release.yml)
  - 在 `released` 事件上触发。
  - 使用提供的发布说明内容更新 `CHANGELOG.md` 文件。
  - 在发布前使用提供的证书对插件进行签名。
  - 使用提供的 `PUBLISH_TOKEN` 将插件发布到 JetBrains 市场。
  - 根据插件版本设置发布渠道，例如 `1.0.0-beta` -> `beta` 渠道。
  - 修补更新日志并提交。
- [运行 UI 测试](.github/workflows/run-ui-tests.yml)
  - 手动触发。
  - 分别针对 macOS、Windows 和 Linux 运行。
  - 运行 `runIdeForUiTests` 和 `test` Gradle 任务。
- [模板清理](.github/workflows/template-cleanup.yml)
  - 在创建新的基于模板的仓库时，在 `push` 事件上触发一次。
  - 用 `.github/template-cleanup` 目录中的文件覆盖脚手架。
  - 用特定于目标仓库的内容覆盖 JetBrains 特定的句子或包名。
  - 删除冗余文件。

所有工作流文件都有准确的文档，因此浏览它们的源代码是个好主意。

### 依赖管理

此模板项目依赖于 Gradle 插件和外部库 – 在开发过程中，你会添加更多依赖。

Gradle 使用的所有插件和依赖项都由 [Gradle 版本目录][gradle:version-catalog] 管理，该目录在 [`gradle/libs.versions.toml`][file:libs.versions.toml] 文件中定义了依赖项的版本和坐标。

> [!注意]
> 要向项目添加新依赖项，请在 `dependencies { ... }` 块中添加：
>
> ```kotlin
> dependencies {
>   implementation(libs.annotations)
> }
> ```
>
> 并在 [`gradle/libs.versions.toml`][file:libs.versions.toml] 文件中定义依赖项，如下所示：
> ```toml
> [versions]
> annotations = "24.0.1"
>
> [libraries]
> annotations = { group = "org.jetbrains", name = "annotations", version.ref = "annotations" }
> ```

保持项目处于良好状态并更新所有依赖项需要时间和精力，但可以使用 [Dependabot][gh:dependabot] 来自动化此过程。

Dependabot 是由 GitHub 提供的一个机器人，用于检查构建配置文件并审查你任何过时或不安全的依赖项 – 如果有任何更新可用，它会创建一个新的拉取请求，提供 [适当的更改][gh:dependabot-pr]。

> [!注意]
> Dependabot 尚不支持检查 Gradle 包装器。
> 查看 [Gradle 发布][gradle:releases] 页面并更新你的 `gradle.properties` 文件：
> ```properties
> gradleVersion = ...
> ```
> 并运行
> ```bash
> ./gradlew wrapper
> ```

### 更新日志维护

发布更新时，让用户知道新版本提供了什么内容非常重要。
最好的方法是提供发布说明。

更新日志是一个精心整理的列表，包含有关任何新功能、修复和弃用的信息。
当提供时，这些列表可以在几个不同的地方找到：
- [CHANGELOG.md](./CHANGELOG.md) 文件，
- [发布页面][gh:releases]，
- JetBrains 市场插件页面的 *新增功能* 部分，
- 以及插件管理器的项目详细信息内部。

有许多处理项目更新日志的方法。
当前模板项目中使用的方法是 [保持更新日志][keep-a-changelog] 方法。

[Gradle Changelog 插件][gh:gradle-changelog-plugin] 负责将 [CHANGELOG.md](./CHANGELOG.md) 中提供的信息传播到 [IntelliJ Platform Gradle Plugin][gh:intellij-platform-gradle-plugin]。
你只需要负责将实际更改写在 `[Unreleased]` 部分的适当章节中。

你从一个几乎为空的更新日志开始：

```
# 你的插件更新日志

## [未发布]
### 新增
- 初始脚手架由 [IntelliJ 平台插件模板](https://github.com/JetBrains/intellij-platform-plugin-template) 创建
```

现在继续向 `新增` 组或任何其他最适合你更改的组提供更多条目（有关更多详细信息，请参见 [如何制作一个好的更新日志？][keep-a-changelog-how]）。

当发布插件更新时，你不需要关心将 `[Unreleased]` 标题提升到即将到来的版本 – 在你发布插件后，这将在持续集成（CI）上自动处理。
GitHub Actions 将替换它，并为你提供下一个版本的空白部分，以便你可以继续进行开发：

```
# 你的插件更新日志

## [未发布]

## [0.0.1]
### 新增
- 一个很棒的功能
- 初始脚手架由 [IntelliJ 平台插件模板](https://github.com/JetBrains/intellij-platform-plugin-template) 创建

### 修复
- 一个烦人的 bug
```

要配置 Changelog 插件的行为，例如创建带发布日期的标题，请参阅 [Gradle Changelog Plugin][gh:gradle-changelog-plugin] 的 README 文件。

### 发布流程

发布过程依赖于上述已经描述的工作流。
当你的主分支收到新的拉取请求或直接推送时，[构建](.github/workflows/build.yml) 工作流会对你的插件运行多个测试并准备一个草稿发布。

![草稿发布][file:draft-release.png]

草稿发布是发布的工作副本，你可以在发布之前进行审查。
它包括预定义的标题和 git 标签，当前插件版本，例如 `v0.0.1`。
更新日志使用 [gradle-changelog-plugin][gh:gradle-changelog-plugin] 自动提供。
还会构建一个附加了插件的工件文件。
每次新的构建都会覆盖之前的草稿，以保持你的*发布*页面整洁。

当你编辑草稿并使用 <kbd>发布版本</kbd> 按钮时，GitHub 将使用给定版本标记你的仓库，并在 Releases 选项卡中添加一个新条目。
接下来，它将通知*关注*仓库的用户，触发最终的 [发布](.github/workflows/release.yml) 工作流。

### 插件签名

插件签名是 2021.2 发布周期中引入的一种机制，旨在提高 [JetBrains Marketplace](https://plugins.jetbrains.com) 和我们所有基于 IntelliJ 的 IDE 中的安全性。

JetBrains Marketplace 签名旨在确保插件在发布和交付流水线过程中不会被修改。

当前项目提供了一个预定义的插件签名配置，允许你从持续集成（CI）和本地环境对插件进行签名和发布。
所有与签名相关的配置都应使用 [环境变量](#环境变量) 提供。

要了解如何生成签名证书，请查看 IntelliJ 平台插件 SDK 文档中的 [插件签名][docs:plugin-signing] 部分。

> [!注意]
> 记得使用 `base64` 编码对秘密环境变量进行编码，以避免多行值出现问题。

### 发布插件

> [!提示]
> 请务必遵循 [发布插件][docs:publishing] 中列出的所有指南，以遵循所有推荐和必需的步骤。

将插件发布到 [JetBrains Marketplace](https://plugins.jetbrains.com) 是一个简单的操作，它使用由 [intellij-platform-gradle-plugin][gh:intellij-platform-gradle-plugin-docs] 提供的 `publishPlugin` Gradle 任务。
此外，[发布](.github/workflows/release.yml) 工作流通过在新的发布出现在 GitHub Releases 部分时运行该任务来自动化此过程。

> [!注意]
> 在插件版本后设置后缀以将其发布到自定义仓库渠道，例如 `v1.0.0-beta` 会将你的插件推送到 `beta` [发布渠道][docs:release-channel]。

授权过程依赖于 `PUBLISH_TOKEN` 秘密环境变量，该变量在你项目仓库的 `⚙️ 设置 > 秘密和变量 > Actions` 部分中指定。

你可以在 JetBrains Marketplace 个人资料仪表板的 [我的令牌][jb:my-tokens] 选项卡中获取该令牌。

> [!警告]
> 在使用自动化部署流程之前，必须先在 JetBrains Marketplace 中手动创建一个新插件以指定许可证、仓库 URL 等选项。
> 请遵循 [发布插件][docs:publishing] 说明。


## 常见问题

### 如何在我的项目中使用 Java？

Java 语言与 Kotlin 一起默认受支持。
最初，`/src/main/kotlin` 目录可用，并包含最少的示例。
你仍然可以替换它或添加 `/src/main/java` 目录以开始使用 Java 语言。

### 如何使用 `[skip ci]` 提交消息禁用*测试*或*构建*作业？

自 2021 年 2 月起，GitHub Actions [已支持跳过 CI 功能][github-actions-skip-ci]。
如果消息包含以下字符串之一：`[skip ci]`、`[ci skip]`、`[no ci]`、`[skip actions]` 或 `[actions skip]` – 工作流将不会被触发。

### 为什么草稿发布不再包含构建的插件工件？

每个工作流创建的所有二进制文件仍然可用，但作为每次运行的输出工件与测试和 Qodana 结果一起提供。
这种方法为测试和调试预发布版本（例如，在你的本地环境中）提供了更多可能性。

## 有用链接

- [IntelliJ 平台 SDK 插件 SDK][docs]
- [IntelliJ 平台 Gradle 插件文档][gh:intellij-platform-gradle-plugin-docs]
- [IntelliJ 平台浏览器][jb:ipe]
- [JetBrains Marketplace 质量指南][jb:quality-guidelines]
- [IntelliJ 平台 UI 指南][jb:ui-guidelines]
- [JetBrains Marketplace 付费插件][jb:paid-plugins]
- [Kotlin UI DSL][docs:kotlin-ui-dsl]
- [IntelliJ SDK 代码示例][gh:code-samples]
- [JetBrains 平台 Slack][jb:slack]
- [JetBrains 平台 Twitter][jb:twitter]
- [IntelliJ IDEA 开放 API 和插件开发论坛][jb:forum]
- [保持更新日志][keep-a-changelog]
- [GitHub Actions][gh:actions]

[docs]: https://plugins.jetbrains.com/docs/intellij?from=IJPluginTemplate
[docs:intellij-platform-kotlin-oom]: https://plugins.jetbrains.com/docs/intellij/using-kotlin.html#incremental-compilation
[docs:intro]: https://plugins.jetbrains.com/docs/intellij/intellij-platform.html?from=IJPluginTemplate
[docs:kotlin-ui-dsl]: https://plugins.jetbrains.com/docs/intellij/kotlin-ui-dsl-version-2.html?from=IJPluginTemplate
[docs:kotlin]: https://plugins.jetbrains.com/docs/intellij/using-kotlin.html?from=IJPluginTemplate
[docs:kotlin-stdlib]: https://plugins.jetbrains.com/docs/intellij/using-kotlin.html?from=IJPluginTemplate#kotlin-standard-library
[docs:plugin.xml]: https://plugins.jetbrains.com/docs/intellij/plugin-configuration-file.html?from=IJPluginTemplate
[docs:publishing]: https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate
[docs:release-channel]: https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate#specifying-a-release-channel
[docs:using-gradle]: https://plugins.jetbrains.com/docs/intellij/developing-plugins.html?from=IJPluginTemplate
[docs:plugin-signing]: https://plugins.jetbrains.com/docs/intellij/plugin-signing.html?from=IJPluginTemplate
[docs:project-structure-settings]: https://www.jetbrains.com/help/idea/project-settings-and-structure.html
[docs:testing-plugins]: https://plugins.jetbrains.com/docs/intellij/testing-plugins.html?from=IJPluginTemplate

[file:draft-release.png]: ./.github/readme/draft-release.png
[file:get-from-version-control]: ./.github/readme/get-from-version-control.png
[file:gradle.properties]: ./gradle.properties
[file:intellij-platform-plugin-template-dark]: ./.github/readme/intellij-platform-plugin-template-dark.svg#gh-dark-mode-only
[file:intellij-platform-plugin-template-light]: ./.github/readme/intellij-platform-plugin-template-light.svg#gh-light-mode-only
[file:libs.versions.toml]: ./gradle/libs.versions.toml
[file:project-structure-sdk.png]: ./.github/readme/project-structure-sdk.png
[file:plugin.xml]: ./src/main/resources/META-INF/plugin.xml
[file:qodana.png]: ./.github/readme/qodana.png
[file:qodana.yml]: ./qodana.yml
[file:run-debug-configurations.png]: ./.github/readme/run-debug-configurations.png
[file:run-logs.png]: ./.github/readme/run-logs.png
[file:settings-secrets.png]: ./.github/readme/settings-secrets.png
[file:template_cleanup.yml]: ./.github/workflows/template-cleanup.yml
[file:ui-testing.png]: ./.github/readme/ui-testing.png
[file:use-this-template.png]: ./.github/readme/use-this-template.png

[gh:actions]: https://help.github.com/zh/actions
[gh:build]: https://github.com/JetBrains/intellij-platform-plugin-template/actions?query=workflow%3ABuild
[gh:code-samples]: https://github.com/JetBrains/intellij-sdk-code-samples
[gh:dependabot]: https://docs.github.com/zh/enterprise-server@3.9/administering-a-repository/keeping-your-dependencies-updated-automatically
[gh:dependabot-pr]: https://github.com/JetBrains/intellij-platform-plugin-template/pull/73
[gh:gradle-changelog-plugin]: https://github.com/JetBrains/gradle-changelog-plugin
[gh:intellij-platform-gradle-plugin]: https://github.com/JetBrains/intellij-platform-gradle-plugin
[gh:intellij-platform-gradle-plugin-docs]: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
[gh:intellij-platform-gradle-plugin-runIde]: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-tasks.html#runIde
[gh:intellij-platform-gradle-plugin-verifyPlugin]: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-tasks.html#verifyPlugin
[gh:gradle-qodana-plugin]: https://github.com/JetBrains/gradle-qodana-plugin
[gh:intellij-ui-test-robot]: https://github.com/JetBrains/intellij-ui-test-robot
[gh:kover]: https://github.com/Kotlin/kotlinx-kover
[gh:releases]: https://github.com/JetBrains/intellij-platform-plugin-template/releases
[gh:ui-test-example]: https://github.com/JetBrains/intellij-ui-test-robot/tree/master/ui-test-example

[gradle]: https://gradle.org
[gradle:build-cache]: https://docs.gradle.org/zh/current/userguide/build_cache.html
[gradle:configuration-cache]: https://docs.gradle.org/zh/current/userguide/configuration_cache.html
[gradle:kotlin-dsl]: https://docs.gradle.org/zh/current/userguide/kotlin_dsl.html
[gradle:kotlin-dsl-assignment]: https://docs.gradle.org/zh/current/userguide/kotlin_dsl.html#kotdsl:assignment
[gradle:lifecycle-tasks]: https://docs.gradle.org/zh/current/userguide/java_plugin.html#lifecycle_tasks
[gradle:releases]: https://gradle.org/releases
[gradle:version-catalog]: https://docs.gradle.org/zh/current/userguide/platforms.html#sub:version-catalog

[jb:github]: https://github.com/JetBrains/.github/blob/main/profile/README.md
[jb:download-ij]: https://www.jetbrains.com/zh-cn/idea/download
[jb:forum]: https://intellij-support.jetbrains.com/hc/en-us/community/topics/200366979-IntelliJ-IDEA-Open-API-and-Plugin-Development
[jb:ipe]: https://jb.gg/ipe
[jb:my-tokens]: https://plugins.jetbrains.com/author/me/tokens
[jb:paid-plugins]: https://plugins.jetbrains.com/docs/marketplace/paid-plugins-marketplace.html
[jb:qodana]: https://www.jetbrains.com/zh-cn/help/qodana
[jb:qodana-github-action]: https://www.jetbrains.com/zh-cn/help/qodana/qodana-intellij-github-action.html
[jb:quality-guidelines]: https://plugins.jetbrains.com/docs/marketplace/quality-guidelines.html
[jb:slack]: https://plugins.jetbrains.com/slack
[jb:twitter]: https://twitter.com/JBPlatform
[jb:ui-guidelines]: https://jetbrains.github.io/ui

[codecov]: https://codecov.io
[github-actions-skip-ci]: https://github.blog/changelog/2021-02-08-github-actions-skip-pull-request-and-push-workflows-with-skip-ci/
[keep-a-changelog]: https://keepachangelog.com
[keep-a-changelog-how]: https://keepachangelog.com/en/1.0.0/#how
[semver]: https://semver.org
[xpath]: https://www.w3.org/TR/xpath-21/
